package application.view;

import application.control.MainMenu;
import application.control.PlayerSettings;
import application.tools.AlertUtilities;
import application.tools.Animations;
import application.tools.ConfigurationSave;
import application.tools.StageManagement;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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

	/**
	 * Initialisation du contrôleur de vue DailyBankMainFrameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _parentStage, Stage _containingStage, boolean _inGame) {
		this.parentStage = _parentStage;
		this.primaryStage = _containingStage;
		this.inGame = _inGame;

		// this.conf = new gameConfiguration();
		// Utilities.sauvegarderConfiguration(conf);
		this.conf = ConfigurationSave.chargerConfiguration();

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
	private Slider ballSpeed;
	@FXML
	private Label labBallSpeed;

	// @FXML
	// private RadioButton keyboardButt;

	// @FXML
	// private RadioButton mouseButt;

	@FXML
	private ChoiceBox<String> sizeChoice;

	@FXML
	private CheckBox soundBox;

	private void itemsConfigure() {
		Animations.setAnimatedIcon(buttPlayer1);
		Animations.setAnimatedIcon(buttPlayer2);
		DoubleProperty sliderValueProperty = new SimpleDoubleProperty(0.0);
		labBallSpeed.textProperty().bind(sliderValueProperty.asString("%.1f"));
		sliderValueProperty.bind(ballSpeed.valueProperty());

		this.oldGameSize = this.conf.gameSize;

		// ToggleGroup controlChoice = new ToggleGroup();
		// keyboardButt.setToggleGroup(controlChoice);
		// mouseButt.setToggleGroup(controlChoice);

		sizeChoice.getItems().addAll("1700 x 1060", "1300 x 1034", "1043 x 778", "800 x 600", "600 x 400");
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
			// mouseButt.setSelected(true);
		} else {
			// keyboardButt.setSelected(true);
		}
		if (this.conf.soundOn) {
			soundBox.setSelected(true);
			soundBox.setText("Activé");
		} else {
			soundBox.setSelected(false);
			soundBox.setText("Désactivé");
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
		PlayerSettings ps = new PlayerSettings(this.primaryStage, 1);
		StageManagement.disableItems(this.primaryStage.getScene(), false);
		this.conf = ConfigurationSave.chargerConfiguration();
	}

	@FXML
	private void doSettingsPlayer2() {
		StageManagement.disableItems(this.primaryStage.getScene(), true);
		PlayerSettings ps = new PlayerSettings(this.primaryStage, 2);
		StageManagement.disableItems(this.primaryStage.getScene(), false);
		this.conf = ConfigurationSave.chargerConfiguration();
	}

	@FXML
	private void doValider() {
		// if (keyboardButt.isSelected()) {
		// this.conf.player1MouseControl = false;
		// } else if (mouseButt.isSelected()) {
		// this.conf.player1MouseControl = true;
		// }
		if (soundBox.isSelected()) {
			this.conf.soundOn = true;
		} else {
			this.conf.soundOn = false;
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
		ConfigurationSave.sauvegarderConfiguration(this.conf);
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
			ConfigurationSave.sauvegarderConfiguration(conf);
			this.setItemsByConf();
		}
	}
}
