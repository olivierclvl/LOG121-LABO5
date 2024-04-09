package Vue;

import Modele.Sujet;

/**
 *
 * Ce module représente l'interface observateur qui contient la déclaration de la méthode update
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public interface Observateur {

    /**
     * Est appelée lorsqu'il ya des modifications au niveau sujet.
     * @param s
     *      le sujet qui a changé.
     */
    void update(Sujet s);
}
