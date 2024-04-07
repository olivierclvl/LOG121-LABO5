package Controlleur;

import Modele.Perspective;
import Modele.PerspectiveMemento;
import java.util.HashMap;
import java.util.Stack;

public class CommandeManager {
    private static CommandeManager instance = null;
    private PerspectiveMemento defaultPerspective;
    private HashMap<Perspective, Stack<PerspectiveMemento>> history;
    private HashMap<Perspective, Stack<PerspectiveMemento>> undoHistory;
    private CommandeManager(){
        history = new HashMap<>();
        undoHistory = new HashMap<>();
    }

    public static CommandeManager getInstance() {
        if (instance == null) {
            instance = new CommandeManager();
        }

        return instance;
    }

    public void save(Perspective p) {
        save(p, history);
    }

    private void saveUndo(Perspective p) {
        save(p, undoHistory);
    }

    private void save(Perspective p, HashMap<Perspective, Stack<PerspectiveMemento>> history) {
        Stack<PerspectiveMemento> persHist;
        if (history.containsKey(p)) {
            persHist = history.get(p);
        }
        else {
            persHist = new Stack<>();
        }
        persHist.push(p.takeSnapshot());
        history.put(p,persHist);
    }

    public void undo(Perspective p) {
        if (!history.containsKey(p)) return;
        if (history.get(p).isEmpty()) return;

        saveUndo(p);
        p.restore(history.get(p).pop());
    }

    public void redo(Perspective p) {
        if (!undoHistory.containsKey(p)) return;
        if (undoHistory.get(p).isEmpty()) return;

        save(p);
        p.restore(undoHistory.get(p).pop());
    }

    public void reset(Perspective p) {
        save(p);
        p.restore(defaultPerspective);
    }

    public void setDefaultPerspective(Perspective p) {
        defaultPerspective = p.takeDefaultSnapshot();
    }

    public void clearHistory() {
        history.clear();
        undoHistory.clear();
    }
}
