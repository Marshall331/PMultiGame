package application.control;

import application.PMultiApp;
import application.tools.Utilities;
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

    public PlayerSettings(Stage _parentStage, int _player) {

        try {

            // Chargement du source fxml
            FXMLLoader loader = new FXMLLoader(
                    SettingsMenuController.class.getResource("PlayerSettings.fxml"));
            BorderPane root = loader.load();

            // Paramétrage du Stage : feuille de style, titre
            Scene scene = new Scene(root, root.getPrefWidth() + 20, root.getPrefHeight() + 10);
            scene.getStylesheets().add(PMultiApp.class.getResource("application.css").toExternalForm());

            this.primaryStage = new Stage();

            // Removing all keyEvents
            Utilities.removeKeysEvents(scene);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Player settings");
            primaryStage.setResizable(true);

            PlayerSettingsController dbmfcViewController = loader.getController();
            dbmfcViewController.initContext(primaryStage, _player);

            dbmfcViewController.displayDialog();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
