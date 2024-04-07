package Observer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Modele.Image;
import Modele.Sujet;

public class Panneau extends JPanel implements Observateur {
    private BufferedImage image;
    protected Point imgPos = new Point(0, 0);
    protected Point imgDimension = new Point(0, 0);

    public Panneau() {
        setPreferredSize(new Dimension(200, 200));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g.drawImage(image, imgPos.x, imgPos.y, imgDimension.x, imgDimension.y, null);
        }
    }

    @Override
    public void update(Sujet s) {
    	if (s instanceof Image) {
            this.image = ((Image) s).getImage();
            this.imgDimension = ((Image) s).getScaledDimension(new Point(getWidth(),getHeight()));
            repaint();
        }
    }
}