package Utils;

public interface Observer {
    /**
     * Called when the subject changes.
     * @param s the subject that changed.
     */
    void update(Subject s);
}
