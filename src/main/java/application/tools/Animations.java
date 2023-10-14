package application.tools;

import application.view.MultiplayerMenuController;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animations {

    public static void setAnimatedIcon(Button _butt) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), _butt);
        scaleTransition.setToX(1.15);
        scaleTransition.setToY(1.15);
        _butt.setStyle("-fx-background-color: white;");

        _butt.setOnMouseEntered(event -> {
            scaleTransition.playFromStart();
            _butt.setStyle("-fx-background-color: white;");
        });

        Image image = new Image(
                MultiplayerMenuController.class.getResource("images/SettingsIcon.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);
        _butt.setGraphic(imageView);

        _butt.setOnMouseExited(event -> {
            scaleTransition.stop();
            _butt.setScaleX(1);
            _butt.setScaleY(1);
        });
    }
}
