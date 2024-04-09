package SauvegarderCopierColler;

import Modele.Perspective;

/**
 *
 * Ce module permet de d'implémenter la stratégie lorsqu'on ne veut faire aucune copie
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class AucuneCopieStrategie implements CopieStrategie {

	/**
     * Stratégie de copie vide
     * @param parameters
	 *        Les paramètres de la perspective à copier
     */
	@Override
	public void copy(Perspective parameters) {
		
	}

	/**
     * colle la stratégie de collage vide
     * @param parameters
	 *         Les paramètres de la perspective à coller
     */
	@Override
	public void paste(Perspective parameters) {

	}

}
