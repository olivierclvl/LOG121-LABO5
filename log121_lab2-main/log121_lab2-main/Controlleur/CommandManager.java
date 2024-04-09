package Controlleur;

import Modele.Perspective;
import Modele.PerspectiveMomento;

import java.util.HashMap;
import java.util.Stack;

/**
 *
 * Ce module permet de de gérer l'historique des commandes faites sur une vue.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class CommandManager {

    private static CommandManager instance = null;
    private PerspectiveMomento defaultPerspective;
    private HashMap<Perspective, Stack<PerspectiveMomento>> historique;
    private HashMap<Perspective, Stack<PerspectiveMomento>> undoHistorique;

    /**
     * Le ocnstructeur privé de la classe CommandManger
     */
    private CommandManager(){
        historique = new HashMap<>();
        undoHistorique = new HashMap<>();
    }

    /**
     * Permet de récupérer une instance unique de CommandManger
     * @return
     *       l'instance de CommandManger
     */
    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }

        return instance;
    }

    /**
     * Sauvegarde la capture d'état d'une perspective donnée dans la pile historique.
     * @param p
     *        La perspective sélectionnée
     */
    public void save(Perspective p) {
        save(p, historique);
    }

    /**
     * Sauvegarde la capture d'état d'une perspective donnée dans la pile undoHistorique.
     * @param p
     *        Sauvegarde la capture d'état d'une perspective donnée dans l'historique.
     */
    private void saveUndo(Perspective p) {
        save(p, undoHistorique);
    }

    /**
     * Sauvegarde la capture d'état d'une perspective donnée dans la pile d'historique donnée
     * @param p
     *        La perspective donnée
     * @param history
     *        la pile d'historique donnée
     */
    private void save(Perspective p, HashMap<Perspective, Stack<PerspectiveMomento>> history) {
        Stack<PerspectiveMomento> persHist;
        if (history.containsKey(p)) {
            persHist = history.get(p);
        }
        else {
            persHist = new Stack<>();
        }
        persHist.push(p.takeSnapshot());
        history.put(p,persHist);
    }

    /**
     * Enlève ou défait les derniers changements appliqués à une perspective
     * @param p
     *       La perspective passée en paramètre
     */
    public void undo(Perspective p) {
        if (!historique.containsKey(p)) return;
        if (historique.get(p).isEmpty()) return;

        saveUndo(p);
        p.restore(historique.get(p).pop());
    }

    /**
     * Reapplique les derniers changements défaits appliqués à une perspective
     * @param p
     *        La perspective passée en paramètre
     */
    public void redo(Perspective p) {
        if (!undoHistorique.containsKey(p)) return;
        if (undoHistorique.get(p).isEmpty()) return;

        save(p);
        p.restore(undoHistorique.get(p).pop());
    }

    /**
     * Remet la perspective à son état initial
     * @param p
     *        La perspective passée en paramètre
     */
    public void reset(Perspective p) {
        save(p);
        p.restore(defaultPerspective);
    }

    /**
     * Défini une perspective par défaut
     * @param p
     *        La perspective à mettre par défaut
     */
    public void setDefaultPerspective(Perspective p) {
        defaultPerspective = p.takeDefaultSnapshot();
    }

    /**
     * Efface les piles d'historiques
     */
    public void clearHistory() {
        historique.clear();
        undoHistorique.clear();
    }
}
