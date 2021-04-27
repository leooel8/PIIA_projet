package address.view;


import java.util.ArrayList;

import address.MainApp;
import address.modele.Item;
import address.modele.Projet;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EditeurLayoutController {
	
	@FXML
	private Label item_NameLabel;
	@FXML
	private Label item_XLabel;
	@FXML
	private Label item_YLabel;
	@FXML
	private ImageView item_PreviewImage;
	@FXML
	private BorderPane borderPane;
	
	private Canvas canvas;
	
	private MainApp mainApp;
	private Stage stage;
	private int currentItemIndex;
	private Projet currentProjet;
	private boolean enDeplacement;
	private double x_souris;
	private double y_souris;
	private int scale;
	private GraphicsContext gc;
	private boolean inNavigate;
	private Projet lastState;
	private ArrayList<Projet> historic;

	public EditeurLayoutController() {
		this.enDeplacement = false;
		this.inNavigate = false;
		historic = new ArrayList<Projet>(0);
	}
	
	public void setCanvas() {
		this.findScale();
		this.canvas = new Canvas(this.currentProjet.getWidth()*scale, this.currentProjet.getHeight()*scale);
		
		canvas.setOnMousePressed(e -> {
			try {
				attrape(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		canvas.setOnMouseReleased(e -> lache(e));
		
		canvas.setOnMouseDragged(e -> {
			try {
				deplace(e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		borderPane.setCenter(canvas);
		
		gc = this.canvas.getGraphicsContext2D();
		
		drawCanvas();
		
	}
	
	public Projet getCurrentProjet() {
		return this.currentProjet;
	}
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	private void attrape(MouseEvent e) throws Exception {
		for (int i = 0; i < this.currentProjet.getItemList().size(); i++) {
			if (this.currentProjet.getItemList().get(i).isIn(e.getX(), e.getY())) {
				lastState = new Projet(this.currentProjet.getName(), this.currentProjet.getWidth(), this.currentProjet.getHeight(), this.currentProjet.getItemList());
				this.historic.add(0, lastState);
				this.currentItemIndex = i;
				this.enDeplacement = true;
				this.x_souris = e.getX();
				this.y_souris = e.getY();
				break;
			}
		}
	}
	
	@FXML
	private void deplace(MouseEvent e) throws Exception {
		if (this.enDeplacement) {
			double dx = e.getX() - this.x_souris;
			double dy = e.getY() - this.y_souris;
			
			double x_avant = this.currentProjet.getItemList().get(currentItemIndex).getX();
			double y_avant = this.currentProjet.getItemList().get(currentItemIndex).getY();
			
			if ((dx < 0 && this.currentProjet.getItemList().get(currentItemIndex).getX() + dx > 0) || (dx > 0 && this.currentProjet.getItemList().get(currentItemIndex).getX() + this.currentProjet.getItemList().get(currentItemIndex).getWidth() + dx <= this.canvas.getWidth())) {
				this.currentProjet.getItemList().get(currentItemIndex).setX(x_avant + dx);
			}
			if ((dy < 0 && this.currentProjet.getItemList().get(currentItemIndex).getY() + dy > 0) || (dy > 0 && this.currentProjet.getItemList().get(currentItemIndex).getY() + this.currentProjet.getItemList().get(currentItemIndex).getHeight() + dy <= this.canvas.getHeight())) {
				this.currentProjet.getItemList().get(currentItemIndex).setY(y_avant + dy);
			}
			
			
			
			this.x_souris = e.getX();
			this.y_souris = e.getY();
			this.drawCanvas();
		}
	}
	
	@FXML
	private void lache(MouseEvent e) {
		if (enDeplacement) {
			enDeplacement = false;
		}
		
		
		this.drawCanvas();
	}
	
	@FXML
	private void handleZoomOut() {
		canvas.setScaleX(this.canvas.getScaleX()/1.5);
		canvas.setScaleY(this.canvas.getScaleY()/1.5);
	}
	
	@FXML
	private void handleZoomIn() {
		canvas.setScaleX(this.canvas.getScaleX()*1.5);
		canvas.setScaleY(this.canvas.getScaleY()*1.5);
	}
	
	@FXML
	private void handleNavigate() {
		this.inNavigate = true;
	}
	
	@FXML
	private void handleCancelButton() {
		if (this.historic.size() != 0) {
			this.currentProjet = this.historic.get(0);
			this.historic.remove(0);
			this.drawCanvas();
		}
		
	}
	
	@FXML
	private void handleAddButton() throws Exception {
		boolean addClicked = this.mainApp.showEditorCatalogue(this);
//		if (addClicked) {
//			this.drawCanvas();
//			
//		}
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	public void setProjetStage(Stage stage) {
		this.stage = stage;
	}
	public void setCurrentItem(int item) {
		this.currentItemIndex = item;
	}
	public void setCurrentProjet(Projet projet) throws Exception {
		this.currentProjet =  projet;
		this.addStateToHistoric();
	}
	
	public void addStateToHistoric() throws Exception {
		lastState = new Projet(this.currentProjet.getName(), this.currentProjet.getWidth(), this.currentProjet.getHeight(), this.currentProjet.getItemList());
		this.historic.add(0, lastState);
	}
	
	private int findScale() {
		scale = 100;
		int lastScale = 100;
		boolean  flag = true;
		int count = 100;
		while (flag) {
			if (this.currentProjet.getWidth() * scale < 1542
					&&  this.currentProjet.getHeight() * scale < 876) {
				if (count > 0) {
					lastScale = scale;
					scale += (int) scale/2;
				} else {
					flag = false;
				}
				
			} else {
				lastScale = scale;
				scale -= (int) scale/2;
			}
			if (Math.abs(lastScale - scale) < 5) {
				flag = false;
			}
			count--;
		}
		return scale;
	}
	
	public void drawCanvas() {

		gc.strokeRect(0, 0, 20, 20);
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,this.canvas.getWidth(),this.canvas.getHeight());
		
		gc.setStroke(Color.BLACK);
		gc.strokeRect(2, 2, canvas.getWidth() - 2, canvas.getHeight()-2);
		
		for (int i = 0; i < this.currentProjet.getItemList().size(); i++) {
			Item currentItem = this.currentProjet.getItemList().get(i);
			
			currentItem.draw(gc);
			
			if (i == this.currentItemIndex) {
				gc.setStroke(Color.BLUE);
				gc.strokeRect(currentItem.getX() + 2, currentItem.getY() + 2, currentItem.getWidth() - 4, currentItem.getHeight() - 4);
			}
		}
		drawLayering();
	}
	
	public void drawLayering() {
		for (int i = 0; i < this.currentProjet.getItemList().size(); i++) {
			Item currItem_1 = this.currentProjet.getItemList().get(i);
			for (int j = 0; j < this.currentProjet.getItemList().size(); j++) {
				Item currItem_2 = this.currentProjet.getItemList().get(j);
				
				if (i != j) {
					for (double x = currItem_1.getX(); x <= currItem_1.getX() + currItem_1.getWidth(); x++) {
						for (double y = currItem_1.getY(); y <=currItem_1.getY() + currItem_1.getHeight(); y++) {
							if (currItem_2.isIn(x, y)) {
								gc.setStroke(Color.RED);
								gc.strokeRect(currItem_1.getX(), currItem_1.getY(), currItem_1.getWidth(), currItem_1.getHeight());
							}
						}
					}
				}
			}
		}
	}
}
