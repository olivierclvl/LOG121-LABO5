package Application;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Controlleur controlleur = new Controlleur();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Vue(controlleur);
            }
        });
    }
}
