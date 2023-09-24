package application.tools;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Utilities {

    public static void setCenterStage(Stage _Stage, Scene _scene) {
        
        // Obtenez les dimensions de la scène au lieu de primaryStage
        double sceneWidth = _scene.getWidth();
        double sceneHeight = _scene.getHeight();

        // Récupérez la taille de l'écran
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Calculez les coordonnées x et y pour centrer la fenêtre
        double windowX = (screenWidth - sceneWidth) / 2;
        double windowY = (screenHeight - sceneHeight) / 2;

        // Définissez les coordonnées de la fenêtre pour la centrer
        _Stage.setX(windowX);
        _Stage.setY(windowY);
    }

    public static void removeKeysEvents(Scene _scene) {

        // Créez un gestionnaire d'événements pour les événements clavier
        EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // Empêchez l'événement de se propager à d'autres composants
                event.consume();
            }
        };

        // Ajoutez le gestionnaire d'événements à la scène
        _scene.addEventFilter(KeyEvent.ANY, keyEventHandler);
    }
}
