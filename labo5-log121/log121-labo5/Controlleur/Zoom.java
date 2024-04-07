package Controlleur;

import Modele.Perspective;

public class Zoom extends Commande {
	private final int MULTIPLICATEUR = 15;

    @Override
    public void execute(Perspective p, double factor, double modifiers) {
		double newScale = p.getScale() - (factor * MULTIPLICATEUR);
		p.setScaleFromZoom(newScale);
    }
}
