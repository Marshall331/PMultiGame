package application.control;

import application.PMultiApp;
import application.tools.StageManagement;
import application.view.PlayerSettingsController;
import application.view.SettingsMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe de controleur de Dialogue de la scène des choix de la difficulté.
 */
public class PlayerSettings {

    private Stage primaryStage;
    private Stage parentStage;
    private int player;

    public PlayerSettings(Stage _parentStage, int _player) {
        this.primaryStage = new Stage();
        this.parentStage = _parentStage;
        this.player = _player;
    }

    public void startMenu() {

        try {
            // Chargement du source fxml
            FXMLLoader loader = new FXMLLoader(
                    SettingsMenuController.class.getResource("PlayerSettings.fxml"));
            BorderPane root = loader.load();

            // Paramétrage du Stage : feuille de style, titre
            Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
            scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

            this.primaryStage = new Stage();

            StageManagement.manageCenteringStage(this.parentStage, primaryStage);

            // Removing all keyEvents
            StageManagement.removeKeysEvents(scene);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Player settings");
            primaryStage.setResizable(false);

            PlayerSettingsController dbmfcViewController = loader.getController();
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
