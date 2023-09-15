package application.control;

import application.PMultiApp;
import application.view.gameController;
import application.view.menuMultiController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
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

			// Détection et déplacement de la fenêtre si nécessaire
			primaryStage.setOnShown(event -> {
				Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
				double x = primaryStage.getX();
				double y = primaryStage.getY();
				double width = primaryStage.getWidth();
				double height = primaryStage.getHeight();

				if (x < screenBounds.getMinX()) {
					x = screenBounds.getMinX();
				} else if (x + width > screenBounds.getMaxX()) {
					x = screenBounds.getMaxX() - width;
				}

				if (y < screenBounds.getMinY()) {
					y = screenBounds.getMinY();
				} else if (y + height > screenBounds.getMaxY()) {
					y = screenBounds.getMaxY() - height;
				}
				primaryStage.setX(x);
				primaryStage.setY(y);
			});

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
