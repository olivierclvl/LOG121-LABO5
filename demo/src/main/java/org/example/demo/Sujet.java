package org.example.demo;
import java.util.ArrayList;

public abstract class Sujet {
    private ArrayList<Observateur> listeObservateur = new ArrayList<>();

    public void addObserver(Observateur o) {
        listeObservateur.add(o);
    }

    public void removeObserver(Observateur o) {
        listeObservateur.remove(o);
    }

    public void notifyObserver() {
        for (Observateur observateur : listeObservateur) {
            observateur.update(this);
        }
    }
}
