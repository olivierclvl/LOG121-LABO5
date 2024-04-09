package Controlleur;

import Modele.Perspective;
import java.awt.Point;

/**
 *
 * Ce module permet d'exécuter une translation ou déplacement de l'image grace à l'implémentation de la méthode execute.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class Translation extends Command{

    /**
     * Fait glisser l'image vers un nouveau point
     * @param perspective
     *        La perspective sur laquelle le déplacement est effectué
     * @param xTranslation
     *        La translation sur l'axe x
     * @param yTranslation
     *        La translation sur l'axe y
     */
    @Override
    public void execute(Perspective perspective, double xTranslation, double yTranslation) {
        Point startPoint = perspective.getPosition();
        Point newPos = new Point(startPoint.x+(int)xTranslation, startPoint.y+(int)yTranslation);
        perspective.setPosition(newPos);
    }
}
