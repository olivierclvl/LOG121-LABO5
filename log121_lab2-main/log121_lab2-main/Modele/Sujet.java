package Modele;

import Vue.Observateur;

import java.util.ArrayList;

/**
 *
 * Ce module permet de représenter la classe abstraite Sujet. elle permet de lier les observateurs à un sujet afin
 * qu'ils soient notifier des changements le moment opportun
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public abstract class Sujet {
    private ArrayList<Observateur> observateurs = new ArrayList<>();

    /**
     * Assigne un observateur à un sujet
     * @param o
     *         un observateur
     */
    public void attachObserver(Observateur o) {
        observateurs.add(o);
    }

    /**
     * désassigne un observateur à un sujet
     * @param o
     *         un observateur
     */
    public void detachObserver(Observateur o) {
        observateurs.remove(o);
    }

    /**
     * Notifie tous les observateurs d'un changement
     */
    protected void notifyObservers() {
        for (Observateur o : observateurs) {
            o.update(this);
        }
    }
}
