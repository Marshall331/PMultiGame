package application.control;

import application.PMultiApp;
import application.tools.Utilities;
import application.view.MultiplayerMenuController;
import application.view.SettingsMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène des choix de la difficulté.
 */
public class SettingsMenu {


	// Stage de la fenêtre principale construite par DailyBankMainFrame
	private Stage primaryStage;

	public SettingsMenu(Stage _parentStage) {

		try {
			
			this.primaryStage = _parentStage;
			
			// Chargement du source fxml
			FXMLLoader loader = new FXMLLoader(
					SettingsMenuController.class.getResource("SettingsMenu.fxml"));
			BorderPane root = loader.load();

			// Paramétrage du Stage : feuille de style, titre
			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
	        scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

			// Removing all keyEvents
			Utilities.removeKeysEvents(scene);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Game settings");
			primaryStage.setResizable(false);
			
			SettingsMenuController dbmfcViewController = loader.getController();
			dbmfcViewController.initContext(primaryStage);

			dbmfcViewController.displayDialog();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
