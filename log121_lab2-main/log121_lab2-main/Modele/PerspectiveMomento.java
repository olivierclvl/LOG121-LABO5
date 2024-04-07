package Modele;

import java.awt.*;
import java.io.Serializable;

public class PerspectiveMomento implements Serializable {

    private Point position;
    private Point imageDimension;
    private double scale;

    /**
     * The constructor of the Memento class.
     * @param position
     * @param imageDimension
     * @param scale
     */
    public PerspectiveMomento (Point position, Point imageDimension, double scale) {
        this.position = position;
        this.imageDimension = imageDimension;
        this.scale = scale;
    }

    /**
     * The position getter
     * @return The position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * The image dimension getter
     * @return The image dimension
     */
    public Point getImageDimension() {
        return imageDimension;
    }

    /**
     * The scale getter
     * @return The scale
     */
    public double getScale() {
        return scale;
    }

}
