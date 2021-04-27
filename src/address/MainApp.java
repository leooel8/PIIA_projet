package address;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import address.view.AccueilLayoutController;
import address.view.CatalogueEditorLayoutController;
import address.view.CatalogueLayoutController;
import address.view.EditeurLayoutController;
import address.view.ProjetsLayoutController;
import address.view.RootLayerController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import address.modele.Item;
import address.modele.Projet;
import address.modele.Type;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private AnchorPane accueilLayout;
	private BorderPane editeurLayout;
	private AnchorPane projetsLayout;
	private AnchorPane catalogueLayout;
	private ArrayList<Projet> projetsList;
	private int currentProjetIndex;
	
	
	
	ArrayList<Type> typesListe= new ArrayList<Type>();
	/** The datas as  observables lists of categories.*/
	private ObservableList<Type> electromenagerData = FXCollections.observableArrayList();
	private ObservableList<Type> mobilierData = FXCollections.observableArrayList();
	private ObservableList<Type> lumiereEtDecorationData = FXCollections.observableArrayList();

	public MainApp() throws MalformedURLException{
		chargeImagesEtTypes();
	}
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("KitchenApp");
		this.projetsList = new ArrayList<Projet>(0);
		this.projetsList.add(initExampleProjet());
		
		initRootLayout();
//		showEditorCatalogue();
		showAccueil();
	}
	
	private void chargeImagesEtTypes() throws MalformedURLException {
		
		File dossierPrincipal = new File("images/");  //on va dans le dossier images
		String [] dossierCategories = dossierPrincipal.list (); 
		for(int c=0;c<dossierCategories.length;c++) { //pour chaques categories	
			String categorie = dossierCategories[c]; 	//on recupere le nom de la categorie
			File dossierCatX = new File("images/"+categorie+"/");
			String [] dossierType = dossierCatX.list (); 
			for(int i=0;i<dossierType.length;i++) { //pour chaques types d'items
				String type = dossierType[i]; //on recupere le nom du type
				File dossierTypeX = new File("images/"+categorie+"/"+type+"/");
				String [] images = dossierTypeX.list (); 
				ArrayList<Item> lesItems = new ArrayList<Item>();
				for (String item : images) {
					String nomImage = item.substring(0, item.length()-4); // on enleve le ".png" du nom
					String url = "images/"+categorie+"/"+type+"/"+item;
					lesItems.add(new Item(nomImage, 200, 200, 0, 0, url));//on rajoute l'image de l'item dans l'arrayList items	
				}					
				typesListe.add(new Type(type,lesItems));
				if(categorie.length()==14) 
					electromenagerData.add(new Type(type,lesItems));				
				else if(categorie.length()==8) 
					mobilierData.add(new Type(type,lesItems));
				else if(categorie.length()==19) 
					lumiereEtDecorationData.add(new Type(type,lesItems));
			}
		}
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
	


public ObservableList<Type> getElectromenagerData() {
	return electromenagerData;
}

public ObservableList<Type> getMobilierData() {
	return mobilierData;
}

public ObservableList<Type> getLumiereEtDecorationData() {
	return lumiereEtDecorationData;
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
			if(this.editeurLayout!=null) {
				if (this.editeurLayout.isVisible()) {
					this.editeurLayout.setVisible(false);
				}
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EditeurLayout.fxml"));
			editeurLayout = (BorderPane) loader.load();
			
			rootLayout.setCenter(editeurLayout);
			
			EditeurLayoutController controller = loader.getController();
			controller.setMainApp(this);
			controller.setCurrentProjet(this.getProjetIndex(currentProjetIndex));
			controller.setCanvas();			
		
		} catch(IOException  e) {
			e.printStackTrace();
		}
	}
	
	public void showCatalogue() {
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
			if (this.editeurLayout != null) {
				if (this.editeurLayout.isVisible()) {
					this.editeurLayout.setVisible(false);
				}
			}
			FXMLLoader loader = new FXMLLoader();	
			loader.setLocation(MainApp.class.getResource("view/CatalogueLayout.fxml"));
			 catalogueLayout = (AnchorPane)loader.load();			
			rootLayout.setCenter(catalogueLayout);			
			CatalogueLayoutController controller = loader.getController();
			controller.setMainApp(this);			
			controller.setCanvas();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showEditorCatalogue() {
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
			loader.setLocation(MainApp.class.getResource("view/CatalogueEditorLayout.fxml"));
			 catalogueLayout = (AnchorPane)loader.load();			
			rootLayout.setBottom(catalogueLayout);			
			CatalogueEditorLayoutController controller = loader.getController();
			controller.setMainApp(this);			
			controller.setCanvas();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Type> getTypesList(){ return this.typesListe;}
	
	
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
	
	public void setCurrentProjet (int index) {
		this.currentProjetIndex = index;
	}
	
	public void alertProjetCreation() {
		Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Cr�ation de projet");
        alert.setHeaderText("Vous avez cr�� un nouveau projet");
        Projet projet = this.projetsList.get(this.projetsList.size() - 1);
        String message = "Nom : " + projet.getName() + "\n";
        message += "Dimensions : " + projet.getWidth() + "x" +projet.getHeight();
        alert.setContentText(message);

        alert.showAndWait();
	}
	
	public Projet initExampleProjet() throws MalformedURLException {
		Projet res  = new Projet ("Example project", 10, 10);
		
		String url = "src/address/Images/Default/ExempleItem.png";
		Item item_1 = new Item("Table_cuisson", 0, 0, 20, 15, url);
		Item item_2 = new Item("Plan de travail", 50, 50, 70, 20, url);
		Item item_3 = new Item("Frigo", 100, 100, 10, 10, url);
		Item item_4 = new Item("Four", 150, 200, 30, 30, url);
		
		res.addItem(item_1);
		res.addItem(item_2);
		res.addItem(item_3);
		res.addItem(item_4);
		
		return res;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
