package Modele;

import java.awt.Point;
import java.io.Serializable;

import Controlleur.Command;

public class Perspective extends Sujet {
    private final Point DEFAULT_POS = new Point(0, 0);
    private final double DEFAULT_SCALE = 0;
    private Point position = new Point(0, 0);
    private Point imageDimension = new Point(0, 0);
    private double scale = 0;
    private Command zoomCommand;
    private Command dragCommand;

    /**
     * Execute the zoom command
     * @param zoomFactor The zoom factor
     * @param modifiers The modifiers of the event
     */
    public void executeZoom(double zoomFactor, double modifiers) {
        zoomCommand.execute(this, zoomFactor, modifiers);
    }
    
    /**
     * Execute the drag command
     * @param xTranslation the translation the image must do on the x axis
     * @param yTranslation the translation the image must do on the y axis
     */
    public void executeDrag(double xTranslation, double yTranslation) {
        dragCommand.execute(this, xTranslation, yTranslation);
    }

    /**
     * Set both the zoom and the drag commands
     * @param zoomCommand
     * @param dragCommand
     */
    public void setCommands(Command zoomCommand, Command dragCommand) {
        this.zoomCommand = zoomCommand;
        this.dragCommand = dragCommand;
    }

    /**
     * The position getter
     * @return the position of the image
     */
    public Point getPosition() {
        return position;
    }


    /**
     * The position setter
     * @param pos the new position
     */
    public void setPosition(Point pos) {
        this.position = pos;
        notifyObservers();
    }

    /**
     * The scale getter
     * @return the scale of the zoom
     */
    public double getScale() {
    	return scale;
    }

    /**
     * The scale setter for the zoom method
     * @param scale The new scale
     */
    public void setScaleFromZoom(double scale) {
        if (scale + imageDimension.x <= 0 || scale + imageDimension.y <= 0) return;
        setScale(scale);
    }

    /**
     * The scale setter
     * @param scale The scale to add to the perspective
     */
    public void setScale(double scale) {
        this.scale = scale;
        notifyObservers();
    }

    /**
     * The image dimension setter.
     * @param dimension The dimension of the image
     */
    public void setImageDimension(Point dimension) {
        this.imageDimension = dimension;
        notifyObservers();
    }

    /**
     * The image dimension getter
     * @return The dimension of the image after applying the scale
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
     * Take a snapshot of the class.
     * @return The snapshot of the class
     */
    public PerspectiveMomento takeSnapshot() {
        return new PerspectiveMomento(position, imageDimension, scale);
    }

    /**
     * Take a snapshot of the class as its default state.
     * @return The snapshot of the class
     */
    public PerspectiveMomento takeDefaultSnapshot() {
        return new PerspectiveMomento(DEFAULT_POS, imageDimension, DEFAULT_SCALE);
    }

    /**
     * Restore the class to the given snapshot of it.
     * @param memento The given snapshot to restore.
     */
    public void restore(PerspectiveMomento memento) {
        this.position = memento.getPosition();
        this.imageDimension = memento.getImageDimension();
        this.scale = memento.getScale();
        notifyObservers();
    }

    /**
     * Represent a snapshot of the class.
     */
//    public static class Memento implements Serializable {
//        private Point position;
//        private Point imageDimension;
//        private double scale;
//
//        /**
//         * The constructor of the Memento class.
//         * @param position
//         * @param imageDimension
//         * @param scale
//         */
//        private Memento (Point position, Point imageDimension, double scale) {
//            this.position = position;
//            this.imageDimension = imageDimension;
//            this.scale = scale;
//        }
//
//        /**
//         * The position getter
//         * @return The position
//         */
//        private Point getPosition() {
//            return position;
//        }
//
//        /**
//         * The image dimension getter
//         * @return The image dimension
//         */
//        private Point getImageDimension() {
//            return imageDimension;
//        }
//
//        /**
//         * The scale getter
//         * @return The scale
//         */
//        private double getScale() {
//            return scale;
//        }
//    }
}
