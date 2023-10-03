package model;

import java.io.Serializable;

public class gameConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    public boolean player1MouseControl;
    public boolean isSoloGame;
    public int gameSize;
    public boolean soundOn;
    public double ballSpeed;
    public double playerSpeed;

    public int WIDTH;
    public int HEIGHT;
    public int PADDLE_WIDTH;
    public int PADDLE_HEIGHT;
    public int BALL_RADIUS;
    public double PADDLE_SPEED;
    public double BALL_SPEED;

    public gameConfiguration() {
        this.player1MouseControl = false;
        this.isSoloGame = true;
        this.gameSize = 2;
        this.soundOn = true;
        this.ballSpeed = 0;
        this.playerSpeed = 0;
    }

    public gameConfiguration(boolean _player1MouseControl, boolean _player2IsComputer, int _gameSize,
            boolean _soundOn, double _ballSpeed,
            double _playerSpeed) {
        this.isSoloGame = true;
        this.player1MouseControl = _player1MouseControl;
        this.gameSize = _gameSize;
        this.soundOn = _soundOn;
        this.ballSpeed = _ballSpeed;
        this.playerSpeed = _playerSpeed;
    }

    public void setSizeValues() {

        switch (gameSize) {
            case 3:
                this.WIDTH = 1699 / 2;
                this.HEIGHT = 982 / 2;
                this.PADDLE_WIDTH = 25;
                this.PADDLE_HEIGHT = 160;
                this.BALL_RADIUS = 30;
                break;
            case 2:
                this.WIDTH = 1299 / 2;
                this.HEIGHT = 956 / 2;
                this.PADDLE_WIDTH = 25;
                this.PADDLE_HEIGHT = 140;
                this.BALL_RADIUS = 25;
                break;
            case 1:
                this.WIDTH = 1043 / 2;
                this.HEIGHT = 700 / 2;
                this.PADDLE_WIDTH = 25;
                this.PADDLE_HEIGHT = 120;
                this.BALL_RADIUS = 20;
                break;
            default:
                break;
        }
    }
}
