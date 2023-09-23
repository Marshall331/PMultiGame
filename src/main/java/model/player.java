package model;

import javafx.scene.shape.Rectangle;

public final class player {

    public Rectangle rect;
    public double vel;
    public final double maxSpeed;
    public boolean isComputer;
    public double mouseMove;

    public player(Rectangle _rect, boolean _isCPU, double _maxSpeed) {
        this.rect = _rect;
        this.isComputer = _isCPU;
        this.maxSpeed = _maxSpeed;
    }

    public void move(double vel) {
        this.vel = vel;
        rect.setTranslateY(rect.getTranslateY() + vel);
    }

    public void mouseMove(boolean isSpeedLimited) {

        if (rect.getTranslateY() - 5 > mouseMove) {
            this.vel = -5;
        } else if (rect.getTranslateY() + 5 < mouseMove) {
            this.vel = 5;
        } else {
            this.vel = 0;
        }
        // rect.setTranslateY(mouseMove);

        System.out.println("Rect = " + rect.getTranslateY() + " Mouse : " + mouseMove);
    }
}
