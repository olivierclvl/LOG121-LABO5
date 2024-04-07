package Commands;

import Application.Perspective;
import java.awt.Point;


public class Drag extends Command{

    /**
     * Drag the image to a new point
     * @param perspective The perspective on which the drag is executed
     * @param xTranslation The translation on the x axis
     * @param yTranslation The translation on the y axis
     */
    @Override
    public void execute(Perspective perspective, double xTranslation, double yTranslation) {
        Point startPoint = perspective.getPosition();
        Point newPos = new Point(startPoint.x+(int)xTranslation, startPoint.y+(int)yTranslation);
        perspective.setPosition(newPos);
    }
}
