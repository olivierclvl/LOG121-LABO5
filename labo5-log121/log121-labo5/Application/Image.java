package Application;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import Utils.Subject;

public class Image extends Subject implements Serializable{
	private transient final FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg", "jpg", ".jpeg", "jpeg", ".png",".png", ".gif", "gif", ".tiff", "tiff", ".tif", "tif");
	private transient BufferedImage image;
	private String imagePath;
	
	/**
	 * Load the image contained in the given file
	 * @param file The given file
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
	 * The image getter
	 * @return the buffered image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * The file extention filter getter
	 * @return The file extention filter
	 */
	public FileNameExtensionFilter getFilter() {
		return filter;
	}

	/**
	 * Scale the dimension of the image to the panel
	 * @param panelDim The dimension of the panel
	 * @return The scaled dimension
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

     /** Copy the buffered image of the specified image.
     * @param image The image to copy
     */
    public void copy(Image image) {
		loadImage(new File(image.imagePath));
    }
}