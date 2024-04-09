package Modele;

import java.awt.Point;

import Controlleur.Command;

/**
 *
 * Ce module permet de représenter une perspective. elle permet de consigner toutes les modifications qui peuvent
 * être appliquées à une vue modifiable comme le zoom, translation.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class Perspective extends Sujet {
    private final Point DEFAULT_POS = new Point(0, 0);
    private final double DEFAULT_SCALE = 0;
    private Point position = new Point(0, 0);
    private Point imageDimension = new Point(0, 0);
    private double scale = 0;
    private Command zoomCommand;
    private Command translationCommand;

    /**
     * Execute la commande zoom
     * @param zoomFactor
     *        Le facteur zoom
     * @param modifiers
     *       Le modificateur(ou multiplicateur) de l'évènement
     */
    public void executeZoom(double zoomFactor, double modifiers) {
        zoomCommand.execute(this, zoomFactor, modifiers);
    }
    
    /**
     * Execute la commande Translation
     * @param xTranslation
     *        La translation que l'image doit effectuer sur l'axe x
     * @param yTranslation
     *        La translation que l'image doit effectuer sur l'axe y
     */
    public void executeTranslation(double xTranslation, double yTranslation) {
        translationCommand.execute(this, xTranslation, yTranslation);
    }

    /**
     * Applique à la fois les commandes de zoom et de translation
     * @param zoomCommand
     *        la commande Zoom
     * @param translationCommand
     *        la commande Translation
     */
    public void setCommands(Command zoomCommand, Command translationCommand) {
        this.zoomCommand = zoomCommand;
        this.translationCommand = translationCommand;
    }

    /**
     * Récupère la position de l'image
     * @return
     *        la position de l'image
     */
    public Point getPosition() {
        return position;
    }


    /**
     * Change la position de l'image
     * @param pos
     *        la nouvelle position de l'image
     */
    public void setPosition(Point pos) {
        this.position = pos;
        notifyObservers();
    }

    /**
     * Récupère l'échelle du zoom
     * @return
     *        l'échelle du zoom
     */
    public double getScale() {
    	return scale;
    }

    /**
     * Change l'échelle du zoom
     * @param scale
     *       La nouvelle échelle du zoom
     */
    public void setScaleFromZoom(double scale) {
        if (scale + imageDimension.x <= 0 || scale + imageDimension.y <= 0) return;
        setScale(scale);
    }

    /**
     * change l'échelle de l'image de la perspective
     * @param scale
     *        la nouvelle échelle de l'image de la perspective
     */
    public void setScale(double scale) {
        this.scale = scale;
        notifyObservers();
    }

    /**
     * Change les dimensions de l'image
     * @param dimension
     *        la nouvelle dimension de l'image
     */
    public void setImageDimension(Point dimension) {
        this.imageDimension = dimension;
        notifyObservers();
    }

    /**
     * Récupère la dimension de l'image apres avoir appliqué le zoom
     * @return
     *        la nouvelle dimension de l'image apres avoir appliqué le zoom
     */
    public Point getDimension() {
        double scaleRatio;
        int scaledX, scaledY;
        if (imageDimension.x < imageDimension.y) {
            scaleRatio = (double) imageDimension.x /imageDimension.y;
            scaledX = (int)(scale*scaleRatio+ imageDimension.x);
            scaledY = (int)(scale+ imageDimension.y);
        }
        else {
            scaleRatio = (double) imageDimension.y /imageDimension.x;
            scaledX = (int)(scale+ imageDimension.x);
            scaledY = (int)(scale*scaleRatio+ imageDimension.y);
        }
        return new Point(scaledX,scaledY);
    }

    /**
     * Capture l'état actuel de la classe
     * @return
     *        L'état capturé de la classe
     */
    public PerspectiveMomento takeSnapshot() {
        return new PerspectiveMomento(position, imageDimension, scale);
    }

    /**
     * Capture l'état initial de la classe
     * @return
     *        L'état capturé de la classe
     */
    public PerspectiveMomento takeDefaultSnapshot() {
        return new PerspectiveMomento(DEFAULT_POS, imageDimension, DEFAULT_SCALE);
    }

    /**
     * Restaure la classe à l'instant passé en paramètre
     * @param memento
     *       l'instant de la classe à restaurer
     */
    public void restore(PerspectiveMomento memento) {
        this.position = memento.getPosition();
        this.imageDimension = memento.getImageDimension();
        this.scale = memento.getScale();
        notifyObservers();
    }


}
