package application.control;

import application.PMultiApp;
import application.tools.Utilitaires;
import application.view.gameController;
import application.view.menuMultiController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène du jeu.
 */
public class game {

	// Stage de la fenêtre précédente
	private Stage primaryStage;

	public game(Stage _parentStage) {

		try {

			this.primaryStage = _parentStage;

			// Chargement du source fxml
			FXMLLoader loader = new FXMLLoader(
					menuMultiController.class.getResource("game.fxml"));
			BorderPane root = loader.load();

			// Paramétrage du Stage : feuille de style, titre
			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
			scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

			// Placement de la fenêtre au milieu de l'écran
			Utilitaires.setCenterStage(primaryStage, scene);

			gameController dbmfcViewController = loader.getController();
			dbmfcViewController.initContext(primaryStage, scene);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Partie en cours");
			primaryStage.setResizable(false);

			dbmfcViewController.displayDialog();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
