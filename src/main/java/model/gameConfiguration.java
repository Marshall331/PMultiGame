package model;

import java.io.Serializable;

public class gameConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    public boolean controlPlayer1Keyboard;
    public int gameSize;
    public boolean soundOn;
    public double ballSpeed;
    public double playerSpeed;

    public gameConfiguration() {
        this.controlPlayer1Keyboard = false;
        this.gameSize = 2;
        this.soundOn = true;
        this.ballSpeed = 0;
        this.playerSpeed = 0;
    }

    public gameConfiguration(boolean _controlPlayer1Keyboard, int _gameSize, boolean _soundOn, double _ballSpeed,
            double _playerSpeed) {
        this.controlPlayer1Keyboard = _controlPlayer1Keyboard;
        this.gameSize = _gameSize;
        this.soundOn = _soundOn;
        this.ballSpeed = _ballSpeed;
        this.playerSpeed = _playerSpeed;
    }
}
