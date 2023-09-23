package model;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.util.Random;

public final class game extends AnimationTimer {

    public IntegerProperty scr1 = new SimpleIntegerProperty(0);
    public IntegerProperty scr2 = new SimpleIntegerProperty(0);

    // Elements de la scène
    private final player player1;
    private final player player2;
    private final Circle ball;

    // Dimensions des éléments de la scène
    private static final int WIDTH = 1680 / 2;
    private static final int HEIGHT = 972 / 2;
    private static final int BALL_RADIUS = 30;
    private static final int PADDLE_WIDTH = 30;
    private static final int PADDLE_HEIGHT = 160;

    Random rand = new Random();

    double acc;
    double mag;
    double dX;
    double dY;

    public game(player _player1, player _player2, Circle _ball) {
        this.player1 = _player1;
        this.player2 = _player2;
        this.ball = _ball;

        reset();
    }

    @Override
    public void handle(long now) {
        updateGame();
    }

    private void updateGame() {
        checkEndGame();
        checkBallCollision();
        checkPaddleCollision();
        movePlayer();
        moveBall();
    }

    private void checkEndGame() {
        // Ajoutez votre logique pour vérifier la fin du jeu ici si nécessaire
    }

    private void movePlayer() {
        if (checkPlayerBorderCollision(player1) && !player1.isComputer) {
            player1.mouseMove();
            player1.move(player1.vel);
        }
        if (checkPlayerBorderCollision(player2) && !player2.isComputer) {
            player2.move(player2.vel);
        }
        if (player2.isComputer) {
            double targetY = ball.getTranslateY(); // Position verticale cible du joueur 2
            double speed = 20; // Vitesse maximale du joueur 2
            double currentY = player2.rect.getTranslateY();

            // Calculer la direction et la distance vers la cible
            double direction = (targetY > currentY) ? 1 : -1;
            double distance = Math.abs(targetY - currentY);

            // Limiter la vitesse du joueur 2 à 10
            if (distance > speed) {
                distance = speed;
            }

            double mouvementBot = currentY + direction * distance;

            if (mouvementBot < -405) {
                mouvementBot = -405;
            } else if (mouvementBot > 415) {
                mouvementBot = 415;
            }

            if (player2.rect.getTranslateY() <= HEIGHT - PADDLE_HEIGHT / 2 + 9
                    && player2.rect.getTranslateY() >= -HEIGHT + PADDLE_HEIGHT / 2) {
                player2.rect.setTranslateY(mouvementBot);
            }
        }
    }

    public boolean checkPlayerBorderCollision(player _player) {
        return _player.rect.getTranslateY() + _player.vel <= HEIGHT - PADDLE_HEIGHT / 2 + 9
                && _player.rect.getTranslateY() + _player.vel >= -HEIGHT + PADDLE_HEIGHT / 2;
    }

    private void reset() {
        ball.setTranslateX(0);
        ball.setTranslateY(0);
        player1.rect.setTranslateY(0);
        player2.rect.setTranslateY(0);

        setRandomDirection();
    }

    private void setRandomDirection() {
        mag = Constants.MAG;
        this.dX = 0;
        this.dY = 0;

        double randomAngle = rand.nextDouble() * Math.PI * 2; // Entre 0 et 2*pi radians
        dX = mag * Math.cos(randomAngle);
        dY = mag * Math.sin(randomAngle);
    }

    private void checkPaddleCollision() {
        if (player1.rect.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            mag *= (mag < Constants.SPEED) ? Constants.ACC : 1;
            acc = Constants.C * Math.abs((player1.rect.getTranslateY() + 75 - ball.getTranslateY()) / 75);
            dX = mag * Math.cos(acc);
            dY = mag * Math.sin(acc);
            dY = (ball.getTranslateY() <= player1.rect.getTranslateY() + 75) ? -dY : dY;
        } else if (player2.rect.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            acc = Constants.C * Math.abs((player2.rect.getTranslateY() + 75 - ball.getTranslateY()) / 75);
            dX = -mag * Math.cos(acc);
        }
    }

    private void checkBallCollision() {
        if (ball.getTranslateY() >= HEIGHT - BALL_RADIUS + 5) {
            dY = -dY;
        } else if (ball.getTranslateY() <= -HEIGHT + BALL_RADIUS) {
            dY = -dY;
        }
        if (ball.getTranslateX() <= -WIDTH + BALL_RADIUS - 5) {
            scr1.set(scr1.getValue() + 1);
            reset();
        } else if (ball.getTranslateX() >= WIDTH - 5) {
            scr2.set(scr2.getValue() + 1);
            reset();
        }
    }

    private void moveBall() {
        ball.setTranslateX(ball.getTranslateX() + dX);
        ball.setTranslateY(ball.getTranslateY() + dY);
    }
}
