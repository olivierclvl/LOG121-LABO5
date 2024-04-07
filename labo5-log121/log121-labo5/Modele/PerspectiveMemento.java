package Modele;

import java.awt.Point;
import java.io.Serializable;

public class PerspectiveMemento implements Serializable {
    private Point position;
    private Point imageDimension;
    private double scale;

    public PerspectiveMemento(Point position, Point imageDimension, double scale) {
        this.position = position;
        this.imageDimension = imageDimension;
        this.scale = scale;
    }

    public Point getPosition() {
        return position;
    }

    public Point getImageDimension() {
        return imageDimension;
    }

    public double getScale() {
        return scale;
    }
}
