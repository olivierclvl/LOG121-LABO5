package Modele;

import java.awt.Point;
import Controlleur.Commande;

public class Perspective extends Sujet {
    private final Point DEFAULT_POS = new Point(0, 0);
    private final double DEFAULT_SCALE = 0;
    private Point position = new Point(0, 0);
    private Point imageDimension = new Point(0, 0);
    private double scale = 0;
    private Commande zoomCommande;
    private Commande dragCommande;

    public void executeZoom(double zoomFactor, double modifiers) {
        zoomCommande.execute(this, zoomFactor, modifiers);
    }

    public void executeDrag(double xTranslation, double yTranslation) {
        dragCommande.execute(this, xTranslation, yTranslation);
    }

    public void setCommands(Commande zoomCommande, Commande dragCommande) {
        this.zoomCommande = zoomCommande;
        this.dragCommande = dragCommande;
    }

    public Point getPosition() {
        return position;
    }


    public void setPosition(Point pos) {
        this.position = pos;
        notifyObserver();
    }

    public double getScale() {
    	return scale;
    }

    public void setScaleFromZoom(double scale) {
        if (scale + imageDimension.x <= 0 || scale + imageDimension.y <= 0) return;
        setScale(scale);
    }

    public void setScale(double scale) {
        this.scale = scale;
        notifyObserver();
    }

    public void setImageDimension(Point dimension) {
        this.imageDimension = dimension;
        notifyObserver();
    }

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

    public PerspectiveMemento takeSnapshot() {
        return new PerspectiveMemento(position, imageDimension, scale);
    }

    public PerspectiveMemento takeDefaultSnapshot() {
        return new PerspectiveMemento(DEFAULT_POS, imageDimension, DEFAULT_SCALE);
    }

    public void restore(PerspectiveMemento memento) {
        this.position = memento.getPosition();
        this.imageDimension = memento.getImageDimension();
        this.scale = memento.getScale();
        notifyObserver();
    }

}
