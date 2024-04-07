package Modele;

import Observer.Observateur;

import java.util.ArrayList;

public abstract class Sujet {
    private ArrayList<Observateur> listeObservateurs = new ArrayList<>();
    public void addObserver(Observateur o) {
        listeObservateurs.add(o);
    }
    public void removeObserver(Observateur o) {
        listeObservateurs.remove(o);
    }

    protected void notifyObserver() {
        for (Observateur o : listeObservateurs) {
            o.update(this);
        }
    }
}
