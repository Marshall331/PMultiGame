package application.control;

import application.PMultiApp;
import application.tools.ConfigurationSave;
import application.tools.StageManagement;
import application.view.DifficultyMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène des choix de la difficulté.
 */
public class DifficultyMenu {


	// Stage de la fenêtre principale construite par DailyBankMainFrame
	private Stage primaryStage;

	public DifficultyMenu(Stage _parentStage) {

		try {

			this.primaryStage = _parentStage;
			
			// Chargement du source fxml
			FXMLLoader loader = new FXMLLoader(
					DifficultyMenuController.class.getResource("DifficultyMenu.fxml"));
			BorderPane root = loader.load();

			// Paramétrage du Stage : feuille de style, titre
			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
	        scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

			// Removing all keyEvents
			StageManagement.removeKeysEvents(scene);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Mode Solo");
			primaryStage.setResizable(false);
			
			DifficultyMenuController dmc = loader.getController();
			dmc.initContext(primaryStage);
			dmc.displayDialog();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void lancerJeu(int difficulte) {
		this.primaryStage.close();
		Game g = new Game(primaryStage);
	}
}
