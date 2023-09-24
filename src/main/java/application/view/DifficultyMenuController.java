package application.view;

import application.control.MainMenu;
import application.control.DifficultyMenu;
import application.tools.AlertUtilities;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller JavaFX de la vue des choix de la difficulté
 */
public class DifficultyMenuController {

	// Fenêtre physique ou est la scène contenant le fichier xml contrôlé par this
	private Stage primaryStage;

	/**
	 * Initialisation du contrôleur de vue DailyBankMainFrameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _containingStage) {
		this.primaryStage = _containingStage;
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
	}

	/**
	 * Affichage de la fenêtre.
	 */
	public void displayDialog() {
		this.primaryStage.show();
	}

	/*
	 * Action menu qui lance le jeu.
	 */
	@FXML
	private void doLancerFacile() {
		this.primaryStage.close();
		DifficultyMenu m = new DifficultyMenu(primaryStage);
		m.lancerJeu(1);
	}
	
	/*
	 * Action menu qui lance le jeu.
	 */
	@FXML
	private void doLancerNormal() {
		this.primaryStage.close();
		DifficultyMenu m = new DifficultyMenu(primaryStage);
		m.lancerJeu(1);
	}
	
	/*
	 * Action menu qui lance le jeu.
	 */
	@FXML
	private void doLancerGuychel() {
		this.primaryStage.close();
		DifficultyMenu m = new DifficultyMenu(primaryStage);
		m.lancerJeu(1);
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
