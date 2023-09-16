package model;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Circle;
import application.tools.Constants;
import java.util.Random;

public final class game extends AnimationTimer {

    public final IntegerProperty score = new SimpleIntegerProperty(0);

    // Elements de la scène
    private final player joueur1;
    private final player joueur2;
    private final Circle ball;

    // Dimensions des éléments de la scène
    private static final int WIDTH = 1680 / 2;
    private static final int HEIGHT = 972 / 2;
    private static final int BALL_RADIUS = 30;
    private static final int PADDLE_WIDTH = 30;
    private static final int PADDLE_HEIGHT = 160;

    double a;
    double mag;
    double dX;
    double dY;

    public game(player joueur1, player joueur2, Circle balle) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.ball = balle;
        reset(); // Initialise la balle au début
    }

    @Override
    public void handle(long now) {
        handlePlayer();
        updateGame();
        checkEndGame();
    }

    void checkEndGame() {
        // Ajoutez votre logique pour vérifier la fin du jeu ici si nécessaire
    }

    private void handlePlayer() {
        if (checkCollision(joueur1) && !joueur1.isComputer) {
            joueur1.move(joueur1.vel);
        } 
        if (checkCollision(joueur2) && joueur2.isComputer) {
                joueur2.rect.setTranslateY(ball.getLayoutY() - 90);
        }
    }

    private boolean checkCollision(player _player) {
        return _player.rect.getTranslateY() + _player.vel <= HEIGHT - PADDLE_HEIGHT / 2
                && _player.rect.getTranslateY() + _player.vel >= -HEIGHT + PADDLE_HEIGHT / 2;
    }

    private void reset() {
        ball.setTranslateX(0);
        ball.setTranslateY(0);
        joueur1.rect.setTranslateY(0);
        joueur2.rect.setTranslateY(0);

        // Génère un angle aléatoire pour la direction de la balle
        Random rand = new Random();
        double randomAngle = rand.nextDouble() * Math.PI * 2; // Entre 0 et 2*pi radians
        mag = Constants.MAG;
        dX = mag * Math.cos(randomAngle);
        dY = mag * Math.sin(randomAngle);
    }

    private void updateGame() {
        ball.setTranslateX(ball.getTranslateX() + dX);
        ball.setTranslateY(ball.getTranslateY() + dY);

        if (joueur1.rect.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            mag *= (mag < Constants.SPEED) ? Constants.ACC : 1;
            a = Constants.C * Math.abs((joueur1.rect.getTranslateY() + 75 - ball.getTranslateY()) / 75);
            dX = mag * Math.cos(a);
            dY = mag * Math.sin(a);
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
