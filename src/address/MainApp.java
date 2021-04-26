package address;

import java.io.IOException;
import java.util.ArrayList;

import address.view.AccueilLayoutController;
import address.view.EditeurLayoutController;
import address.view.ProjetsLayoutController;
import address.view.RootLayerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import address.modele.Projet;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private AnchorPane accueilLayout;
	private BorderPane editeurLayout;
	private AnchorPane projetsLayout;
	private ArrayList<Projet> projetsList;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("KitchenApp");
		this.projetsList = new ArrayList<Projet>(0);
		
		initRootLayout();
		
		showAccueil();
	}
	
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			RootLayerController controller = loader.getController();
			controller.setMainApp(this);
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void showAccueil() {
		try {
			if (this.editeurLayout != null) {
				if (this.editeurLayout.isVisible()) {
					this.editeurLayout.setVisible(false);
				}
			}
			if (this.projetsLayout != null) {
				if (this.projetsLayout.isVisible()) {
					this.projetsLayout.setVisible(false);
				}
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AccueilLayout.fxml"));
			accueilLayout = (AnchorPane) loader.load();
			
			rootLayout.setCenter(accueilLayout);
			
			AccueilLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch(IOException  e) {
			e.printStackTrace();
		}
	}
	
	public void showProjets() {
		try {
			if(this.editeurLayout!=null) {
				if (this.editeurLayout.isVisible()) {
					this.editeurLayout.setVisible(false);
				}
			}
			
			else if (this.accueilLayout.isVisible()) {
				this.accueilLayout.setVisible(false);
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProjetsLayout.fxml"));
			projetsLayout = (AnchorPane) loader.load();
			
			rootLayout.setCenter(projetsLayout);
			
			ProjetsLayoutController controller = loader.getController();
			controller.setMainApp(this);
			controller.setProjetStage(primaryStage);
			controller.setFields();
			if (this.projetsList.size() != 0) {
				controller.setProjet(this.projetsList.get(this.projetsList.size() - 1), this.projetsList.size() - 1);
			} else {
				Projet projet = new  Projet("Aucun projet existant", 0, 0);
				controller.setProjet(projet, -1);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showEditeur() {
		try {
			if (this.accueilLayout != null) {
				if (this.accueilLayout.isVisible()) {
					this.accueilLayout.setVisible(false);
				}
			}
			if (this.projetsLayout != null) {
				if (this.projetsLayout.isVisible()) {
					this.projetsLayout.setVisible(false);
				}
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EditeurLayout.fxml"));
			editeurLayout = (BorderPane) loader.load();
			
			rootLayout.setCenter(editeurLayout);
			
			EditeurLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch(IOException  e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Projet> getProjetsList() {
		return this.projetsList;
	}
	
	public Projet getProjetIndex(int index) {
		return this.projetsList.get(index);
	}
	
	public boolean projetExists(String name) {
		for (int i = 0; i  < this.projetsList.size(); i++) {
			if  (this.projetsList.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void alertProjetCreation() {
		Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Création de projet");
        alert.setHeaderText("Vous avez créé un nouveau projet");
        Projet projet = this.projetsList.get(this.projetsList.size() - 1);
        String message = "Nom : " + projet.getName() + "\n";
        message += "Dimensions : " + projet.getWidth() + "x" +projet.getHeight();
        alert.setContentText(message);

        alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
