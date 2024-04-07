package Vue;

import Controlleur.Controlleur;
import Modele.Perspective;
import Modele.Sujet;

import javax.swing.*;

public class PerspectiveView extends ThumbnailView {

    private Controlleur controlleur;

    /**
     * The constructor of the AlterablePanel class
     * @param controlleur
     */
    public PerspectiveView(Controlleur controlleur) {
        this.controlleur = controlleur;
        this.addMouseListener(controlleur);
        this.addMouseMotionListener(controlleur);
        this.addMouseWheelListener(controlleur);

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
    
    /**
     * Update the panel with the given subject
     * @param s the given subject 
     */
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
