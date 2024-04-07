package Utils;

import java.util.ArrayList;

public abstract class Subject {
    private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Attach an observer to the subject.
     * @param o the observer to attach.
     */
    public void attachObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Detach the given observer from the subject.
     * @param o The observer to detach
     */
    public void detachObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Notify all the observers of a change.
     */
    protected void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }
}
