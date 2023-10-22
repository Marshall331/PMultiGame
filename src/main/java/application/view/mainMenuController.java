package application.view;

import application.control.DifficultyMenu;
import application.control.MainMenu;
import application.control.MultiplayerMenu;
import application.control.SettingsMenu;
import application.tools.AlertUtilities;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller JavaFX de la vue du menu principale.
 */
public class MainMenuController {

	// Fenêtre physique ou est la scène contenant le fichier xml contrôlé par this
	private Stage primaryStage;

	/**
	 * Initialisation du contrôleur de vue DailyBankMainFrameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _containingStage, MainMenu mM) {
		this.primaryStage = _containingStage;
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
	}

	/**
	 * Affichage de la fenêtre.
	 */
	public void displayDialog() {
		this.primaryStage.show();
	}

	@FXML
	private void doSolo() {
		DifficultyMenu dM = new DifficultyMenu(primaryStage);
	}

	@FXML
	private void doMulti() {
		MultiplayerMenu mM = new MultiplayerMenu(primaryStage);
	}

	@FXML
	private void doSettings() {
		SettingsMenu sS = new SettingsMenu(primaryStage, false);
		sS.startMenu();
	}

	/*
	 * Action menu quitter. Demander une confirmation puis fermer la fenêtre (donc
	 * l'application car fenêtre principale).
	 */
	@FXML
	private void doQuit() {
		this.primaryStage.close();
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
