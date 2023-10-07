package application.view;

import application.control.Game;
import application.control.MainMenu;
import application.tools.AlertUtilities;
import application.tools.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.gameConfiguration;

/**
 * Controller JavaFX de la vue des choix de la difficulté
 */
public class SettingsMenuController {

	// Fenêtre physique ou est la scène contenant le fichier xml contrôlé par this
	private Stage primaryStage;

	private Stage parentStage;

	public gameConfiguration conf;

	public boolean inGame;
	public int oldGameSize;

	/**
	 * Initialisation du contrôleur de vue DailyBankMainFrameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _parentStage, Stage _containingStage, boolean _inGame) {
		this.parentStage = _parentStage;
		this.primaryStage = _containingStage;
		this.inGame = _inGame;

		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
		// this.conf = new gameConfiguration();
		// Utilities.sauvegarderConfiguration(conf);
		this.conf = Utilities.chargerConfiguration();

		itemsConfigure();

		setItemsByConf();
	}

	/**
	 * Affichage de la fenêtre.
	 */
	public void displayDialog() {
		if (this.inGame) {
			this.primaryStage.showAndWait();
		} else {
			this.primaryStage.show();
		}
	}

	@FXML
	private RadioButton keyboardButt;

	@FXML
	private RadioButton mouseButt;

	@FXML
	private ChoiceBox<String> sizeChoice;

	@FXML
	private CheckBox soundBox;

	private void itemsConfigure() {
		this.oldGameSize = this.conf.gameSize;

		ToggleGroup controlChoice = new ToggleGroup();
		keyboardButt.setToggleGroup(controlChoice);
		mouseButt.setToggleGroup(controlChoice);

		sizeChoice.getItems().addAll("1024 x 768", "1280 x 1024", "1680 x 1050");
		sizeChoice.setStyle("-fx-font-size: 18px;");

		this.soundBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				soundBox.setText("Activé");
			} else {
				soundBox.setText("Désactivé");
			}
		});
	}

	private void setItemsByConf() {
		if (this.conf.player1MouseControl) {
			mouseButt.setSelected(true);
		} else {
			keyboardButt.setSelected(true);
		}
		if (this.conf.soundOn) {
			soundBox.setSelected(true);
			soundBox.setText("Activé");
		} else {
			soundBox.setSelected(false);
			soundBox.setText("Désactivé");
		}
		if (this.conf.gameSize == 1) {
			sizeChoice.setValue("1024 x 768");
		} else if (this.conf.gameSize == 2) {
			sizeChoice.setValue("1280 x 1024");
		} else {
			sizeChoice.setValue("1680 x 1050");
		}
	}

	@FXML
	private void doValider() {
		if (keyboardButt.isSelected()) {
			this.conf.player1MouseControl = false;
		} else if (mouseButt.isSelected()) {
			this.conf.player1MouseControl = true;
		}
		if (soundBox.isSelected()) {
			this.conf.soundOn = true;
		} else {
			this.conf.soundOn = false;
		}
		if (sizeChoice.getValue().equals("1024 x 768")) {
			this.conf.gameSize = 1;
		} else if (sizeChoice.getValue().equals("1280 x 1024")) {
			this.conf.gameSize = 2;
		} else if (sizeChoice.getValue().equals("1680 x 1050")) {
			this.conf.gameSize = 3;
		}
		this.conf.setSizeValues();
		Utilities.sauvegarderConfiguration(this.conf);
		this.doRetour();
	}

	/*
	 * Action menu retour. Retourne à la fenêtre précédente.
	 */
	@FXML
	private void doRetour() {
		if (this.inGame) {
			this.primaryStage.close();
		} else {
			MainMenu mM = new MainMenu();
			mM.start(primaryStage);
		}
	}

	@FXML
	private void doReset() {
		if (AlertUtilities.confirmYesCancel(this.primaryStage, "Réinitialiser les paramètres ?",
				"Voulez vous vraiment réinitialiser les paramètres du jeu ?",
				null, AlertType.CONFIRMATION)) {
			this.conf = new gameConfiguration();
			Utilities.sauvegarderConfiguration(conf);
			this.setItemsByConf();
		}
	}

	/*
	 * Méthode de fermeture de la fenêtre par la croix.
	 *
	 * @param e Evénement associé (inutilisé pour le moment)
	 *
	 */
	private void closeWindow(WindowEvent e) {
		if (AlertUtilities.confirmYesCancel(this.primaryStage, "Quitter l'application",
				"Etes vous sur de vouloir quitter le jeu ?", null, AlertType.CONFIRMATION)) {
			this.primaryStage.close();
		}
		e.consume();
	}
}
