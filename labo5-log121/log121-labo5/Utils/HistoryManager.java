package Utils;

import Application.Perspective;

import java.util.HashMap;
import java.util.Stack;

public class HistoryManager {

    private static HistoryManager instance = null;
    private Perspective.Memento defaultPerspective;
    private HashMap<Perspective, Stack<Perspective.Memento>> history;
    private HashMap<Perspective, Stack<Perspective.Memento>> undoHistory;

    /**
     * The private constructor of the HistoryManager class
     */
    private HistoryManager(){
        history = new HashMap<>();
        undoHistory = new HashMap<>();
    }

    /**
     * The instance getter
     * @return The instance
     */
    public static HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }

        return instance;
    }

    /**
     * Save a snapshot of the given perspective to history.
     * @param p The given perspective
     */
    public void save(Perspective p) {
        save(p, history);
    }

    /**
     * Save a snapshot of the given perspective to the undo history.
     * @param p The given perspective
     */
    private void saveUndo(Perspective p) {
        save(p, undoHistory);
    }

    /**
     * Save a snapshot of the given perspective to the given history.
     * @param p The given perspective
     * @param history The given history
     */
    private void save(Perspective p, HashMap<Perspective, Stack<Perspective.Memento>> history) {
        Stack<Perspective.Memento> persHist;
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
        if (!history.containsKey(p)) return;
        if (history.get(p).isEmpty()) return;

        saveUndo(p);
        p.restore(history.get(p).pop());
    }

    /**
     * Redo the last undone changes from the given perspective.
     * @param p The given perspective
     */
    public void redo(Perspective p) {
        if (!undoHistory.containsKey(p)) return;
        if (undoHistory.get(p).isEmpty()) return;

        save(p);
        p.restore(undoHistory.get(p).pop());
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
        history.clear();
        undoHistory.clear();
    }
}
