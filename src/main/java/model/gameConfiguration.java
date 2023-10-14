package model;

import java.io.Serializable;

public class gameConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    public double player1MaxSpeed;
    public boolean player1MouseControl;
    public boolean player1isComputer;
    public int player1PaddleSize;

    public double player2MaxSpeed;
    public boolean player2MouseControl;
    public boolean player2isComputer;
    public int player2PaddleSize;

    public int gameMaxSpeed;
    public double gameAcc;

    public boolean gameSizeChanged;
    public boolean isSoloGame;
    public int gameSize;
    public boolean soundOn;

    public int scr1;
    public int scr2;

    public int WIDTH;
    public int HEIGHT;
    public int midLineY;
    public int PADDLE_WIDTH;
    public int BALL_RADIUS;
    public double PADDLE_SPEED;
    public double BALL_SPEED;

    public gameConfiguration() {

        this.gameMaxSpeed = 30;
        this.gameAcc = 1.1;

        this.player1MaxSpeed = 5;
        this.player1MouseControl = false;
        this.player1isComputer = false;
        this.player1PaddleSize = 140;

        this.player2MaxSpeed = 5;
        this.player2MouseControl = false;
        this.player2isComputer = false;
        this.player1PaddleSize = 140;

        this.isSoloGame = false;
        this.gameSize = 2;
        this.soundOn = true;
        // this.ballSpeed = 0;
        // this.playerSpeed = 0;
        this.scr1 = 0;
        this.scr2 = 0;
    }

    public void resetScore() {
        this.scr1 = 0;
        this.scr2 = 0;
    }

    public void setNewPlayerSettings(int _playerId, double _maxSpeed, boolean _mouseControl, boolean _isComputer,
            int _paddleSize) {
        if (_playerId == 1) {
            this.player1MaxSpeed = _maxSpeed;
            this.player1MouseControl = _mouseControl;
            this.player1isComputer = _isComputer;
            this.player1PaddleSize = _paddleSize;
        } else {
            this.player2MaxSpeed = _maxSpeed;
            this.player2MouseControl = _mouseControl;
            this.player2isComputer = _isComputer;
            this.player2PaddleSize = _paddleSize;
        }
    }

    public void setSizeValues() {
        switch (gameSize) {
            case 1:
                this.WIDTH = 1699 / 2;
                this.HEIGHT = 982 / 2;
                this.midLineY = 882;
                this.PADDLE_WIDTH = 25;
                // this.PADDLE_HEIGHT = 160;
                this.BALL_RADIUS = 25;
                break;
            case 2:
                this.WIDTH = 1299 / 2;
                this.HEIGHT = 936 / 2;
                this.midLineY = 836;
                this.PADDLE_WIDTH = 25;
                // this.PADDLE_HEIGHT = 140;
                this.BALL_RADIUS = 25;
                break;
            case 3:
                this.WIDTH = 1043 / 2;
                this.HEIGHT = 700 / 2;
                this.midLineY = 600;
                this.PADDLE_WIDTH = 25;
                // this.PADDLE_HEIGHT = 120;
                this.BALL_RADIUS = 20;
                break;
            case 4:
                this.WIDTH = 800 / 2;
                this.HEIGHT = 600 / 2;
                this.midLineY = 500;
                this.PADDLE_WIDTH = 22;
                // this.PADDLE_HEIGHT = 100;
                this.BALL_RADIUS = 15;
                break;
            default:
                break;
        }
    }
}
