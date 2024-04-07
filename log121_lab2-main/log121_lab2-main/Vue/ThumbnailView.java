package Vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Modele.Image;
import Modele.Sujet;

public class ThumbnailView extends JPanel implements Observateur {
    private BufferedImage image;
    protected Point imgPos = new Point(0, 0);
    protected Point imgDimension = new Point(0, 0);

    /**
     * The constructor of the Panel class.
     */
    public ThumbnailView() {
        setPreferredSize(new Dimension(200, 200));
    }

    /**
     * Draw the image
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g.drawImage(image, imgPos.x, imgPos.y, imgDimension.x, imgDimension.y, null);
        }
    }

    /**
     * Update the panel with the given subject
     * @param s the given subject
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