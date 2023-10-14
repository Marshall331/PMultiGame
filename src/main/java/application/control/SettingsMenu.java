package application.control;

import application.PMultiApp;
import application.tools.StageManagement;
import application.tools.ConfigurationSave;
import application.view.MultiplayerMenuController;
import application.view.SettingsMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.gameConfiguration;

/**
 * Classe de controleur de Dialogue de la scène des choix de la difficulté.
 */
public class SettingsMenu {

	private Stage primaryStage;

	public SettingsMenu(Stage _parentStage, boolean inGame) {

		try {

			if (!inGame) {
				this.primaryStage = _parentStage;
			} else {
				this.primaryStage = new Stage();
				// StageManagement.manageCenteringStage(_parentStage, primaryStage);
			}

			// Chargement du source fxml
			FXMLLoader loader = new FXMLLoader(
					SettingsMenuController.class.getResource("SettingsMenu.fxml"));
			BorderPane root = loader.load();

			// Paramétrage du Stage : feuille de style, titre
			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
			scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

			// Removing all keyEvents
			StageManagement.removeKeysEvents(scene);

			primaryStage.setScene(scene);
			if (inGame) {
				StageManagement.setCenterStageOnScreen(primaryStage, scene);
			}
			primaryStage.setTitle("Game settings");
			primaryStage.setResizable(false);

			SettingsMenuController dbmfcViewController = loader.getController();
			dbmfcViewController.initContext(_parentStage, primaryStage, inGame);

			dbmfcViewController.displayDialog();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
