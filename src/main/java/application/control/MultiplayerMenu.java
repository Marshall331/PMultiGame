package application.control;

import application.PMultiApp;
import application.tools.ConfigurationSave;
import application.tools.StageManagement;
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

		try {

			this.primaryStage = _parentStage;

			FXMLLoader loader = new FXMLLoader(
					MultiplayerMenuController.class.getResource("MultiplayerMenu.fxml"));
			BorderPane root = loader.load();

			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
			scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

			StageManagement.removeKeysEvents(scene);

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
