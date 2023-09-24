package application.control;

import application.PMultiApp;
import application.tools.Utilities;
import application.view.MultiplayerMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène des choix de la difficulté.
 */
public class MultiplayerMenu {

	// Stage de la fenêtre principale construite par DailyBankMainFrame
	private Stage primaryStage;

	public MultiplayerMenu(Stage _parentStage) {

		this.primaryStage = primaryStage;

		try {

			this.primaryStage = _parentStage;

			// Chargement du source fxml
			FXMLLoader loader = new FXMLLoader(
					MultiplayerMenuController.class.getResource("MultiplayerMenu.fxml"));
			BorderPane root = loader.load();

			// Paramétrage du Stage : feuille de style, titre
			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
			scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

			// Removing all keyEvents
			Utilities.removeKeysEvents(scene);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Multiplayer Game");
			primaryStage.setResizable(false);

			MultiplayerMenuController mmc = loader.getController();
			mmc.initContext(primaryStage);
			mmc.displayDialog();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
