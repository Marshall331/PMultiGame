package application.control;

import application.PMultiApp;
import application.view.menuDifficulteController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène des choix de la difficulté.
 */
public class menuDifficulte {


	// Stage de la fenêtre principale construite par DailyBankMainFrame
	private Stage primaryStage;

	public menuDifficulte(Stage _parentStage) {

		try {

			this.primaryStage = _parentStage;
			
			// Chargement du source fxml
			FXMLLoader loader = new FXMLLoader(
					menuDifficulteController.class.getResource("gamedifficulte.fxml"));
			BorderPane root = loader.load();

			// Paramétrage du Stage : feuille de style, titre
			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
	        scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());
	        
			primaryStage.setScene(scene);
			primaryStage.setTitle("Configuration de la partie");
			primaryStage.setResizable(false);
			
			menuDifficulteController dbmfcViewController = loader.getController();
			dbmfcViewController.initContext(primaryStage);

			dbmfcViewController.displayDialog();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void lancerJeu(int difficulte) {
		this.primaryStage.close();
		game m = new game(primaryStage);
	}
}
