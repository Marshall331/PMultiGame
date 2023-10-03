package application.control;

import application.PMultiApp;
import application.tools.Utilities;
import application.view.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.gameConfiguration;

/**
 * Classe de controleur de Dialogue de la scène du jeu.
 */
public class Game {

	// Stage de la fenêtre précédente
	private Stage primaryStage;

	public Game(Stage _parentStage) {

		try {
			this.primaryStage = _parentStage;

			// Chargement du source fxml
			gameConfiguration conf = Utilities.chargerConfiguration();
			String sizeGame;
			switch (conf.gameSize) {
				case 1:
					sizeGame = "Game1.fxml";
					break;
				case 2:
					sizeGame = "Game2.fxml";
					break;
				case 3:
					sizeGame = "Game3.fxml";
					break;
				default:
					sizeGame = "";
					break;
			}

			FXMLLoader loader = new FXMLLoader(
					GameController.class.getResource(sizeGame));
			BorderPane root = loader.load();

			// Paramétrage du Stage : feuille de style, titre
			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
			scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());
			
			Utilities.setCenterStage(primaryStage, scene);

			GameController dbmfcViewController = loader.getController();
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

	public void setWindowSize(int _size) {

	}
}
