package model;

import javafx.scene.shape.Rectangle;

public final class player{
   
    public Rectangle rect;
    public double vel;
    public boolean isComputer;
    
    public player(Rectangle _rect, boolean isCPU){
        rect = _rect;
        isComputer = isCPU;
    }

    public void move (double vel){
        this.vel = vel;
        rect.setTranslateY(rect.getTranslateY() + vel);
    }
}
