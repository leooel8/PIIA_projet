
package address.view;

import java.util.ArrayList;
import address.MainApp;
import address.modele.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;

public class CatalogueEditorLayoutController {

	@FXML
	private Canvas canvas;
	
	ArrayList<Type> types= new ArrayList<Type>();
	
	private MainApp mainApp;
	private Stage stage;

	

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
	private void showImagesCatalogue(Type type) { ////////////////////////////////////////////Typelist<item>
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if (type != null) {		
			setCanvas();		
			// Fill the labels with info from the person object.        
			int abs=40;
			int ord=50;
			for (int i=0;i<=type.getItemsList().size()/2;i++) {
				type.getItemsList().get(i).setX(abs);
				type.getItemsList().get(i).setY(ord);
				type.getItemsList().get(i).draw(gc);
				gc.strokeText(type.getItemsList().get(i).getName(), abs+20, ord-10); //on ecrit son nom au dessus de l'image.
				abs+=type.getItemsList().get(i).getWidth()+40;
				if(i==(type.getItemsList().size()/2)) 
					ord+=type.getItemsList().get(i).getHeight()+50;
			}
			abs=40;			
			for (int i=type.getItemsList().size()/2+1;i<type.getItemsList().size();i++) {
				type.getItemsList().get(i).setX(abs);
				type.getItemsList().get(i).setY(ord);
				type.getItemsList().get(i).draw(gc);
				gc.strokeText(type.getItemsList().get(i).getName(), abs+20, ord-10);
				abs+=type.getItemsList().get(i).getWidth()+40;			
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
				showImagesCatalogue(type);
				break;
			}
		}
	}

	public void setCanvas() {			
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,this.canvas.getWidth(),this.canvas.getHeight());
	}

	public void setProjetStage(Stage stage) {
		this.stage = stage;
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