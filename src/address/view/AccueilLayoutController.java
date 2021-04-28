package address.view;



import address.MainApp;
import javafx.fxml.FXML;


public class AccueilLayoutController {
	
	
	
	private MainApp mainApp;
	
	public AccueilLayoutController() {
	}

	
	@FXML
	private void handleProjectButton() throws Exception {
		 this.mainApp.showProjets();
	}
	
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
