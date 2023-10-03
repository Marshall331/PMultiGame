package model;

import javafx.scene.shape.Rectangle;

public final class player {

    public Rectangle paddle;
    public double vel;
    public final double maxSpeed;
    public final boolean isComputer;
    public final boolean isSpeedLimited;
    public final boolean mouseControl;
    public double mouseMove;

    public player(Rectangle _paddle, boolean _isCPU, double _maxSpeed, boolean _speedLimit, boolean _mouseControl) {
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