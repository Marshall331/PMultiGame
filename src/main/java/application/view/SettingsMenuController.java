package application.view;

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

	public gameConfiguration conf;

	/**
	 * Initialisation du contrôleur de vue DailyBankMainFrameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _containingStage) {
		this.primaryStage = _containingStage;
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
		this.conf = Utilities.chargerConfiguration();

		ToggleGroup controlChoice = new ToggleGroup();
		keyboardButt.setToggleGroup(controlChoice);
		mouseButt.setToggleGroup(controlChoice);

		sizeChoice.getItems().addAll("1024 x 768", "1280 x 1024", "1680 x 1050");
		sizeChoice.setStyle("-fx-font-size: 18px;");

		if (this.conf.soundOn) {
			soundBox.setSelected(true);
			soundBox.setText("Activé");
		} else {
			soundBox.setSelected(false);
			soundBox.setText("Désactivé");
		}
		this.soundBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				soundBox.setText("Activé");
				this.conf.soundOn = true;
			} else {
				soundBox.setText("Désactivé");
				this.conf.soundOn = false;
			}
		});
	}

	/**
	 * Affichage de la fenêtre.
	 */
	public void displayDialog() {
		this.primaryStage.show();
	}

	@FXML
	private RadioButton keyboardButt;

	@FXML
	private RadioButton mouseButt;

	@FXML
	private ChoiceBox sizeChoice;

	@FXML
	private CheckBox soundBox;

	@FXML
	private gameConfiguration doValider() {
		System.out.println("clavier " + conf.controlPlayer1Keyboard);
		System.out.println("son " + conf.soundOn);
		if (keyboardButt.isPressed()) {
			this.conf.controlPlayer1Keyboard = true;
		} else if (mouseButt.isPressed()) {
			this.conf.controlPlayer1Keyboard = false;
		}
		Utilities.sauvegarderConfiguration(this.conf);
		return conf;
	}

	/*
	 * Action menu retour. Retourne à la fenêtre précédente.
	 */
	@FXML
	private void doRetour() {
		MainMenu mM = new MainMenu();
		mM.start(primaryStage);
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
