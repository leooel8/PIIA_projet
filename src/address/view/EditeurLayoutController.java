package address.view;


import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import address.MainApp;
import address.modele.Item;
import address.modele.Projet;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
	private Label projet_NameLabel;
	@FXML
	private Label projet_WidthLabel;
	@FXML
	private Label projet_HeightLabel;
	@FXML
	private ImageView projet_PreviewImage;
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
	private Projet lastState;
	private Projet previousState;
	private ArrayList<Projet> historic;
	private ArrayList<Projet> cirotsih;

	public EditeurLayoutController() {
		enDeplacement = false;
		historic = new ArrayList<Projet>(0);
		cirotsih = new ArrayList<Projet>(0);
	}
	
	public void setCanvas() throws Exception {
		this.findScale();
		this.canvas = new Canvas(this.currentProjet.getWidth()*scale, this.currentProjet.getHeight()*scale);
		this.projet_NameLabel.setText(currentProjet.getName());
		this.projet_WidthLabel.setText(Integer.toString(this.currentProjet.getWidth()));
		this.projet_HeightLabel.setText(Integer.toString(this.currentProjet.getHeight()));
		
		File file = new File("src/address/Images/Default/ExempleItem.png");
		String localURL = file.toURI().toURL().toString();
		Image useImage = new Image(localURL);
		this.projet_PreviewImage.setImage(useImage);
		
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
				this.addStateToHistoric(true);
				this.currentItemIndex = i;
				this.enDeplacement = true;
				this.cirotsih = new ArrayList<Projet>(0);
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
			
			Item currItem = this.currentProjet.getItemList().get(currentItemIndex);
			
			double x_avant = this.currentProjet.getItemList().get(currentItemIndex).getX();
			double y_avant = this.currentProjet.getItemList().get(currentItemIndex).getY();
			
			if (currItem.getRotated()) {
				double diff = (currItem.getWidth() - currItem.getHeight())/2 ;
				if ((dx < 0 && currItem.getX() + dx + diff > 0) || (dx > 0 && currItem.getX() + diff + dx + currItem.getHeight() < canvas.getWidth())) {
					this.currentProjet.getItemList().get(currentItemIndex).setX(x_avant + dx);
				}
				if ((dy < 0 && currItem.getY() - diff + dy > 0) || (dy > 0 && currItem.getY() - diff + dy + currItem.getWidth() < canvas.getHeight())) {
					this.currentProjet.getItemList().get(currentItemIndex).setY(y_avant + dy);
				}
			} else {
				if ((dx < 0 && currItem.getX() + dx > 0) || (dx > 0 && currItem.getX() + currItem.getWidth() + dx <= this.canvas.getWidth())) {
					this.currentProjet.getItemList().get(currentItemIndex).setX(x_avant + dx);
				}
				if ((dy < 0 && currItem.getY() + dy > 0) || (dy > 0 && currItem.getY() + currItem.getHeight() + dy <= this.canvas.getHeight())) {
					this.currentProjet.getItemList().get(currentItemIndex).setY(y_avant + dy);
				}
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
	private void handleCancelButton() throws Exception {
		if (this.historic.size() != 0) {
			this.addStateToHistoric(false);
			this.currentProjet = this.historic.get(0);
			this.historic.remove(0);
			this.currentItemIndex = this.currentProjet.getItemList().size() - 1;
			this.drawCanvas();
		}
	}
	
	@FXML
	private void handleRetablirButton() {
		if (this.cirotsih.size() != 0) {
			this.currentProjet = this.cirotsih.get(0);
			this.cirotsih.remove(0);
			this.drawCanvas();
		}
	}
	
	@FXML
	private void handleAddButton() throws Exception {
		boolean addClicked = this.mainApp.showEditorCatalogue(this);
		if (addClicked) {
			this.drawCanvas();
		}
	}
	
	@FXML
	private void handleDeleteButton() throws Exception {
		if (this.currentProjet.getItemList().size() != 0) {
			this.addStateToHistoric(true);
			this.currentProjet.getItemList().remove(currentItemIndex);
			currentItemIndex = this.currentProjet.getItemList().size() - 1;
			this.drawCanvas();
		}
	}
	
	@FXML
	private void handlRotateButton() throws Exception {
		System.out.println("Before" + historic.size());
		this.addStateToHistoric(true);
		System.out.println("After" + historic.size());
		currentProjet.getItemList().get(currentItemIndex).rotateRight(this.canvas);
		drawCanvas();
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
		this.addStateToHistoric(true);
	}
	
	public void addStateToHistoric(boolean historic) throws Exception {
		
		if (historic) {
			lastState = new Projet(this.currentProjet.getName(), this.currentProjet.getWidth(), this.currentProjet.getHeight(), this.currentProjet.getItemList());
			this.historic.add(0, lastState);
		} else {
			previousState = new Projet(this.currentProjet.getName(), this.currentProjet.getWidth(), this.currentProjet.getHeight(), this.currentProjet.getItemList());
			this.cirotsih.add(0, previousState);
		}
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
	
	public Canvas drawCanvas() {
		if (currentProjet.getItemList().size() != 0) {
			this.item_NameLabel.setText(currentProjet.getItemList().get(currentItemIndex).getName());
			this.item_XLabel.setText(Double.toString(currentProjet.getItemList().get(currentItemIndex).getWidth()));
			this.item_YLabel.setText(Double.toString(currentProjet.getItemList().get(currentItemIndex).getHeight()));
			this.item_PreviewImage.setImage(currentProjet.getItemList().get(currentItemIndex).getImage());
		
		} else {
			this.item_NameLabel.setText("Aucun item n'est encore dans le projet");
			this.item_XLabel.setText(Integer.toString(0));
			this.item_YLabel.setText(Integer.toString(0));
			this.item_PreviewImage.setImage(null);
		}
		
		
		gc.strokeRect(0, 0, 20, 20);
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,this.canvas.getWidth(),this.canvas.getHeight());
		
		gc.setStroke(Color.BLACK);
		gc.strokeRect(2, 2, canvas.getWidth() - 2, canvas.getHeight()-2);
		
		if (currentProjet.getItemList().size() != 0) {
			for (int i = 0; i < this.currentProjet.getItemList().size(); i++) {
				Item currentItem = this.currentProjet.getItemList().get(i);
				if (currentItem.getRotation() != 0) {
					currentItem.drawTest(gc);
				} else {
					currentItem.draw(gc);
				}
				
				if (i == this.currentItemIndex) {
					gc.setStroke(Color.BLUE);
					if (currentItem.getRotated()) {
						double diff = (currentItem.getWidth() - currentItem.getHeight())/2 ;
						gc.strokeRect(currentItem.getX() + diff + 2, currentItem.getY() - diff + 2, currentItem.getHeight() - 4, currentItem.getWidth() - 4);
					} else {
						gc.strokeRect(currentItem.getX() + 2, currentItem.getY() + 2, currentItem.getWidth() - 4, currentItem.getHeight() - 4);
					}
					
				}
			}
			drawLayering();
		}
			
		return this.canvas;
	}
	
	public void drawLayering() {
		for (int i = 0; i < this.currentProjet.getItemList().size(); i++) {
			Item currItem_1 = this.currentProjet.getItemList().get(i);
			for (int j = 0; j < this.currentProjet.getItemList().size(); j++) {
				Item currItem_2 = this.currentProjet.getItemList().get(j);
				
				if (i != j) {
					if (currItem_1.getRotated()) {
						double diff = (currItem_1.getWidth() - currItem_1.getHeight())/2 ;
						for (double x = currItem_1.getX() + diff; x <= currItem_1.getX() + diff + currItem_1.getHeight(); x++) {
							for (double y = currItem_1.getY() - diff; y <=currItem_1.getY() - diff + currItem_1.getWidth(); y++) {
								if (currItem_2.isIn(x, y)) {
									gc.setStroke(Color.RED);
									gc.strokeRect(currItem_1.getX() + diff, currItem_1.getY() - diff, currItem_1.getHeight(), currItem_1.getWidth());
									
									
								}
							}
						}
					} else {
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
}
