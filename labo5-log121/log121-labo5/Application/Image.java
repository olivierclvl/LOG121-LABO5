package Application;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import Utils.Sujet;

public class Image extends Sujet implements Serializable{
	private transient final FileNameExtensionFilter filter = new FileNameExtensionFilter("Type d'image", "jpg", ".jpeg", "jpeg", ".png",".png", ".gif", "gif", ".tiff", "tiff", ".tif", "tif");
	private transient BufferedImage image;
	private String imageFilePath;

	public void loadImage(File file) {
		imageFilePath = file.getAbsolutePath();
		try {
			image=ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		notifyObserver();
	}

	public BufferedImage getImage() {
		return image;
	}

	public FileNameExtensionFilter getFilter() {
		return filter;
	}

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

    public void copy(Image image) {
		loadImage(new File(image.imageFilePath));
    }
}