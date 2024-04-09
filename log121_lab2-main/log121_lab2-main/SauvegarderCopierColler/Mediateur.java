package SauvegarderCopierColler;

import Modele.Perspective;

/**
 *
 * Ce module représente l'interface Mediateur qui gère les communications entre les différentes stratégies et
 * les perspectives. Elle contient la déclaration des méthodes Storecopy et paste
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public interface Mediateur {
	void storeCopy(CopieStrategie strategy);
    void paste(Perspective p);
}
