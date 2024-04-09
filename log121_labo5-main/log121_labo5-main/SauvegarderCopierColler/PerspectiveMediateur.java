package SauvegarderCopierColler;

import Modele.Perspective;

/**
 *
 * Ce module permet de gèrer les communications entre les différentes stratégies et les perspectives.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class PerspectiveMediateur implements Mediateur {

	private CopieStrategie lastCopieStrategie;

	/**
     * Garde une copie de la stratégie de copie choisie dans le médiateur
     * @param strategy
     *        La stratégie à utiliser pour la copie
     */
    @Override
    public void storeCopy(CopieStrategie strategy) {
        lastCopieStrategie = strategy;
    }

    /**
     * Colle la copie d'une stratégie sur une perspective
     * @param p
     *       La perspective qui a reçu la copie
     */
    @Override
    public void paste(Perspective p) {
        if (lastCopieStrategie != null) {
            lastCopieStrategie.paste(p);
        }
    }

}
