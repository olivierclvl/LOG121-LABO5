package Observer;

import Controlleur.Controlleur;
import Modele.Perspective;
import Modele.Sujet;

import javax.swing.*;

public class PanneauModifiable extends Panneau {
    private Controlleur controlleur;

    public PanneauModifiable(Controlleur controlleur) {
        this.controlleur = controlleur;
        this.addMouseListener(controlleur);
        this.addMouseMotionListener(controlleur);
        this.addMouseWheelListener(controlleur);
        setup();
    }

    private void setup() {
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton resetButton = new JButton("Réinitialiser");
        this.add(undoButton);
        this.add(redoButton);
        this.add(resetButton);
        undoButton.addActionListener(controlleur);
        redoButton.addActionListener(controlleur);
        resetButton.addActionListener(controlleur);
    }

    @Override
    public void update(Sujet s) {
        if (s instanceof Perspective) {
            imgPos = ((Perspective)s).getPosition();
            imgDimension = ((Perspective)s).getDimension();
            repaint();
        }else {
        	super.update(s);
        }
    }
}
