package model;

import javafx.scene.shape.Rectangle;

public final class player {

    public final int id;
    public Rectangle paddle;
    public double vel;
    public double maxSpeed;
    public boolean isComputer;
    public boolean isSpeedLimited;
    public boolean mouseControl;
    public double mouseMove;

    public player(int _playerId, Rectangle _paddle, boolean _isCPU, double _maxSpeed, boolean _speedLimit,
            boolean _mouseControl) {
        this.id = _playerId;
        this.paddle = _paddle;
        this.isComputer = _isCPU;
        this.maxSpeed = _maxSpeed;
        this.isSpeedLimited = _speedLimit;
        this.mouseControl = _mouseControl;
    }

    public void move() {
        paddle.setTranslateY(paddle.getTranslateY() + vel);
    }

    public void mouseMove() {
        if (isSpeedLimited) {
            if (paddle.getTranslateY() - maxSpeed > mouseMove) {
                this.vel = -maxSpeed;
                this.move();
            } else if (paddle.getTranslateY() + maxSpeed < mouseMove) {
                this.vel = maxSpeed;
                this.move();
            } else {
                this.vel = 0;
            }
        } else {
            paddle.setTranslateY(mouseMove);
        }
    }
}