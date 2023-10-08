package application.view;

import application.tools.AlertUtilities;
import application.tools.Utilities;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

	@FXML
	private Label labPaddleSpeed;

	private void itemsConfigure() {
		this.isSpeedLimited.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				isSpeedLimited.setText("Oui");
			} else {
				isSpeedLimited.setText("Non");
			}
		});
		this.labPlayer.setText("Joueur " + this.playerSettings);
		ToggleGroup controlChoice = new ToggleGroup();
		keyboardButt.setToggleGroup(controlChoice);
		mouseButt.setToggleGroup(controlChoice);
		botButt.setToggleGroup(controlChoice);

		this.isSpeedLimited.setSelected(false);
		DoubleProperty sliderValueProperty = new SimpleDoubleProperty(0.0);
		labPaddleSpeed.textProperty().bind(sliderValueProperty.asString("%.1f"));
		sliderValueProperty.bind(paddleSpeed.valueProperty());

		// if (this.paddleSpeed.getValue() == 100) {
		// this.labSpeed.setText("illimité");
		// }
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
			if (this.conf.player1isSpeedLimited) {
				this.isSpeedLimited.setSelected(true);
			}
		} else {
			if (this.conf.player2MouseControl) {
				mouseButt.setSelected(true);
			} else if (this.conf.player2isComputer) {
				botButt.setSelected(true);
			} else {
				keyboardButt.setSelected(true);
			}
			if (this.conf.player2isSpeedLimited) {
				this.isSpeedLimited.setSelected(true);
			}
		}
		this.paddleSpeed.setValue(this.playerSettings == 1 ? this.conf.player1MaxSpeed : this.conf.player2MaxSpeed);
	}

	@FXML
	private void doValider() {
		boolean allOK = true;
		if (this.playerSettings == 1) {
			if (keyboardButt.isSelected()) {
				this.conf.player1MouseControl = false;
				this.conf.player1isComputer = false;
			} else if (mouseButt.isSelected()) {
				if (this.conf.player2MouseControl) {
					AlertUtilities.showAlert(primaryStage, "Option impossible",
							"La souris est déjà attribuée au joueur 1.", null, AlertType.INFORMATION);
					allOK = false;
				} else {
					this.conf.player1MouseControl = true;
					this.conf.player1isComputer = false;
				}
			} else if (botButt.isSelected()) {
				this.conf.player1MouseControl = false;
				this.conf.player1isComputer = true;
			}
			this.conf.player1MaxSpeed = this.paddleSpeed.getValue();
			if (this.isSpeedLimited.isSelected()) {
				this.conf.player1isSpeedLimited = true;
			} else {
				this.conf.player1isSpeedLimited = false;
			}
		} else {
			if (keyboardButt.isSelected()) {
				this.conf.player2MouseControl = false;
				this.conf.player2isComputer = false;
			} else if (mouseButt.isSelected()) {
				if (this.conf.player1MouseControl) {
					AlertUtilities.showAlert(primaryStage, "Option impossible",
							"La souris est déjà attribuée au joueur 1.", null, AlertType.INFORMATION);
					allOK = false;
				} else {
					this.conf.player2MouseControl = true;
					this.conf.player2isComputer = false;
				}
			} else if (botButt.isSelected()) {
				this.conf.player2MouseControl = false;
				this.conf.player2isComputer = true;
			}
			if (this.isSpeedLimited.isSelected()) {
				this.conf.player2isSpeedLimited = true;
			} else {
				this.conf.player2isSpeedLimited = false;
			}
			this.conf.player2MaxSpeed = this.paddleSpeed.getValue();
		}
		this.conf.setSizeValues();
		Utilities.sauvegarderConfiguration(this.conf);
		if (allOK) {
			this.doRetour();
		}
		System.out.println("Player 1 mouse : " + this.conf.player1MouseControl);
		System.out.println("Player 1 CPU : " + this.conf.player1isComputer);
		System.out.println("Player 2 mouse : " + this.conf.player2MouseControl);
		System.out.println("Player 2 CPU : " + this.conf.player2isComputer);
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
			this.resetPlayerSettings();
			this.setItemsByConf();
		}
	}

	private void resetPlayerSettings() {
		if (this.playerSettings == 1) {
			this.conf.player1MaxSpeed = 10;
			this.conf.player1MouseControl = false;
			this.conf.player1isComputer = false;
			this.conf.player1isSpeedLimited = false;
		} else {
			this.conf.player2MaxSpeed = 10;
			this.conf.player2MouseControl = false;
			this.conf.player2isComputer = false;
			this.conf.player2isSpeedLimited = false;
		}
		this.setItemsByConf();
	}
}
