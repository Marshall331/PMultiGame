package model;

import java.io.Serializable;
import static java.lang.Math.PI;

public class gameConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    public final double C = PI / 4.0;
    public final double MAG = Math.sqrt(26);

    public double player1MaxSpeed;
    public boolean isPlayer1MouseControl;
    public boolean isPlayer1Computer;
    public int player1PaddleSize;
    public String player1Color;

    public double player2MaxSpeed;
    public boolean isPlayer2MouseControl;
    public boolean isPlayer2Computer;
    public int player2PaddleSize;
    public String player2Color;

    public double ballBaseSpeedY;
    public double ballBaseSpeedX;
    public double ballMaxSpeed;
    public double ballAcc;
    public double ballSize;
    public String ballColor;

    public boolean isGameSizeChanged;
    public boolean isSoloGame;
    public int gameSize;
    public boolean isSoundOn;

    public int scr1;
    public int scr2;

    public int WIDTH;
    public int HEIGHT;
    public int midLineY;
    public int PADDLE_WIDTH;
    public double PADDLE_SPEED;

    public boolean isConfHasChanged;

    public int menuButtonWidth;
    public int menuButtonFontSize;
    public int settingsButtonRightMargin;
    public int labScoreWidth;
    public int labScoreFontSize;
    public int labScoreMargin;
    public int labGoalFontSize;

    public gameConfiguration() {

        this.player1MaxSpeed = 5;
        this.isPlayer1MouseControl = false;
        this.isPlayer1Computer = false;
        this.player1PaddleSize = 140;
        this.player1Color = "#8808a3";

        this.player2MaxSpeed = 5;
        this.isPlayer2MouseControl = false;
        this.isPlayer2Computer = true;
        this.player2PaddleSize = 140;
        this.player2Color = "#18ab05";

        this.ballBaseSpeedY = 3;
        this.ballBaseSpeedX = 3;
        this.ballMaxSpeed = 30;
        this.ballAcc = 1.1;
        this.ballSize = 25;
        this.ballColor = "#4000ff";

        this.isSoloGame = false;
        this.gameSize = 2;
        this.isSoundOn = true;

    }

    public void setSizeValues() {
        switch (gameSize) {
            case 1:
                this.WIDTH = 1699 / 2;
                this.HEIGHT = 982 / 2;
                this.midLineY = 882;
                this.PADDLE_WIDTH = 25;
                menuButtonWidth = 250;
                menuButtonFontSize = 31;
                settingsButtonRightMargin = 270;
                labScoreWidth = 130;
                labScoreFontSize = 65;
                labScoreMargin = 85;
                labGoalFontSize = 110;
                break;
            case 2:
                this.WIDTH = 1299 / 2;
                this.HEIGHT = 936 / 2;
                this.midLineY = 836;
                this.PADDLE_WIDTH = 25;
                menuButtonWidth = 200;
                menuButtonFontSize = 31;
                settingsButtonRightMargin = 220;
                labScoreWidth = 115;
                labScoreFontSize = 55;
                labScoreMargin = 70;
                labGoalFontSize = 95;
                break;
            case 3:
                this.WIDTH = 1043 / 2;
                this.HEIGHT = 700 / 2;
                this.midLineY = 600;
                this.PADDLE_WIDTH = 25;
                menuButtonWidth = 150;
                menuButtonFontSize = 31;
                settingsButtonRightMargin = 170;
                labScoreWidth = 100;
                labScoreFontSize = 45;
                labScoreMargin = 55;
                labGoalFontSize = 80;
                break;
            case 4:
                this.WIDTH = 800 / 2;
                this.HEIGHT = 600 / 2;
                this.midLineY = 500;
                this.PADDLE_WIDTH = 22;
                menuButtonWidth = 125;
                menuButtonFontSize = 26;
                settingsButtonRightMargin = 155;
                labScoreWidth = 85;
                labScoreFontSize = 35;
                labScoreMargin = 40;
                labGoalFontSize = 60;
                break;
            case 5:
                this.WIDTH = 600 / 2;
                this.HEIGHT = 400 / 2;
                this.midLineY = 300;
                this.PADDLE_WIDTH = 20;
                menuButtonWidth = 100;
                menuButtonFontSize = 21;
                settingsButtonRightMargin = 120;
                labScoreWidth = 70;
                labScoreFontSize = 35;
                labScoreMargin = 25;
                labGoalFontSize = 45;
                break;
            default:
                break;
        }
    }

    public void setNewGameSpeedSettings(double _ballBaseSpeedY, double _ballBaseSpeedX, double _maxSpeed, double _acc,
            double _size, String _color) {
        this.ballBaseSpeedY = _ballBaseSpeedY;
        this.ballBaseSpeedX = _ballBaseSpeedX;
        this.ballMaxSpeed = _maxSpeed;
        this.ballAcc = _acc;
        this.ballSize = _size;
        this.ballColor = _color;
    }

    public void setNewPlayerSettings(int _playerId, double _maxSpeed, boolean _mouseControl, boolean _isComputer,
            int _paddleSize, String _color) {
        if (_playerId == 1) {
            this.player1MaxSpeed = _maxSpeed;
            this.isPlayer1MouseControl = _mouseControl;
            this.isPlayer1Computer = _isComputer;
            this.player1PaddleSize = _paddleSize;
            this.player1Color = _color;
        } else {
            this.player2MaxSpeed = _maxSpeed;
            this.isPlayer2MouseControl = _mouseControl;
            this.isPlayer2Computer = _isComputer;
            this.player2PaddleSize = _paddleSize;
            this.player2Color = _color;
        }
    }

    public void resetScore() {
        this.scr1 = 0;
        this.scr2 = 0;
    }

    public void setScores(int _scr1, int _scr2) {
        this.scr1 = _scr1;
        this.scr2 = _scr2;
    }
}