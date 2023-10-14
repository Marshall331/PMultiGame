package application.view;

import java.text.DecimalFormat;

import application.tools.AlertUtilities;
import application.tools.ConfigurationSave;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
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
	private int playerId;

	public double maxSpeed;
	public boolean isComputer;
	public boolean mouseControl;
	public int paddleSize;

	/**
	 * Initialisation du contrôleur de vue DailyBankMainFrameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _containingStage, int _playerId) {
		this.primaryStage = _containingStage;
		this.playerId = _playerId;

		// this.primaryStage.requestFocus();
		// this.conf = new gameConfiguration();
		// Utilities.sauvegarderConfiguration(conf);
		this.conf = ConfigurationSave.chargerConfiguration();

		initPlayerSettings();
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
	private Slider sliderPaddleSpeed;

	@FXML
	private Label labPaddleSpeed;

	@FXML
	private Label labPaddleSize;

	@FXML
	private Slider sliderPaddleSize;

	private void initPlayerSettings() {
		if (this.playerId == 1) {
			this.isComputer = this.conf.player1isComputer;
			this.mouseControl = this.conf.player1MouseControl;
			this.maxSpeed = this.conf.player1MaxSpeed;
			this.paddleSize = this.conf.player1PaddleSize;
		} else {
			this.isComputer = this.conf.player2isComputer;
			this.mouseControl = this.conf.player2MouseControl;
			this.maxSpeed = this.conf.player2MaxSpeed;
			this.paddleSize = this.conf.player2PaddleSize;
		}
	}

	private void itemsConfigure() {

		ToggleGroup controlChoice = new ToggleGroup();
		keyboardButt.setToggleGroup(controlChoice);
		mouseButt.setToggleGroup(controlChoice);
		botButt.setToggleGroup(controlChoice);

		keyboardButt.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (this.sliderPaddleSpeed.getValue() == 50) {
				this.labPaddleSpeed.setText("50");
			}
		});
		mouseButt.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (this.sliderPaddleSpeed.getValue() == 50) {
				this.labPaddleSpeed.setText("50");
			}
		});
		botButt.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (this.sliderPaddleSpeed.getValue() == 50) {
				this.labPaddleSpeed.setText("infini");
			}
		});

		sliderPaddleSpeed.valueProperty().addListener((observable, oldValue, newValue) -> {
			Double speed = newValue.doubleValue();
			DecimalFormat decimalFormat = new DecimalFormat("#.#"); // Format avec un seul chiffre après la virgule
			this.labPaddleSpeed.setText(decimalFormat.format(speed));

			if (speed == 50) {
				if (this.botButt.isSelected()) {
					labPaddleSpeed.setText("infini");
				}
			}
		});

		sliderPaddleSize.valueProperty().addListener((observable, oldValue, newValue) -> {
			int size = newValue.intValue();
			this.labPaddleSize.setText(Integer.toString(size));
		});
	}

	private void setItemsByConf() {

		this.labPlayer.setText("Joueur " + this.playerId);
		this.labPaddleSpeed.setText("" + this.maxSpeed);
		this.labPaddleSize.setText("" + this.paddleSize);

		if (this.mouseControl) {
			mouseButt.setSelected(true);
		} else if (this.isComputer) {
			botButt.setSelected(true);
		} else {
			keyboardButt.setSelected(true);
		}
		this.sliderPaddleSpeed.setValue(this.maxSpeed);
		this.sliderPaddleSize.setValue(this.paddleSize);
	}

	@FXML
	private void doValider() {

		boolean allOK = true;

		if (keyboardButt.isSelected()) {
			this.mouseControl = false;
			this.isComputer = false;
		} else if (mouseButt.isSelected()) {
			if (this.conf.player2MouseControl) {
				AlertUtilities.showAlert(primaryStage, "Option impossible",
						"La souris est déjà attribuée au joueur 1.", null, AlertType.INFORMATION);
				allOK = false;
			} else {
				this.mouseControl = true;
				this.isComputer = false;
			}
		} else if (botButt.isSelected()) {
			this.mouseControl = false;
			this.isComputer = true;
		}

		this.maxSpeed = this.sliderPaddleSpeed.getValue() == 50 && this.botButt.isSelected() ? 1000
				: this.sliderPaddleSpeed.getValue();
		this.paddleSize = Integer.valueOf(this.labPaddleSize.getText());

		this.setNewSettings();
		ConfigurationSave.sauvegarderConfiguration(this.conf);

		if (allOK) {
			ConfigurationSave.sauvegarderConfiguration(conf);
			this.conf = ConfigurationSave.chargerConfiguration();
			this.doRetour();
		}
	}

	/*
	 * Action menu retour. Retourne à la fenêtre précédente.
	 */
	@FXML
	private void doRetour() {
		this.primaryStage.close();
		System.out.println("PADDLESIZE 1: " + this.conf.player1PaddleSize);
		System.out.println("PADDLESIZE 2: " + this.conf.player2PaddleSize);
	}

	@FXML
	private void doReset() {
		if (AlertUtilities.confirmYesCancel(this.primaryStage, "Réinitialiser les paramètres ?",
				"Voulez vous vraiment réinitialiser les paramètres du " + this.labPlayer.getText(),
				null, AlertType.CONFIRMATION)) {
			this.resetPlayerSettings();
		}
	}

	private void setNewSettings() {
		this.conf.setNewPlayerSettings(playerId, maxSpeed, mouseControl, isComputer, paddleSize);
	}

	public void resetPlayerSettings() {
		this.maxSpeed = 10;
		this.mouseControl = false;
		this.isComputer = this.playerId == 1 ? false : true;
		this.paddleSize = 140;
		this.setItemsByConf();
	}
}
