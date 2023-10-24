package application.tools;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Animations {

    public static void setAnimatedIcon(Button _butt, double _scale, String _bGColor) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100),
                _butt);
        scaleTransition.setToX(_scale);
        scaleTransition.setToY(_scale);
        _butt.setStyle("-fx-background-color: " + _bGColor + ";");
        _butt.setOnMouseEntered(event -> {
            scaleTransition.playFromStart();
            _butt.setStyle("-fx-background-color: " + _bGColor + ";");
        });

        // Image image = new Image(
        // MultiplayerMenuController.class.getResource("images/" +
        // _imageName).toExternalForm());
        // ImageView imageView = new ImageView(image);
        // imageView.setFitWidth(65);
        // imageView.setFitHeight(65);
        // _butt.setGraphic(imageView);

        _butt.setOnMouseExited(event -> {
            scaleTransition.stop();
            _butt.setScaleX(1);
            _butt.setScaleY(1);
        });
    }
}
