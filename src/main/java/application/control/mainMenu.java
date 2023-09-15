package application.control;

import application.PMultiApp;
import application.tools.StageManagement;
import application.view.mainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène du menu principale.
 */
public class mainMenu extends Application {


	// Stage de la fenêtre principale construite par DailyBankMainFrame
	private Stage primaryStage;

	/**
	 * Méthode de démarrage (JavaFX).
	 */ 
	@Override
	public void start(Stage _parentStage) {

		try {
			
			this.primaryStage = _parentStage;

			// Chargement du source fxml
			FXMLLoader loader = new FXMLLoader(
					mainMenuController.class.getResource("gamemenu.fxml"));
			BorderPane root = loader.load();

			// Paramétrage du Stage : feuille de style, titre
			Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
	        scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("Menu du jeu");
			primaryStage.setResizable(false);
			
			mainMenuController dbmfcViewController = loader.getController();
			dbmfcViewController.initContext(primaryStage,this);
			scene.getStylesheets().add("application.css");

			dbmfcViewController.displayDialog();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Méthode principale de lancement de l'application.
	 */
	public static void runApp() {
		Application.launch();
	}

	/**
	 * Méthode principale de lancement du menu des choix de la difficulté.
	 */
	public void menuSolo() {
		menuDifficulte mD = new menuDifficulte(primaryStage);
	}

	/**
	 * Méthode principale de lancement du menu multijoueur.
	 */
	public void menuMulti() {
		menuMulti mM = new menuMulti(primaryStage);
	}

	/**
	 * Méthode principale de lancement de la scène des paramètres.
	 */
	public void menuSettings() {
		menuSettings mS = new menuSettings(primaryStage);
	}
}
