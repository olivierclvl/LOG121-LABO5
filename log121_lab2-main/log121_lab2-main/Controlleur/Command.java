package Controlleur;

import Modele.Perspective;

/**
 *
 * Ce module permet de représenter la classe abstraite Command qui contient la déclaration de la méthode execute
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public abstract class Command {
    public abstract void execute(Perspective p, double x, double y);
}
