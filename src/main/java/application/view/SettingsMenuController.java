package application.view;

import application.control.GameSpeedSettings;
import application.control.MainMenu;
import application.control.PlayerSettings;
import application.tools.AlertUtilities;
import application.tools.Animations;
import application.tools.ConfigurationSave;
import application.tools.StageManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
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
	public boolean oldSoundSetting;

	public PlayerSettings playerSettings;
	public GameSpeedSettings gameSpeedSettings;

	/**
	 * Initialisation du contrôleur de vue DailyBankMainFrameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _parentStage, Stage _containingStage, boolean _inGame) {
		this.parentStage = _parentStage;
		this.primaryStage = _containingStage;
		this.primaryStage.setOnCloseRequest(e -> this.doRetour());

		this.inGame = _inGame;

		this.conf = ConfigurationSave.chargerConfiguration();
		this.conf.isConfHasChanged = false;
		ConfigurationSave.sauvegarderConfiguration(conf);
		this.oldGameSize = conf.gameSize;
		this.oldSoundSetting = conf.isSoundOn;

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
	private Button buttPlayer1;

	@FXML
	private Button buttPlayer2;

	@FXML
	private Button buttBallSettings;

	@FXML
	private Label labSound;

	@FXML
	private ChoiceBox<String> sizeChoice;

	@FXML
	private CheckBox soundBox;

	private void itemsConfigure() {
		Animations.setAnimatedIcon(buttPlayer1, 1.2, "black");
		Animations.setAnimatedIcon(buttPlayer2, 1.2, "black");
		Animations.setAnimatedIcon(buttBallSettings, 1.2, "black");

		sizeChoice.getItems().addAll("1700 x 1060", "1300 x 1034", "1043 x 778", "800 x 600", "600 x 400");
		sizeChoice.setStyle("-fx-font-size: 18px;");

		this.soundBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				labSound.setText("Activé");
			} else {
				labSound.setText("Désactivé");
			}
		});
	}

	private void setItemsByConf() {
		if (this.conf.isSoundOn) {
			soundBox.setSelected(true);
			labSound.setText("Activé");
		} else {
			soundBox.setSelected(false);
			labSound.setText("Désactivé");
		}
		if (this.conf.gameSize == 1) {
			sizeChoice.setValue("1700 x 1060");
		} else if (this.conf.gameSize == 2) {
			sizeChoice.setValue("1300 x 1034");
		} else if (this.conf.gameSize == 3) {
			sizeChoice.setValue("1043 x 778");
		} else if (this.conf.gameSize == 4) {
			sizeChoice.setValue("800 x 600");
		} else if (this.conf.gameSize == 5) {
			sizeChoice.setValue("600 x 400");
		}
	}

	@FXML
	private void doSettingsPlayer1() {
		StageManagement.disableItems(this.primaryStage.getScene(), true);
		playerSettings = new PlayerSettings(this.primaryStage, 1);
		playerSettings.startMenu();
		StageManagement.disableItems(this.primaryStage.getScene(), false);
		this.conf = ConfigurationSave.chargerConfiguration();
	}

	@FXML
	private void doSettingsPlayer2() {
		StageManagement.disableItems(this.primaryStage.getScene(), true);
		playerSettings = new PlayerSettings(this.primaryStage, 2);
		playerSettings.startMenu();
		StageManagement.disableItems(this.primaryStage.getScene(), false);
		this.conf = ConfigurationSave.chargerConfiguration();
	}

	@FXML
	private void doSettingsGameSpeed() {
		StageManagement.disableItems(this.primaryStage.getScene(), true);
		gameSpeedSettings = new GameSpeedSettings(this.primaryStage);
		gameSpeedSettings.startMenu();
		StageManagement.disableItems(this.primaryStage.getScene(), false);
		this.conf = ConfigurationSave.chargerConfiguration();
	}

	@FXML
	private void doValider() {
		if (soundBox.isSelected()) {
			this.conf.isSoundOn = true;
		} else {
			this.conf.isSoundOn = false;
		}
		if (sizeChoice.getValue().equals("600 x 400")) {
			this.conf.gameSize = 5;
		} else if (sizeChoice.getValue().equals("800 x 600")) {
			this.conf.gameSize = 4;
		} else if (sizeChoice.getValue().equals("1043 x 778")) {
			this.conf.gameSize = 3;
		} else if (sizeChoice.getValue().equals("1300 x 1034")) {
			this.conf.gameSize = 2;
		} else if (sizeChoice.getValue().equals("1700 x 1060")) {
			this.conf.gameSize = 1;
		}
		this.conf.setSizeValues();
		this.checkSettingsChanged();
		ConfigurationSave.sauvegarderConfiguration(this.conf);
		this.doRetour();
	}

	private void checkSettingsChanged() {
		if (this.oldSoundSetting != this.conf.isSoundOn || this.oldGameSize != this.conf.gameSize) {
			this.conf.isConfHasChanged = true;
		}
	}

	/*
	 * Action menu retour. Retourne à la fenêtre précédente.
	 */
	@FXML
	private void doRetour() {
		if (this.playerSettings != null) {
			this.playerSettings.closeMenu();
		}
		if (this.gameSpeedSettings != null) {
			this.gameSpeedSettings.closeMenu();
		}
		if (this.inGame) {
			ConfigurationSave.sauvegarderConfiguration(this.conf);
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
			this.setItemsByConf();
		}
	}
}