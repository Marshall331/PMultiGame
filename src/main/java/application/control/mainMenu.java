package application.control;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène du menu principale.
 */
public class MainMenu extends Application {

	// Stage de la fenêtre principale construite par DailyBankMainFrame
	private Stage primaryStage;

	public boolean inGame = false;

	/**
	 * Méthode de démarrage (JavaFX).
	 */
	@Override
	public void start(Stage _parentStage) {

		try {

			DifficultyMenu m = new DifficultyMenu(new Stage());
			m.lancerJeu(1);
			// this.primaryStage = _parentStage;

			// // Chargement du source fxml
			// FXMLLoader loader = new FXMLLoader(
			// MainMenuController.class.getResource("MainMenu.fxml"));
			// BorderPane root = loader.load();

			// // Paramétrage du Stage : feuille de style, titre
			// Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight()
			// + 10);
			// scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

			// if (inGame) {
			// // Placement de la fenêtre au milieu de l'écran
			// Utilities.setCenterStage(primaryStage, scene);
			// }

			// gameConfiguration conf = Utilities.chargerConfiguration();
			// conf.resetScore();
			// Utilities.sauvegarderConfiguration(conf);

			// // Removing all keyEvents
			// Utilities.removeKeysEvents(scene);

			// primaryStage.setScene(scene);
			// primaryStage.setTitle("Menu du jeu");
			// primaryStage.setResizable(false);

			// MainMenuController dbmfcViewController = loader.getController();
			// dbmfcViewController.initContext(primaryStage, this);
			// scene.getStylesheets().add("application.css");

			// dbmfcViewController.displayDialog();

			// // Game g = new Game(primaryStage);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Méthode principale de lancement de l'application.
	 */
	public static void runApp() {
		Application.launch();
	}

	/**
	 * Méthode principale de lancement du menu des choix de la difficulté.
	 */
	public void menuSolo() {
		DifficultyMenu dM = new DifficultyMenu(primaryStage);
	}

	/**
	 * Méthode principale de lancement du menu multijoueur.
	 */
	public void menuMulti() {
		MultiplayerMenu mM = new MultiplayerMenu(primaryStage);
	}

	/**
	 * Méthode principale de lancement de la scène des paramètres.
	 */
	public void menuSettings() {
		SettingsMenu sS = new SettingsMenu(primaryStage, false);
	}
}
