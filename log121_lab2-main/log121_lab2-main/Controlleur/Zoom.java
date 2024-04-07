package Controlleur;

import Modele.Perspective;

public class Zoom extends Command{
	private final double ZOOM_MULTIPLICATOR = 20;

	/**
     * Zoom the image: Convert the factor to a scale for the image.
     * @param p The perspective on which the drag is executed.
     * @param factor The zoom factor.
	 * @param modifiers The mask modifiers.
     */
    @Override
    public void execute(Perspective p, double factor, double modifiers) {
		double newScale = p.getScale() - factor * ZOOM_MULTIPLICATOR;
		p.setScaleFromZoom(newScale);
    }
}
