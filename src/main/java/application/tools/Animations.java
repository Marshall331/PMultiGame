package application.tools;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import model.game;

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

        _butt.setOnMouseExited(event -> {
            scaleTransition.stop();
            _butt.setScaleX(1);
            _butt.setScaleY(1);
        });
    }

    public static void playGoalAnimation(Scene _scene, game _game, Label _labGoal, int _player, int _fontSizeGoal, int _labScoreMargin) {

        _game.ball.setVisible(false);
        _game.stop();

        // StageManagement.disableItems(_scene, true);

        _labGoal.setTranslateX(0);
        _labGoal.setTranslateY(0);
        _labGoal.setStyle("-fx-font-size: " + _fontSizeGoal + "px; -fx-font-weight: bold;");
        _labGoal.setText("GOOOOAL du joueur " + _player);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), _labGoal);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.7), _labGoal);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0.8);

        SequentialTransition sequence = new SequentialTransition(fadeIn, fadeOut);
        sequence.setOnFinished(event -> {
            _labGoal.setVisible(false);
            playAddScore(_scene, _game, _labGoal, _player, _labScoreMargin);
        });

        _labGoal.setVisible(true);
        sequence.play();
    }

    public static void playAddScore(Scene _scene, game _game, Label _labGoal, int _player, int _labScoreMargin) {
        // _labGoal.setStyle("-fx-font-size: " + _fontSizeScore + "px; -fx-font-weight:
        // bold;"
        // + "-fx-border-color: red; -fx-border-width: 4px;");
        _labGoal.setStyle("-fx-font-size: " + _labScoreMargin + "px; -fx-font-weight: bold;");
        _labGoal.setTranslateX(
                _player == 1 ? -_labScoreMargin : _labScoreMargin);
        _labGoal.setText("+ 1");

        FadeTransition fadeIn = new FadeTransition(Duration.millis(100), _labGoal);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(700), _labGoal);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        TranslateTransition translateUpGoal = new TranslateTransition(Duration.millis(250), _labGoal);
        translateUpGoal.setByY(-ConfigurationSave.chargerConfiguration().HEIGHT + _labScoreMargin);

        SequentialTransition sequence = new SequentialTransition(fadeIn, translateUpGoal, fadeOut);
        sequence.setOnFinished(event -> {
            if (_player == 2) {
                _game.scorePlayer1.set(_game.scorePlayer1.getValue() + 1);
            } else {
                _game.scorePlayer2.set(_game.scorePlayer2.getValue() + 1);
            }
            _labGoal.setVisible(false);
            _game.ball.setVisible(true);
            _game.reset();
            _game.start();
            StageManagement.disableItems(_scene, false);
        });

        _labGoal.setVisible(true);
        sequence.play();
    }
}
