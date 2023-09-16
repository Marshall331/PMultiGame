package application.control;

import application.PMultiApp;
import application.tools.Utilitaires;
import application.view.menuMultiController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène des choix de la difficulté.
 */
public class menuMulti {


	// Stage de la fenêtre principale construite par DailyBankMainFrame
	private Stage primaryStage;

	public menuMulti(Stage _parentStage) {

		this.primaryStage = primaryStage;

		try {

			this.primaryStage = _parentStage;
			
			// Chargement du source fxml
			FXMLLoader loader = new FXMLLoader(
					menuMultiController.class.getResource("gamemulti.fxml"));
			BorderPane root = loader.load();

			// Paramétrage du Stage : feuille de style, titre
			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
	        scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

			// Suppression des évènements du clavier
			Utilitaires.removeKeysEvents(scene);

			// Placement de la fenêtre au milieu de l'écran
			Utilitaires.setCenterStage(primaryStage, scene);
	        
			primaryStage.setScene(scene);
			primaryStage.setTitle("Menu multijoueur");
			primaryStage.setResizable(false);
			
			menuMultiController dbmfcViewController = loader.getController();
			dbmfcViewController.initContext(primaryStage);

			dbmfcViewController.displayDialog();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
