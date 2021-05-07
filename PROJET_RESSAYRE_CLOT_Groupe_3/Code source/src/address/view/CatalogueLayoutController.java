package address.view;

import address.MainApp;
import address.modele.Type;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CatalogueLayoutController {

	//ATTRIBUTS FXML
	@FXML
	private TableView<Type> electromenagerTable=null; 
	@FXML
	private TableView<Type> mobilierTable=null; 
	@FXML
	private TableView<Type> lumiereEtDecorationTable=null; 
	@FXML
	private TableColumn<Type, String> electromenagerColumn=null;
	@FXML
	private TableColumn<Type, String> mobilierColumn=null;
	@FXML
	private TableColumn<Type, String> lumiereEtDecorationColumn=null;   

	@FXML
	private Canvas canvas;

	//ATTRIBUTS
	private MainApp mainApp;
	private Stage stage;

	//CONSTRUCTEUR
	public CatalogueLayoutController() {
	}

	//METHODES FXML
	@FXML
	private void initialize() {

		// Initialize the types tables with columns.  	
		electromenagerColumn.setCellValueFactory(
				cellData -> cellData.getValue().typeNameProperty());
		mobilierColumn.setCellValueFactory(
				cellData -> cellData.getValue().typeNameProperty());
		lumiereEtDecorationColumn.setCellValueFactory(
				cellData -> cellData.getValue().typeNameProperty());  

		// Clear type d'item details.
		// setCanvas();

		// Listen for selection changes and show the type details when changed.
		electromenagerTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showImagesCatalogue(newValue));

		mobilierTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showImagesCatalogue(newValue));

		lumiereEtDecorationTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showImagesCatalogue(newValue));

	}	

	//Setters
	public void setCanvas() {		
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,this.canvas.getWidth(),this.canvas.getHeight());
	}

	public void setProjetStage(Stage stage) {
		this.stage = stage;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		// Add observable list data to the table
		electromenagerTable.setItems(mainApp.getElectromenagerData());
		mobilierTable.setItems(mainApp.getMobilierData());
		lumiereEtDecorationTable.setItems(mainApp.getLumiereEtDecorationData());      	
	}

	private void showImagesCatalogue(Type type) { 
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

}