package application.tools;

import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Style {

    public static void setBallColor(Circle _ball, String _color) {
        RadialGradient ballColor = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#000000")),
                new Stop(1, Color.web(_color)));
        _ball.setFill(ballColor);
    }

    public static void setPlayerColor(Rectangle _player, String _color) {
        RadialGradient playerColor = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#000000")),
                new Stop(1, Color.web(_color)));
        _player.setFill(playerColor);
    }
}
