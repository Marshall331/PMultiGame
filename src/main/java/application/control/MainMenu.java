package application.control;

import application.PMultiApp;
import application.tools.StageManagement;
import application.tools.ConfigurationSave;
import application.view.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.gameConfiguration;

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

			// DifficultyMenu m = new DifficultyMenu(new Stage());
			// m.lancerJeu(1);
			if (inGame) {
				// Utilities.setCenterStage(primaryStage, scene);
				this.primaryStage = new Stage();
				StageManagement.manageCenteringStage(primaryStage, _parentStage);
			} else {
				this.primaryStage = _parentStage;
			}

			// Chargement du source fxml
			FXMLLoader loader = new FXMLLoader(
					MainMenuController.class.getResource("MainMenu.fxml"));
			BorderPane root = loader.load();

			// Paramétrage du Stage : feuille de style, titre
			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight()
					+ 10);
			scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

			gameConfiguration conf = ConfigurationSave.chargerConfiguration();
			conf.resetScore();
			ConfigurationSave.sauvegarderConfiguration(conf);

			// Removing all keyEvents
			StageManagement.removeKeysEvents(scene);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Menu du jeu");
			primaryStage.setResizable(false);

			MainMenuController dbmfcViewController = loader.getController();
			dbmfcViewController.initContext(primaryStage, this);
			scene.getStylesheets().add("application.css");

			dbmfcViewController.displayDialog();

			// Game g = new Game(primaryStage);

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
}
