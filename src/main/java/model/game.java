package model;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Circle;
import java.util.Random;

import application.tools.Animations;
import application.view.GameController;

public final class game extends AnimationTimer {

    private gameConfiguration conf;
    private GameController gameController;

    private double C;
    private double MAG;
    private double SPEED;
    private double ACC;

    private int scr1;
    private int scr2;
    public IntegerProperty scorePlayer1;
    public IntegerProperty scorePlayer2;

    private final player player1;
    private final player player2;
    public final Circle ball;

    public final int WIDTH;
    public final int HEIGHT;

    public final double BALL_RADIUS;
    public final int PADDLE_WIDTH;

    public final double ballBaseSpeedX;
    public final double ballBaseSpeedY;
    public final double ballMinX;
    public final double ballMaxX;
    public final double ballMinY;
    public final double ballMaxY;

    Random rand = new Random();

    private double acc;
    private double mag;
    private double dX;
    private double dY;

    public game(GameController _gameController, player _player1, player _player2, Circle _ball,
            gameConfiguration _conf) {

        this.conf = _conf;
        this.gameController = _gameController;

        this.C = this.conf.C;
        this.MAG = this.conf.MAG;
        this.ACC = this.conf.ballAcc;
        this.SPEED = this.conf.ballMaxSpeed;

        this.player1 = _player1;
        this.player2 = _player2;

        this.ball = _ball;

        this.scr1 = this.conf.scr1;
        this.scr2 = this.conf.scr2;

        this.scorePlayer1 = new SimpleIntegerProperty(scr2);
        this.scorePlayer2 = new SimpleIntegerProperty(scr1);

        this.WIDTH = this.conf.WIDTH;
        this.HEIGHT = this.conf.HEIGHT;

        this.BALL_RADIUS = this.conf.ballSize;
        this.PADDLE_WIDTH = this.conf.PADDLE_WIDTH;

        this.player1.paddleSize = this.conf.player1PaddleSize;
        this.player2.paddleSize = this.conf.player2PaddleSize;

        this.player1.paddleMaxY = this.HEIGHT - this.player1.paddleSize / 2;
        this.player1.paddleMinY = -this.HEIGHT + this.player1.paddleSize / 2;
        this.player2.paddleMaxY = this.HEIGHT - this.player2.paddleSize / 2;
        this.player2.paddleMinY = -this.HEIGHT + this.player2.paddleSize / 2;

        this.ballBaseSpeedX = this.conf.ballBaseSpeedX;
        this.ballBaseSpeedY = this.conf.ballBaseSpeedY;
        this.ballMinX = -WIDTH + BALL_RADIUS;
        this.ballMaxX = WIDTH - BALL_RADIUS;
        this.ballMinY = -HEIGHT + BALL_RADIUS;
        this.ballMaxY = HEIGHT - BALL_RADIUS;

        reset();
    }

    @Override
    public void handle(long now) {
        // scorePlayer1.set(100);
        // scorePlayer1.set(100);
        // Animations.playGoalAnimation(this.gameController.scene, this, this.gameController.labGoal, 2,
        //         this.conf.labGoalFontSize, this.conf.labScoreMargin);

        checkEndGame();
        checkGoal();
        checkPaddleCollision();
        movePlayer();
        moveBall();
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
            if (player2.mouseControl) {
                player2.mouseMove();
            } else {
                player2.move();
            }
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
        double speed = _player.maxSpeed;
        double currentY = _player.paddle.getTranslateY();

        double direction = (targetY > currentY) ? 1 : -1;
        double distance = Math.abs(targetY - currentY);

        if (distance > speed) {
            distance = speed;
        }

        double mouvementBot = currentY + direction * distance;

        if (mouvementBot < _player.paddleMinY) {
            mouvementBot = _player.paddleMinY;
        } else if (mouvementBot > _player.paddleMaxY) {
            mouvementBot = _player.paddleMaxY;
        }

        if (checkPlayerBorderCollision(_player)) {
            _player.paddle.setTranslateY(mouvementBot);
        }
    }

    public boolean checkPlayerBorderCollision(player _player) {
        return _player.paddle.getTranslateY() + _player.vel <= _player.paddleMaxY
                && _player.paddle.getTranslateY() + _player.vel >= _player.paddleMinY;
    }

    public void reset() {
        this.stop();
        ball.setTranslateX(0);
        ball.setTranslateY(0);
        player1.paddle.setTranslateY(0);
        player2.paddle.setTranslateY(0);

        setRandomDirection();
    }

    private void setRandomDirection() {
        this.mag = this.MAG;
        this.dX = this.conf.ballBaseSpeedX;
        this.dY = this.conf.ballBaseSpeedY;
        if (this.rand.nextInt(2) == 1) {
            this.dY = -dY;
        }
        if (this.rand.nextInt(2) == 0) {
            this.dX = -dX;
        }
    }

    private void checkPaddleCollision() {
        if (player1.paddle.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            mag *= (mag < this.SPEED) ? this.ACC : 1;
            acc = this.C * Math.abs(
                    (player1.paddle.getTranslateY() + this.player1.paddleSize - ball.getTranslateY())
                            / this.player1.paddleSize);
            dX = mag * Math.cos(acc) < this.ballBaseSpeedX ? this.ballBaseSpeedX : mag * Math.cos(acc);
            dY = mag * Math.sin(acc) < this.ballBaseSpeedX ? this.ballBaseSpeedX : mag * Math.sin(acc);
            dY = (ball.getTranslateY() <= player1.paddle.getTranslateY()) ? -dY : dY;
        } else if (player2.paddle.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            mag *= (mag < this.SPEED) ? this.ACC : 1;
            acc = this.C * Math.abs(
                    (player2.paddle.getTranslateY() + this.player2.paddleSize - ball.getTranslateY())
                            / this.player2.paddleSize);
            dX = mag * Math.cos(acc) < this.ballBaseSpeedX ? this.ballBaseSpeedX : mag * Math.cos(acc);
            dY = mag * Math.sin(acc) < this.ballBaseSpeedX ? this.ballBaseSpeedX : mag * Math.sin(acc);
            dY = (ball.getTranslateY() <= player1.paddle.getTranslateY()) ? -dY : dY;
            dX = -mag * Math.cos(acc);
        }
    }

    private void checkGoal() {
        if (ball.getTranslateX() <= this.ballMinX) {
            Animations.playGoalAnimation(this.gameController.scene, this, this.gameController.labGoal, 2,
                    this.conf.labGoalFontSize, this.conf.labScoreMargin);
        } else if (ball.getTranslateX() >= this.ballMaxX) {
            Animations.playGoalAnimation(this.gameController.scene, this, this.gameController.labGoal, 1,
                    this.conf.labGoalFontSize, this.conf.labScoreMargin);
        }
    }

    private void moveBall() {
        double ballPosX = ball.getTranslateX() + dX;
        double ballPosY = ball.getTranslateY() + dY;

        if (ballPosY >= ballMinY && ballPosY <= ballMaxY) {
            ball.setTranslateY(ballPosY);
        } else {
            dY = -dY;
        }
        ball.setTranslateX(ballPosX);
    }
}