package Commands;

import Application.Perspective;
import java.awt.Point;


public class Translation extends Commande {
    @Override
    public void execute(Perspective perspective, double xTranslation, double yTranslation) {
        Point startPoint = perspective.getPosition();
        Point newPos = new Point(startPoint.x+(int)xTranslation, startPoint.y+(int)yTranslation);
        perspective.setPosition(newPos);
    }
}
