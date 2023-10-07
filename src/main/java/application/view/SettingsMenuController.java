package application.view;

import application.control.MainMenu;
import application.control.PlayerSettings;
import application.tools.AlertUtilities;
import application.tools.Utilities;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
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
		Utilities.setAnimatedIcon(buttPlayer1);
		Utilities.setAnimatedIcon(buttPlayer2);
		DoubleProperty sliderValueProperty = new SimpleDoubleProperty(0.0);
		labBallSpeed.textProperty().bind(sliderValueProperty.asString("%.1f"));
		sliderValueProperty.bind(ballSpeed.valueProperty());

		this.oldGameSize = this.conf.gameSize;

		// ToggleGroup controlChoice = new ToggleGroup();
		// keyboardButt.setToggleGroup(controlChoice);
		// mouseButt.setToggleGroup(controlChoice);

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
			sizeChoice.setValue("1024 x 768");
		} else if (this.conf.gameSize == 2) {
			sizeChoice.setValue("1280 x 1024");
		} else {
			sizeChoice.setValue("1680 x 1050");
		}
	}

	@FXML
	private void doSettingsPlayer1() {
		this.buttPlayer1.setDisable(true);
		PlayerSettings ps = new PlayerSettings(this.primaryStage, 1);
		this.buttPlayer1.setDisable(false);
		this.conf = Utilities.chargerConfiguration();
	}

	@FXML
	private void doSettingsPlayer2() {
		this.buttPlayer2.setDisable(true);
		PlayerSettings ps = new PlayerSettings(this.primaryStage, 2);
		this.buttPlayer2.setDisable(false);
		this.conf = Utilities.chargerConfiguration();
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
}
