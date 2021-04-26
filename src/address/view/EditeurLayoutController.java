package address.view;


import address.MainApp;
import address.modele.Item;
import address.modele.Projet;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	private Canvas canvas;
	
	private MainApp mainApp;
	private Stage stage;
	private int currentItemIndex;
	private Projet currentProjet;
	private boolean enDeplacement;
	private double x_souris;
	private double y_souris;

	public EditeurLayoutController() {
		this.enDeplacement = false;
	}
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	private void attrape(MouseEvent e) {
		for (int i = 0; i < this.currentProjet.getItemList().size(); i++) {
			if (this.currentProjet.getItemList().get(i).isIn(e.getX(), e.getY())) {
				this.currentItemIndex = i;
				this.enDeplacement = true;
				this.x_souris = e.getX();
				this.y_souris = e.getY();
				break;
			}
		}
	}
	
	@FXML
	private void deplace(MouseEvent e) {
		if (this.enDeplacement) {
			double dx = e.getX() - this.x_souris;
			double dy = e.getY() - this.y_souris;
			
			double x_avant = this.currentProjet.getItemList().get(currentItemIndex).getX();
			double y_avant = this.currentProjet.getItemList().get(currentItemIndex).getY();
			
			this.currentProjet.getItemList().get(currentItemIndex).setX(x_avant + dx);
			this.currentProjet.getItemList().get(currentItemIndex).setY(y_avant + dy);
			
			this.x_souris = e.getX();
			this.y_souris = e.getY();
			this.setCanvas();
		}
	}
	
	@FXML
	public void lache(MouseEvent e) {
		this.enDeplacement = false;
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
	public void setCurrentProjet(Projet projet) {
		this.currentProjet =  projet;
	}
	public void setCanvas() {
		this.canvas.setWidth(500);
		this.canvas.setHeight(500);
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,this.canvas.getWidth(),this.canvas.getHeight());
		for (int i = 0; i < this.currentProjet.getItemList().size(); i++) {
			Item currentItem = this.currentProjet.getItemList().get(i);
			
			currentItem.draw(gc);
		}
	}
}
