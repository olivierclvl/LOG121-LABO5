package Vue;

import Controlleur.Controlleur;
import Modele.Perspective;
import Modele.Sujet;

import javax.swing.*;
import java.awt.*;

/**
 *
 * Ce module représente les vues modifiables. on peut zoomer et translater
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class PerspectiveView extends ThumbnailView {

    private Controlleur controlleur;

    /**
     * Ce constructeur crée la vue modifiable Perspaectiveview
     *
     *@return
     *         Crée le panneau des vues modifiables
     */
    public PerspectiveView(Controlleur controlleur) {
        this.controlleur = controlleur;
        this.addMouseListener(controlleur);
        this.addMouseMotionListener(controlleur);
        this.addMouseWheelListener(controlleur);

        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton resetButton = new JButton("Réinitialiser");
        this.add(undoButton, BorderLayout.SOUTH);
        this.add(redoButton);
        this.add(resetButton);
        undoButton.addActionListener(controlleur);
        redoButton.addActionListener(controlleur);
        resetButton.addActionListener(controlleur);


}

    /**
     * met à jour la vue en fonction du sujet passé en paramètre
     * @param s
     *       le sujet donné
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
