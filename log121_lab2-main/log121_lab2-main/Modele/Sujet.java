package Modele;

import Vue.Observateur;

import java.util.ArrayList;

public abstract class Sujet {
    private ArrayList<Observateur> observateurs = new ArrayList<>();

    /**
     * Attach an observer to the subject.
     * @param o the observer to attach.
     */
    public void attachObserver(Observateur o) {
        observateurs.add(o);
    }

    /**
     * Detach the given observer from the subject.
     * @param o The observer to detach
     */
    public void detachObserver(Observateur o) {
        observateurs.remove(o);
    }

    /**
     * Notify all the observers of a change.
     */
    protected void notifyObservers() {
        for (Observateur o : observateurs) {
            o.update(this);
        }
    }
}
