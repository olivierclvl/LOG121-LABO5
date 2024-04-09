package Vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Modele.Image;
import Modele.Sujet;

/**
 *
 * Ce module représente la vue non modifiable. on ne peut ni zoomer, ni translater
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class ThumbnailView extends JPanel implements Observateur {
    private BufferedImage image;
    protected Point imgPos = new Point(0, 0);
    protected Point imgDimension = new Point(0, 0);

    /**
     * Ce constructeur crée la vue non modifiable thumbnailview
     *
     *@return
     *         Crée le panneau de la vue non modifiable
     */
    public ThumbnailView() {
        setPreferredSize(new Dimension(200, 200));
    }


    /**
     * Dessine l'image
     * @param g
     *        l'objet <code>Graphics</code> à protéger
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g.drawImage(image, imgPos.x, imgPos.y, imgDimension.x, imgDimension.y, null);
        }
    }

    /**
     * met à jour la vue en fonction du sujet passé en paramètre
     * @param s
     *       le sujet donné
     */
    @Override
    public void update(Sujet s) {
    	if (s instanceof Image) {
            this.image = ((Image) s).getImage();
            this.imgDimension = ((Image) s).getScaledDimension(new Point(getWidth(),getHeight()));
            repaint();
        }
    }
}