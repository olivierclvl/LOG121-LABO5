package Application;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new View(controller);
            }
        });
    }
}
