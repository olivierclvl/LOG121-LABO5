package org.example.demo;

public class Zoom extends Commande{
    private final double ZOOM_MULTIPLICATOR = 20;
    @Override
    public void execute(Perspective p, double factor, double modifiers) {
        double newScale = p.getScale() - factor * ZOOM_MULTIPLICATOR;
        p.setScaleFromZoom(newScale);
    }
}
