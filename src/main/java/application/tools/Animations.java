package application.tools;

import javax.swing.text.View;

import application.view.MultiplayerMenuController;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animations {

    // public static void setAnimatedIcon(Button _butt, String _imageName) {
    // ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100),
    // _butt);
    // scaleTransition.setToX(1.3);
    // scaleTransition.setToY(1.3);
    // _butt.setStyle("-fx-background-color: black;");
    // _butt.setOnMouseEntered(event -> {
    // scaleTransition.playFromStart();
    // _butt.setStyle("-fx-background-color: black;");
    // });

    // Image image = new Image(
    // MultiplayerMenuController.class.getResource("images/" +
    // _imageName).toExternalForm());
    // ImageView imageView = new ImageView(image);
    // imageView.setFitWidth(65);
    // imageView.setFitHeight(65);
    // _butt.setGraphic(imageView);

    // _butt.setOnMouseExited(event -> {
    // scaleTransition.stop();
    // _butt.setScaleX(1);
    // _butt.setScaleY(1);
    // });
    // }

    public static void setAnimatedIcon(ImageView _image) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), _image);
        scaleTransition.setToX(1.3);
        scaleTransition.setToY(1.3);
        // _butt.setStyle("-fx-background-color: black;");
        _image.setOnMouseEntered(event -> {
            scaleTransition.playFromStart();
            System.out.println("DEDANS");
            // _butt.setStyle("-fx-background-color: black;");
        });

        // Image image = new Image(
        // MultiplayerMenuController.class.getResource("images/" +
        // _imageName).toExternalForm());
        // ImageView imageView = new ImageView(image);
        // imageView.setFitWidth(65);
        // imageView.setFitHeight(65);
        // _butt.setGraphic(imageView);

        _image.setOnMouseExited(event -> {
            System.out.println("DEHORS");
            scaleTransition.stop();
            _image.setScaleX(1);
            _image.setScaleY(1);
        });
    }
}
