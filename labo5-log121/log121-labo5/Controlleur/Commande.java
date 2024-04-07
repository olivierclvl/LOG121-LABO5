package Controlleur;

import Modele.Perspective;

public abstract class Commande {
    public abstract void execute(Perspective p, double x, double y);
}
