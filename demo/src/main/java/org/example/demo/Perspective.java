package org.example.demo;

import javafx.geometry.Point2D;

import java.awt.*;

public class Perspective extends Sujet{
    private double scale;
    private Point position;

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
