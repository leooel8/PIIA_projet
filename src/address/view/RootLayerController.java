package address.view;

import address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

public class RootLayerController {
	private MainApp mainApp;

	@FXML
	private void initialize() {
	}
	
	public void setMainApp(MainApp mainApp)  {
		this.mainApp = mainApp;
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
}
