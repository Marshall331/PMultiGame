package application.view;

import application.tools.AlertUtilities;
import application.tools.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.gameConfiguration;

/**
 * Controller JavaFX de la vue des choix de la difficulté
 */
public class PlayerSettingsController {

	// Fenêtre physique ou est la scène contenant le fichier xml contrôlé par this
	private Stage primaryStage;

	public gameConfiguration conf;

	public boolean inGame;
	public int oldGameSize;
	private int playerSettings;

	/**
	 * Initialisation du contrôleur de vue DailyBankMainFrameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _containingStage, int _player) {
		this.primaryStage = _containingStage;
		this.playerSettings = _player;

		// this.primaryStage.requestFocus();
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
		this.primaryStage.showAndWait();
	}

	@FXML
	private Label labPlayer;

	@FXML
	private RadioButton keyboardButt;

	@FXML
	private RadioButton mouseButt;

	@FXML
	private RadioButton botButt;

	@FXML
	private Slider paddleSpeed;

	@FXML
	private CheckBox isSpeedLimited;

	private void itemsConfigure() {
		this.labPlayer.setText("Joueur " + (this.playerSettings == 1 ? "1" : "2"));
		ToggleGroup controlChoice = new ToggleGroup();
		keyboardButt.setToggleGroup(controlChoice);
		mouseButt.setToggleGroup(controlChoice);
		botButt.setToggleGroup(controlChoice);
	}

	private void setItemsByConf() {
		if (this.playerSettings == 1) {
			if (this.conf.player1MouseControl) {
				mouseButt.setSelected(true);
			} else if (this.conf.player1isComputer) {
				botButt.setSelected(true);
			} else {
				keyboardButt.setSelected(true);
			}
		} else {
			if (this.conf.player2MouseControl) {
				mouseButt.setSelected(true);
			} else if (this.conf.player2isComputer) {
				botButt.setSelected(true);
			} else {
				keyboardButt.setSelected(true);
			}
		}
	}

	@FXML
	private void doValider() {
		if (this.playerSettings == 1) {
			if (keyboardButt.isSelected()) {
				this.conf.player1MouseControl = false;
				this.conf.player1isComputer = false;
			} else if (mouseButt.isSelected()) {
				this.conf.player1MouseControl = true;
				this.conf.player1isComputer = false;
			} else if (botButt.isSelected()) {
				this.conf.player1MouseControl = false;
				this.conf.player1isComputer = true;
			}
		} else {
			if (keyboardButt.isSelected()) {
				this.conf.player2MouseControl = false;
				this.conf.player2isComputer = false;
			} else if (mouseButt.isSelected()) {
				this.conf.player2MouseControl = true;
				this.conf.player2isComputer = false;
			} else if (botButt.isSelected()) {
				this.conf.player2MouseControl = false;
				this.conf.player2isComputer = true;
			}
		}
		this.conf.setSizeValues();
		Utilities.sauvegarderConfiguration(this.conf);
		this.doRetour();
		System.out.println(conf.player2isComputer);
	}

	/*
	 * Action menu retour. Retourne à la fenêtre précédente.
	 */
	@FXML
	private void doRetour() {
		this.primaryStage.close();
	}

	@FXML
	private void doReset() {
		if (AlertUtilities.confirmYesCancel(this.primaryStage, "Réinitialiser les paramètres ?",
				"Voulez vous vraiment réinitialiser les paramètres du " + this.labPlayer.getText(),
				null, AlertType.CONFIRMATION)) {
			this.conf = new gameConfiguration();
			Utilities.sauvegarderConfiguration(conf);
			this.setItemsByConf();
		}
	}
}
