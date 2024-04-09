package SauvegarderCopierColler;

import Modele.Perspective;

/**
 *
 * Ce module permet de d'implémenter la stratégie lorsqu'on ne veut copier le zoom fait sur une perspective
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class ZoomCopieStrategie implements CopieStrategie {
	Perspective copiedParameters;

	/**
	 * Copie l'échelle du zoom le plus récent fait sur une perspective
	 * @param parameters
	 *        Les paramètres de la perspective à copier
	 */
	@Override
	public void copy(Perspective parameters) {
		copiedParameters = new Perspective();
		copiedParameters.setScale(parameters.getScale());
	}

	/**
	 * Colle l'échelle du zoom copiée
	 * @param parameters
	 *        Les paramètres de la perspective à coller
	 */
	@Override
	public void paste(Perspective parameters) {
		parameters.setScale(copiedParameters.getScale());
	}

}
