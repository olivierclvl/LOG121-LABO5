package Vue;

import Modele.Sujet;

public interface Observateur {
    /**
     * Called when the subject changes.
     * @param s the subject that changed.
     */
    void update(Sujet s);
}
