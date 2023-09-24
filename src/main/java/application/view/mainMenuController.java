package application.view;

import application.control.MainMenu;
import application.tools.AlertUtilities;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller JavaFX de la vue du menu principale.
 */
public class MainMenuController {
	
	private MainMenu mM;
	
	// Fenêtre physique ou est la scène contenant le fichier xml contrôlé par this
	private Stage primaryStage;

	/**
	 * Initialisation du contrôleur de vue DailyBankMainFrameController.
	 *
     *@param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _containingStage, MainMenu mM) {
		this.primaryStage = _containingStage;
		this.mM = mM;
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
		this.mM.menuSolo();
	}
	
	@FXML
	private void doMulti() {
		this.mM.menuMulti();
	}
	
	@FXML
	private void doSettings() {
		this.mM.menuSettings();
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
