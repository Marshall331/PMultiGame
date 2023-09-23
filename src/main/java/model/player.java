package model;

import javafx.scene.shape.Rectangle;

public final class player {

    public Rectangle paddle;
    public double vel;
    public final double maxSpeed;
    public boolean isComputer;
    public boolean isSpeedLimited;
    public double mouseMove;

    public player(Rectangle _paddle, boolean _isCPU, double _maxSpeed, boolean _speedLimit) {
        this.paddle = _paddle;
        this.isComputer = _isCPU;
        this.maxSpeed = _maxSpeed;
        this.isSpeedLimited = _speedLimit;
    }

    public void move(double vel) {
        this.vel = vel;
        paddle.setTranslateY(paddle.getTranslateY() + vel);
    }

    public void mouseMove() {
        if (isSpeedLimited) {
            if (paddle.getTranslateY() - 5 > mouseMove) {
                this.vel = -5;
            } else if (paddle.getTranslateY() + 5 < mouseMove) {
                this.vel = 5;
            } else {
                this.vel = 0;
            }
        } else {
            paddle.setTranslateY(mouseMove);
        }
    }
}
