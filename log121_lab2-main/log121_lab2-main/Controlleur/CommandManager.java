package Controlleur;

import Modele.Perspective;
import Modele.PerspectiveMomento;

import java.util.HashMap;
import java.util.Stack;

public class CommandManager {

    private static CommandManager instance = null;
    private PerspectiveMomento defaultPerspective;
    private HashMap<Perspective, Stack<PerspectiveMomento>> historique;
    private HashMap<Perspective, Stack<PerspectiveMomento>> undoHistorique;

    /**
     * The private constructor of the HistoryManager class
     */
    private CommandManager(){
        historique = new HashMap<>();
        undoHistorique = new HashMap<>();
    }

    /**
     * The instance getter
     * @return The instance
     */
    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }

        return instance;
    }

    /**
     * Save a snapshot of the given perspective to history.
     * @param p The given perspective
     */
    public void save(Perspective p) {
        save(p, historique);
    }

    /**
     * Save a snapshot of the given perspective to the undo history.
     * @param p The given perspective
     */
    private void saveUndo(Perspective p) {
        save(p, undoHistorique);
    }

    /**
     * Save a snapshot of the given perspective to the given history.
     * @param p The given perspective
     * @param history The given history
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
     * Undo the last changes applied to the given perspective.
     * @param p The given perspective
     */
    public void undo(Perspective p) {
        if (!historique.containsKey(p)) return;
        if (historique.get(p).isEmpty()) return;

        saveUndo(p);
        p.restore(historique.get(p).pop());
    }

    /**
     * Redo the last undone changes from the given perspective.
     * @param p The given perspective
     */
    public void redo(Perspective p) {
        if (!undoHistorique.containsKey(p)) return;
        if (undoHistorique.get(p).isEmpty()) return;

        save(p);
        p.restore(undoHistorique.get(p).pop());
    }

    /**
     * Reset the given perspective
     * @param p The given perspective
     */
    public void reset(Perspective p) {
        save(p);
        p.restore(defaultPerspective);
    }

    /**
     * The default perspective setter
     * @param p The given perspective to make default
     */
    public void setDefaultPerspective(Perspective p) {
        defaultPerspective = p.takeDefaultSnapshot();
    }

    /**
     * Clear the histories
     */
    public void clearHistory() {
        historique.clear();
        undoHistorique.clear();
    }
}
