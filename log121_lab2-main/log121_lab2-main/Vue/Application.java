package Vue;

import Controlleur.Controlleur;

import javax.swing.SwingUtilities;

/**
 *
 * Ce module permet de représenter la classe application. elle permet de gérer l'interaction entre l'utilisateur
 * et l'application
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class Application {

    public static void main(String[] args) {

        Controlleur controlleur = new Controlleur();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Vue(controlleur);
            }
        });
    }
}
