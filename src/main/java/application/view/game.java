package application.view;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import application.tools.Constants;
import application.tools.player;

import static java.lang.Math.*;

public final class game extends AnimationTimer {

    final IntegerProperty score = new SimpleIntegerProperty(0);

    private final player joueur1;
    private final player joueur2;
    private final Circle ball;
    private static final int WIDTH = 1680 / 2;
    private static final int HEIGHT = 972 / 2;
    private static final int BALL_RADIUS = 30;
    private static final int PADDLE_WIDTH = 30;
    private static final int PADDLE_HEIGHT = 160;

    double a = atan2(Constants.VY, Constants.VX);
    double mag = Constants.MAG;
    double dX = mag * cos(a);
    double dY = mag * sin(a);

    public game(player joueur1, player joueur2, Circle balle) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.ball = balle;
    }

    @Override
    public void handle(long now) {
        handlePlayer();
        updateGame();
        checkEndGame();

    }

    void checkEndGame() {

    }

    void handlePlayer() {
        checkCollision(joueur1);
        checkCollision(joueur2);
    }

    void checkCollision(player _player) {
        if (_player.rect.getTranslateY() + _player.vel <= HEIGHT - PADDLE_HEIGHT / 2
                && _player.rect.getTranslateY() + _player.vel >= -HEIGHT + PADDLE_HEIGHT / 2) {
            _player.rect.setTranslateY(_player.rect.getTranslateY() + _player.vel);
        }
    }

    void reset() {
        ball.setTranslateX(0);
        ball.setTranslateY(0);
        joueur1.rect.setTranslateY(0);
        joueur2.rect.setTranslateY(0);
        a = atan2(Constants.VY, Constants.VX);
        mag = Constants.MAG;
        dX = mag * cos(a);
        dY = mag * sin(a);
    }

    void updateGame() {
        ball.setTranslateX(ball.getTranslateX() + dX);
        ball.setTranslateY(ball.getTranslateY() + dY);
        joueur2.rect.setY(ball.getTranslateY() - 75);

        if (joueur1.rect.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            mag *= (mag < Constants.SPEED) ? Constants.ACC : 1;
            a = Constants.C * abs((joueur1.rect.getTranslateY() + 75 - ball.getTranslateY()) / 75);
            dX = mag * cos(a);
            dY = mag * sin(a);
            dY = (ball.getTranslateY() <= joueur1.rect.getTranslateY() + 75) ? -dY : dY;
            score.set(score.get() + 1);
        } else if (joueur2.rect.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            dX = -dX;
        } else if (ball.getTranslateY() >= HEIGHT - BALL_RADIUS) {
            dY = -dY;
        } else if (ball.getTranslateY() <= -HEIGHT + BALL_RADIUS) {
            dY = -dY;
        }
        if (ball.getTranslateX() < -WIDTH + BALL_RADIUS) {
            score.set(score.getValue() + 1);
            reset();
        } else if (ball.getTranslateX() > WIDTH - BALL_RADIUS / 2) {
            score.set(score.getValue() + 1);
            reset();
        }
    }
}