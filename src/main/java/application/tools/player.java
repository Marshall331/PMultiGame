package application.tools;

import javafx.scene.shape.Rectangle;

public final class player{
   
    public Rectangle rect;
    public double vel;

    public player(Rectangle _rect){
        rect = _rect;
    }

    public void move (double vel){
        this.vel = vel;
        rect.setTranslateY(rect.getTranslateY() + vel);
    }
}
