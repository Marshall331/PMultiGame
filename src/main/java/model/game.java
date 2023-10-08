package model;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Circle;

import java.util.Random;

public final class game extends AnimationTimer {

    private gameConfiguration conf;

    public int scr1;
    public int scr2;
    public IntegerProperty scorePlayer1;
    public IntegerProperty scorePlayer2;

    // Elements de la scène
    private final player player1;
    private final player player2;
    private final Circle ball;

    // Dimensions des éléments de la scène
    public int WIDTH;
    public int HEIGHT;
    public int BALL_RADIUS;
    public int PADDLE_WIDTH;
    private int PADDLE_HEIGHT;
    public int paddleMinY;
    public int paddleMaxY;
    private int ballMinX;
    private int ballMaxX;
    private int ballMinY;
    private int ballMaxY;

    Random rand = new Random();

    private double acc;
    private double mag;
    private double dX;
    private double dY;

    public game(player _player1, player _player2, Circle _ball, gameConfiguration _conf) {
        this.conf = _conf;
        this.player1 = _player1;
        this.player2 = _player2;
        this.ball = _ball;
        this.scr1 = this.conf.scr1;
        this.scr2 = this.conf.scr2;
        this.scorePlayer1 = new SimpleIntegerProperty(scr2);
        this.scorePlayer2 = new SimpleIntegerProperty(scr1);
        setSizeValues();

        reset();
    }

    private void setSizeValues() {
        this.WIDTH = this.conf.WIDTH;
        this.HEIGHT = this.conf.HEIGHT;
        this.BALL_RADIUS = this.conf.BALL_RADIUS;
        this.PADDLE_WIDTH = this.conf.PADDLE_WIDTH;
        this.PADDLE_HEIGHT = this.conf.PADDLE_HEIGHT;
        this.paddleMaxY = this.HEIGHT - this.PADDLE_HEIGHT / 2;
        this.paddleMinY = -HEIGHT + PADDLE_HEIGHT / 2;
        this.ballMinX = -WIDTH + BALL_RADIUS - 2;
        this.ballMaxX = WIDTH - BALL_RADIUS + 2;
        this.ballMinY = -HEIGHT + BALL_RADIUS;
        this.ballMaxY = HEIGHT - BALL_RADIUS;
    }

    @Override
    public void handle(long now) {
        System.out.println("X :" + ball.getTranslateX() + "   Y :" + ball.getTranslateY());
        checkEndGame();
        checkBallCollision();
        checkPaddleCollision();
        movePlayer();
        // System.out.println("PADDLE Y : " + this.player2.paddle.getTranslateY());
        moveBall();
        // System.out.println(this.conf.WIDTH);
        // System.out.println(this.conf.HEIGHT);
        // System.out.println(this.conf.BALL_RADIUS);
        // System.out.println(this.conf.PADDLE_WIDTH);
        // System.out.println(this.conf.PADDLE_HEIGHT);
    }

    private void checkEndGame() {
        // Ajoutez votre logique pour vérifier la fin du jeu ici si nécessaire
    }

    private void movePlayer() {
        if (checkPlayerBorderCollision(player1) && !player1.isComputer) {
            if (player1.mouseControl) {
                player1.mouseMove();
            } else {
                player1.move();
            }
        }
        if (checkPlayerBorderCollision(player2) && !player2.isComputer) {
            player2.move();
        }
        if (player1.isComputer) {
            moveComputer(player1);
        }
        if (player2.isComputer) {
            moveComputer(player2);
        }
    }

    private void moveComputer(player _player) {
        double targetY = ball.getTranslateY();
        double speed = (_player.id == 1) ? this.conf.player1MaxSpeed : this.conf.player2MaxSpeed;
        double currentY = _player.paddle.getTranslateY();

        double direction = (targetY > currentY) ? 1 : -1;
        double distance = Math.abs(targetY - currentY);

        if (distance > speed) {
            distance = speed;
        }

        double mouvementBot = currentY + direction * distance;

        if (mouvementBot < this.paddleMinY) {
            mouvementBot = this.paddleMinY;
        } else if (mouvementBot > this.paddleMaxY) {
            mouvementBot = this.paddleMaxY;
        }

        if (checkPlayerBorderCollision(_player)) {
            _player.paddle.setTranslateY(mouvementBot);
        }
    }

    public boolean checkPlayerBorderCollision(player _player) {
        return _player.paddle.getTranslateY() + _player.vel <= this.paddleMaxY
                && _player.paddle.getTranslateY() + _player.vel >= this.paddleMinY;
    }

    private void reset() {
        ball.setTranslateX(0);
        ball.setTranslateY(0);
        player1.paddle.setTranslateY(0);
        player2.paddle.setTranslateY(0);

        setRandomDirection();
    }

    private void setRandomDirection() {
        mag = Constants.MAG;
        this.dX = 0;
        this.dY = 0;

        double randomAngle = rand.nextDouble() * Math.PI * 2; // Entre 0 et 2*pi
        // radians
        dX = mag * Math.sin(randomAngle);
        dY = mag * Math.sin(randomAngle);
    }

    private void checkPaddleCollision() {
        if (player1.paddle.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            mag *= (mag < Constants.SPEED) ? Constants.ACC : 1;
            acc = Constants.C * Math.abs((player1.paddle.getTranslateY() + 75 - ball.getTranslateY()) / 75);
            dX = mag * Math.cos(acc);
            dY = mag * Math.sin(acc);
            dY = (ball.getTranslateY() <= player1.paddle.getTranslateY() + 75) ? -dY : dY;
        } else if (player2.paddle.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            acc = Constants.C * Math.abs((player2.paddle.getTranslateY() + 75 - ball.getTranslateY()) / 75);
            dX = -mag * Math.cos(acc);
        }
    }

    private void checkBallCollision() {
        if (ball.getTranslateY() >= ballMaxY) {
            dY = -dY;
        } else if (ball.getTranslateY() <= ballMinY) {
            dY = -dY;
        }
        if (this.ball.getTranslateY() < ballMinY) {
            this.ball.setTranslateY(ballMinY);
        }
        if (this.ball.getTranslateY() > ballMaxY) {
            this.ball.setTranslateY(ballMaxY);
        }
        if (ball.getTranslateX() <= this.ballMinX) {
            scorePlayer1.set(scorePlayer1.getValue() + 1);
            reset();
        } else if (ball.getTranslateX() >= this.ballMaxX) {
            scorePlayer2.set(scorePlayer2.getValue() + 1);
            reset();
        }
    }

    private void moveBall() {
        ball.setTranslateX(ball.getTranslateX() + dX);
        ball.setTranslateY(ball.getTranslateY() + dY);
    }
}
