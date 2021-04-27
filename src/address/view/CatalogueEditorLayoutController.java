
package address.view;

import java.net.MalformedURLException;
import java.util.ArrayList;
import address.MainApp;
import address.modele.Item;
import address.modele.Type;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;

public class CatalogueEditorLayoutController {

	@FXML
	private Canvas canvas;
	
	ArrayList<Type> types= new ArrayList<Type>();
	Type currentType;
	Item currentItem;
	
	private MainApp mainApp;
	private Stage stage;
	private EditeurLayoutController editeur;
	private boolean addClicked = false;

	

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public CatalogueEditorLayoutController() {
	}

	
	@FXML
	private void initialize() {
	}
	
	/**
	 * Fills all text fields to show details about the items.
	 * If the specified person is null, all text fields are cleared.
	 *
	 * @param person the person or null
	 */
	private void showImagesCatalogue() { ////////////////////////////////////////////Typelist<item>
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if (currentType != null) {		
			setCanvas();		
			// Fill the labels with info from the person object.        
			int abs=40;
			int ord=50;
			for (int i=0;i<=currentType.getItemsList().size()/2;i++) {
				currentType.getItemsList().get(i).setX(abs);
				currentType.getItemsList().get(i).setY(ord);
				currentType.getItemsList().get(i).draw(gc);
				gc.strokeText(currentType.getItemsList().get(i).getName(), abs+20, ord-10); //on ecrit son nom au dessus de l'image.
				abs+=currentType.getItemsList().get(i).getWidth()+40;
				if(i==(currentType.getItemsList().size()/2)) 
					ord+=currentType.getItemsList().get(i).getHeight()+50;
				
				if(currentType.getItemsList().get(i) == currentItem) {
					gc.setStroke(Color.BLUE);
					gc.strokeRect(this.currentItem.getX(), this.currentItem.getY(), this.currentItem.getWidth(), this.currentItem.getHeight());
					gc.setStroke(Color.BLACK);
				}
			}
			abs=40;			
			for (int i=currentType.getItemsList().size()/2+1;i<currentType.getItemsList().size();i++) {
				currentType.getItemsList().get(i).setX(abs);
				currentType.getItemsList().get(i).setY(ord);
				currentType.getItemsList().get(i).draw(gc);
				gc.strokeText(currentType.getItemsList().get(i).getName(), abs+20, ord-10);
				abs+=currentType.getItemsList().get(i).getWidth()+40;
				if(currentType.getItemsList().get(i) == currentItem) {
					gc.setStroke(Color.BLUE);
					gc.strokeRect(this.currentItem.getX(), this.currentItem.getY(), this.currentItem.getWidth(), this.currentItem.getHeight());
					gc.setStroke(Color.BLACK);
				}
			}
		} else {
			// catalogue is null, remove all the text.
			setCanvas();
		}
	}

	@FXML
	private void handleTypesLink(ActionEvent e) {
		//canvas.
		for (Type type : this.mainApp.getTypesList()) {
			Hyperlink hyp=(Hyperlink) e.getSource();
			if(type.getTypeName().equals(hyp.getText())) {
				this.currentType = type;
				showImagesCatalogue();
				break;
			}
		}
	}
	@FXML
	private void handleAddButton() throws Exception {
		Item res = new Item(currentItem.getName(), currentItem.getX(), currentItem.getY(), currentItem.getWidth(), currentItem.getHeight(), currentItem.getUrl());
		this.editeur.getCurrentProjet().addItem(res);
		this.editeur.drawCanvas();
	}
	
	public void attrape(MouseEvent e) {
		if(currentType != null) {
			for (int i = 0; i < currentType.getItemsList().size(); i++) {
				if (currentType.getItemsList().get(i).isIn(e.getX(), e.getY())) {
					this.currentItem = currentType.getItemsList().get(i);
				}
			}
		}
		showImagesCatalogue();
	}

	public void setCanvas() {	
		canvas.setOnMouseClicked(e -> {
			attrape(e);
		});
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,this.canvas.getWidth(),this.canvas.getHeight());
	}
	
	public void setEditeur(EditeurLayoutController editeur) {
		this.editeur = editeur;
	}

	public void setProjetStage(Stage stage) {
		this.stage = stage;
	}
	
	public boolean isAddClicked() {
		return this.addClicked;
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;	      	
	}

}