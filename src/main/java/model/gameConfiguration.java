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

    public gameConfiguration() {
        this.player1MouseControl = false;
        this.isSoloGame = true;
        this.gameSize = 2;
        this.soundOn = true;
        this.ballSpeed = 0;
        this.playerSpeed = 0;
    }

    public gameConfiguration(boolean _controlPlayer1Keyboard, boolean _player2IsComputer, int _gameSize,
            boolean _soundOn, double _ballSpeed,
            double _playerSpeed) {
        this.isSoloGame = true;
        this.player1MouseControl = _controlPlayer1Keyboard;
        this.gameSize = _gameSize;
        this.soundOn = _soundOn;
        this.ballSpeed = _ballSpeed;
        this.playerSpeed = _playerSpeed;
    }
}
