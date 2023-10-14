package model;

import java.io.Serializable;

import javafx.scene.shape.Rectangle;

public class player {

    public final int id;
    public Rectangle paddle;
    public double vel;
    public double maxSpeed;
    public boolean isComputer;
    public boolean mouseControl;
    public double mouseMove;

    public int paddleSize;
    public int paddleMaxY;
    public int paddleMinY;

    public player() {
        this.id = 1;
        this.paddle = new Rectangle();
        this.isComputer = true;
        this.maxSpeed = 0;
        this.mouseControl = false;
    }

    public player(int _playerId, Rectangle _paddle, boolean _isCPU, double _maxSpeed, boolean _mouseControl) {
        this.id = _playerId;
        this.paddle = _paddle;
        this.isComputer = _isCPU;
        this.maxSpeed = _maxSpeed;
        this.mouseControl = _mouseControl;
    }

    public void move() {
        paddle.setTranslateY(paddle.getTranslateY() + vel);
    }

    public void mouseMove() {
        paddle.setTranslateY(mouseMove);
    }
}