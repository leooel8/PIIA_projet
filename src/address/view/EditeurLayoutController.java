package address.view;

import address.MainApp;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class EditeurLayoutController {
	
	private MainApp mainApp;
	private Stage stage;

	public EditeurLayoutController() {
	}
	
	@FXML
	private void initialize() {
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	public void setProjetStage(Stage stage) {
		this.stage = stage;
	}
}
