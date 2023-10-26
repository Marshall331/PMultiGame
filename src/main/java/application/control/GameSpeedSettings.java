package application.control;

import application.PMultiApp;
import application.tools.StageManagement;
import application.view.GameSpeedSettingsController;
import application.view.SettingsMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène des choix de la difficulté.
 */
public class GameSpeedSettings {

    private Stage primaryStage;
    private Stage parentStage;
    private int player;

    public GameSpeedSettings(Stage _parentStage) {
        this.primaryStage = new Stage();
        this.parentStage = _parentStage;
    }

    public void startMenu() {

        try {
            // Chargement du source fxml
            FXMLLoader loader = new FXMLLoader(
                    SettingsMenuController.class.getResource("GameSpeedSettings.fxml"));
            BorderPane root = loader.load();

            // Paramétrage du Stage : feuille de style, titre
            Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
            scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

            this.primaryStage = new Stage();

            StageManagement.manageCenteringStage(this.parentStage, primaryStage);

            // Removing all keyEvents
            StageManagement.removeKeysEvents(scene);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Ball settings");
            primaryStage.setResizable(false);

            GameSpeedSettingsController dbmfcViewController = loader.getController();
            dbmfcViewController.initContext(primaryStage, this.player);

            dbmfcViewController.displayDialog();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void closeMenu() {
        this.primaryStage.close();
    }
}
