package SauvegarderCopierColler;

import Modele.Perspective;

/**
 *
 * Ce module représente l'interface CopieStrategie qui contient la déclaration des méthodes copy et paste
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public interface CopieStrategie {
	void copy(Perspective parameters);
    void paste(Perspective parameters);
}

