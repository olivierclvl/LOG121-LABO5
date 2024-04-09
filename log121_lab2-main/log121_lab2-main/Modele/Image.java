package Modele;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * Ce module permet de représenter lune image. elle permet de consigner les caractéristiques d'une image.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class Image extends Sujet implements Serializable{

	private transient final FileNameExtensionFilter filter =
			                     new FileNameExtensionFilter("image(.jpg)", "jpg", ".jpeg", "jpeg");
	private transient BufferedImage image;
	private String imagePath;

	/**
	 * Charge l'image contenue dans le fichier donné
	 * @param file
	 *        Le fichier donné
	 */
	public void loadImage(File file) {
		imagePath = file.getAbsolutePath();
		try {
			image=ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		notifyObservers();
	}

	/**
	 * Récupère l'image choisie
	 * @return
	 *        l'image mise en mémoire tampon
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Récupère le filtre de l'extension du fichier image
	 * @return
	 *        le filtre de l'extension du fichier image
	 */
	public FileNameExtensionFilter getFilter() {
		return filter;
	}

	/**
	 * Met à l'échelle les dimensions de l'image pour correspondre au panneau
	 * @param panelDim
	 *        La dimension du panneau
	 * @return
	 *       La nouvelle dimension mise à l'échelle
	 */
	public Point getScaledDimension(Point panelDim) {
		int width = image.getWidth();
		int height = image.getHeight();
		double scaleX = (double) panelDim.x / width;
		double scaleY =  (double) panelDim.y / height;
		double scale = Math.min(scaleX, scaleY);
		int scaledWidth = (int) (scale*width);
		int scaledHeight = (int) (scale*height);

		return new Point(scaledWidth,scaledHeight);
	}

     /** Copier l'image mise en mémoire tampon de l'image spécifiée.
     * @param image
	  *       L'image à copier
     */
    public void copy(Image image) {
		loadImage(new File(image.imagePath));
    }
}