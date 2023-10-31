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

    public static void playGoalAnimation(Scene _scene, game _game, Label _labGoal, int _player, int _fontSizeGoal,
            int _labScoreMargin) {

        StageManagement.disableItems(_scene, true);
        if (_player == 1) {
            Style.setLabelColor(_labGoal, _game.gameController.conf.player1Color);
        } else {
            Style.setLabelColor(_labGoal, _game.gameController.conf.player2Color);
        }
        _labGoal.setVisible(true);
        _game.stop();

        _labGoal.setTranslateX(0);
        _labGoal.setTranslateY(0);
        _labGoal.setStyle("-fx-font-size: " + _fontSizeGoal + "px; -fx-font-weight: bold;");
        _labGoal.setText("Joueur " + _player + " a marqué !");

        FadeTransition fadeOutBall = new FadeTransition(Duration.seconds(0.1), _game.ball);
        fadeOutBall.setFromValue(1);
        fadeOutBall.setToValue(0);

        ScaleTransition fadeOutLine = new ScaleTransition(Duration.seconds(0.2), _game.gameController.midLine);
        fadeOutLine.setToY(0);

        FadeTransition fadeInGoal = new FadeTransition(Duration.seconds(0.5), _labGoal);
        fadeInGoal.setFromValue(0);
        fadeInGoal.setToValue(1);

        FadeTransition fadeOutGoal = new FadeTransition(Duration.seconds(0.7), _labGoal);
        fadeOutGoal.setFromValue(1);
        fadeOutGoal.setToValue(0.2);

        fadeOutGoal.setOnFinished(event -> {
            _labGoal.setStyle("-fx-font-size: " + _labScoreMargin + "px; -fx-font-weight: bold;");
            _labGoal.setTranslateX(_player == 1 ? -_labScoreMargin : _labScoreMargin);
            _labGoal.setText("+100");
        });

        FadeTransition fadeInScore = new FadeTransition(Duration.millis(100), _labGoal);
        fadeInScore.setFromValue(0);
        fadeInScore.setToValue(1);

        TranslateTransition translateUpGoal = new TranslateTransition(Duration.millis(500), _labGoal);
        translateUpGoal.setByY(-ConfigurationSave.chargerConfiguration().HEIGHT + _labScoreMargin);

        FadeTransition fadeOutScore = new FadeTransition(Duration.millis(300), _labGoal);
        fadeOutScore.setFromValue(1);
        fadeOutScore.setToValue(0);

        ScaleTransition fadeInLine = new ScaleTransition(Duration.seconds(0.5), _game.gameController.midLine);
        fadeInLine.setToY(1);

        fadeInLine.setOnFinished(event -> {
            _game.ball.setTranslateY(0);
            _game.ball.setTranslateX(0);
        });

        FadeTransition fadeInBall = new FadeTransition(Duration.seconds(0.5), _game.ball);
        fadeInBall.setFromValue(0);
        fadeInBall.setToValue(1);

        TranslateTransition translateMidPlayer1 = new TranslateTransition(Duration.millis(250),
                _game.gameController.paddle1);
        translateMidPlayer1.setToY(0);

        TranslateTransition translateMidPlayer2 = new TranslateTransition(Duration.millis(250),
                _game.gameController.paddle2);
        translateMidPlayer2.setToY(0);

        fadeOutScore.setOnFinished(event -> {
            if (_player == 1) {
                _game.scorePlayer2.setValue(_game.scorePlayer2.getValue() + 100);
            } else {
                _game.scorePlayer1.setValue(_game.scorePlayer1.getValue() + 100);
            }
        });

        SequentialTransition sequence = new SequentialTransition(fadeOutBall,
                fadeOutLine, fadeInGoal, fadeOutGoal,
                fadeInScore, translateUpGoal, fadeOutScore, fadeInLine, fadeInBall, translateMidPlayer1,
                translateMidPlayer2);

        sequence.setOnFinished(event -> {
            _game.reset();
            _game.start();
            StageManagement.disableItems(_scene, false);
        });

        sequence.play();
    }
}