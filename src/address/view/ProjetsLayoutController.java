package address.view;

import address.MainApp;
import address.modele.Projet;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class ProjetsLayoutController {
	
	@FXML
	private TextField projet_nameField;
	@FXML
	private TextField projet_XField;
	@FXML
	private TextField projet_YField;
	@FXML
	private Label projet_nameLabel;
	@FXML
	private Label projet_dimensionLabel;
	
	private MainApp mainApp;
	private Stage stage;
	private int currentProjetsIndex;
	
	public ProjetsLayoutController() {
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
	public void setFields() {
		projet_nameField.setText("");
		projet_XField.setText("");
		projet_YField.setText("");
	}
	public void setProjet(Projet projet, int index) {
		this.projet_nameLabel.setText(projet.getName());
		this.projet_dimensionLabel.setText(projet.getWidth() + "x" + projet.getHeight());
		this.currentProjetsIndex = index;
	}
	
	@FXML
	private void handleCreateProjet() {
		if (isInputValid()) {
			String name = this.projet_nameField.getText();
			int xDim = Integer.parseInt(this.projet_XField.getText());
			int yDim = Integer.parseInt(this.projet_YField.getText());
			Projet projet = new Projet(name, xDim, yDim);
			this.mainApp.getProjetsList().add(projet);
			this.mainApp.alertProjetCreation();
			setFields();
			setProjet(this.mainApp.getProjetsList().get(this.mainApp.getProjetsList().size() - 1), this.mainApp.getProjetsList().size() - 1);
		}
	}
	
	@FXML
	private void handleEditProjet() {
		if (this.currentProjetsIndex != -1) {
			this.mainApp.setCurrentProjet(this.currentProjetsIndex);
			this.mainApp.showEditeur();
			this.mainApp.showEditorCatalogue();
			
		}
		
	}
	
	@FXML
	private void handleLeftButton() {
		if (currentProjetsIndex > 0) {
			this.currentProjetsIndex -= 1;
			setProjet(this.mainApp.getProjetIndex(this.currentProjetsIndex),this.currentProjetsIndex);
		}
		else if (currentProjetsIndex == 0) {
			this.currentProjetsIndex = this.mainApp.getProjetsList().size() - 1;
			setProjet(this.mainApp.getProjetIndex(this.currentProjetsIndex),this.currentProjetsIndex);
		}
	}
	
	@FXML
	private void handleRightButton() {
		if (this.mainApp.getProjetsList().size() != 0) {
			if (currentProjetsIndex < this.mainApp.getProjetsList().size() - 1) {
				this.currentProjetsIndex += 1;
				setProjet(this.mainApp.getProjetIndex(this.currentProjetsIndex),this.currentProjetsIndex);
			}
			else if (currentProjetsIndex == this.mainApp.getProjetsList().size() - 1) {
				this.currentProjetsIndex = 0;
				setProjet(this.mainApp.getProjetIndex(this.currentProjetsIndex),this.currentProjetsIndex);
			}
		}
		
	}
	
	@FXML
	private void handleEditButton() {
		
	}
	
	private boolean isInputValid() {
		String errorMessage = "";
		
		if (this.projet_nameField.getText() == null || this.projet_nameField.getText().length() == 0) {
			errorMessage += "Le nom n'est pas valide!\n";
		}
		if (mainApp.projetExists(this.projet_nameField.getText())) {
			errorMessage += "Ce nom de projet existe déjà!\n";
		}
		if (this.projet_XField.getText() == null || this.projet_XField.getText().length() == 0) {
			errorMessage += "La dimension X est invalide!\n";
		} else {
			try {
				int x = Integer.parseInt(this.projet_XField.getText());
				if (x == 0) {
					errorMessage += "La dimension X est invalide (valuer zéro)";
				}
			} catch (NumberFormatException e) {
				errorMessage += "La dimension X est invalide (doit être une valeure entière)\n";
			}
		}
		if (this.projet_YField.getText() == null || this.projet_YField.getText().length() == 0) {
			errorMessage += "La dimension Y est invalide!\n";
		} else {
			try {
				int y = Integer.parseInt(this.projet_YField.getText());
				if (y == 0) {
					errorMessage += "La dimension Y est invalide (valuer zéro)";
				}
			} catch (NumberFormatException e) {
				errorMessage += "La dimension Y est invalide (doit être une valeure entière)\n";
			}
		}
		
		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Champs invalides");
            alert.setHeaderText("Corrigez les champs invalides");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
		}
	}

}
