package address.view;

import java.io.File;
import java.net.MalformedURLException;

import address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class AccueilLayoutController {
	//ATTRIBUTS FXML
	@FXML
	private ImageView projet_PreviewImage;
	
	//ATTRIBUTS
	private MainApp mainApp;
	
	//CONSTRUCTEUR
	public AccueilLayoutController() {
	}
	
	//METHODES FXML
	
	/*
	 * Initialize l'affichage de l'accueil
	 * */
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
	
	/*
	 * Setter
	 * */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
