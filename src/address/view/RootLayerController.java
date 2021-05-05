package address.view;

import address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

public class RootLayerController {
	//ATTRIBUTS
	private MainApp mainApp;

	//METHODES FXML
	@FXML
	private void initialize() {
	}
	
	@FXML
	private void handleAccueilLink() {
		this.mainApp.showAccueil();
	}
	
	@FXML
	private void handleAccueilButton() {
		this.mainApp.showAccueil();
	}
	
	@FXML
	private void handleProjetsButton() {
		this.mainApp.showProjets();
	}
	
	@FXML
	private void handleCatalogueButton() {
		this.mainApp.showCatalogue();
	}
	
	//METHODES
	//Setters
	public void setMainApp(MainApp mainApp)  {
		this.mainApp = mainApp;
	}
}
