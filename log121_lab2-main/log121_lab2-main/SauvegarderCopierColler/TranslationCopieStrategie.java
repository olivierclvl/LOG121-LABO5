package SauvegarderCopierColler;

import Modele.Perspective;

/**
 *
 * Ce module permet de d'implémenter la stratégie lorsqu'on ne veut copier la translation faite sur une perspective
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class TranslationCopieStrategie implements CopieStrategie {
	Perspective copiedParameters;

	/**
     * Copie la translation la plus récente faite sur une perspective
     * @param parameters
     *        Les paramètres de la perspective à copier
     */
    @Override
    public void copy(Perspective parameters) {
        copiedParameters = new Perspective();
        copiedParameters.setPosition(parameters.getPosition());
    }

    /**
     * Colle la translation copiée
     * @param parameters
     *        Les paramètres de la perspective à coller
     */
    @Override
    public void paste(Perspective parameters) {
        parameters.setPosition(copiedParameters.getPosition());
    }
}
