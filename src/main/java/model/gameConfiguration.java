package model;

import java.io.Serializable;

public class gameConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    public double player1MaxSpeed;
    public boolean player1MouseControl;
    public boolean player1isComputer;
    public boolean player1isSpeedLimited;

    public double player2MaxSpeed;
    public boolean player2MouseControl;
    public boolean player2isComputer;
    public boolean player2isSpeedLimited;

    public boolean gameSizeChanged;
    public boolean isSoloGame;
    public int gameSize;
    public boolean soundOn;
    // public double ballSpeed;
    // public double playerSpeed;
    public int scr1;
    public int scr2;

    public int WIDTH;
    public int HEIGHT;
    public int PADDLE_WIDTH;
    public int PADDLE_HEIGHT;
    public int BALL_RADIUS;
    public double PADDLE_SPEED;
    public double BALL_SPEED;

    public gameConfiguration() {

        this.player1MaxSpeed = 5;
        this.player1MouseControl = false;
        this.player1isComputer = false;
        this.player1isSpeedLimited = false;

        this.player2MaxSpeed = 5;
        this.player2MouseControl = false;
        this.player2isComputer = false;
        this.player2isSpeedLimited = false;

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
