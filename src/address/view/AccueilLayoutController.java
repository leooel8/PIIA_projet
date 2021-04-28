package address.view;



import java.io.File;
import java.net.MalformedURLException;

import address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class AccueilLayoutController {
	
	@FXML
	private ImageView projet_PreviewImage;
	
	private MainApp mainApp;
	
	public AccueilLayoutController() {
	}
	
	@FXML
	private void initialize() throws MalformedURLException {
		File file = new File("src/address/Images/Default/Cuisine_2.png");
		String localURL = file.toURI().toURL().toString();
		Image useImage = new Image(localURL);
		this.projet_PreviewImage.setImage(useImage);
	}
	
	@FXML
	private void handleProjectButton() throws Exception {
		 this.mainApp.showProjets();
	}
	
	
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
