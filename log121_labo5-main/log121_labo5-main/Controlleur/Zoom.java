package Controlleur;

import Modele.Perspective;

/**
 *
 * Ce module permet d'exécuter le zoom grace à l'implémentation de la méthode execute.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class Zoom extends Command{
	private final double ZOOM_MULTIPLICATOR = 20;

	/**
	 * Zoomer l'image : Convertir le facteur en une échelle pour l'image.
	 * @param p
	 *        La perspective sur laquelle le zoom est exécuté.
	 * @param factor
	 *        Le facteur de zoom.
	 * @param modifiers
	 *        Les modificateurs du masque.
	 */
    @Override
    public void execute(Perspective p, double factor, double modifiers) {
		double newScale = p.getScale() - factor * ZOOM_MULTIPLICATOR;
		p.setScaleFromZoom(newScale);
    }
}
