package application.control;

import application.PMultiApp;
import application.tools.StageManagement;
import application.view.SettingsMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène des choix de la difficulté.
 */
public class SettingsMenu {

	private Stage primaryStage;
	private Stage parentStage;
	SettingsMenuController dbmfcViewController;
	private boolean inGame;

	public SettingsMenu(Stage _parentStage, boolean _inGame) {
		if (!_inGame) {
			this.primaryStage = _parentStage;
		} else {
			this.primaryStage = new Stage();
			this.parentStage = _parentStage;
		}
		this.inGame = _inGame;
	}

	public void startMenu() {

		try {

			// if (!inGame) {
			// this.primaryStage = _parentStage;
			// } else {
			// this.primaryStage = new Stage();
			// // StageManagement.manageCenteringStage(_parentStage, primaryStage);
			// }

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
			primaryStage.setTitle("Paramètres");
			primaryStage.setResizable(false);

			dbmfcViewController = loader.getController();
			dbmfcViewController.initContext(this.parentStage, this.primaryStage, this.inGame);

			dbmfcViewController.displayDialog();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void closeMenu() {
		if (this.dbmfcViewController.playerSettings != null) {
			this.dbmfcViewController.playerSettings.closeMenu();
		}
		if (this.dbmfcViewController.gameSpeedSettings != null) {
			this.dbmfcViewController.gameSpeedSettings.closeMenu();
		}
		this.primaryStage.close();
	}
}
