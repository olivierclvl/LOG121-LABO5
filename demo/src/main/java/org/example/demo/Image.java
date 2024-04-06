package org.example.demo;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image extends Sujet{
    private String filePath;
    private BufferedImage image;

    public Image(String filePath) {
        this.filePath = filePath;
        this.loadImage();
    }

    public BufferedImage getImage() {
        return this.image;
    }

    private void loadImage() {
        try {
            this.image = ImageIO.read(new File(this.filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Point getDimensionImage() {
        int width = this.image.getWidth();
        int height = this.image.getHeight();
        return new Point(width, height);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        this.loadImage();
    }
}
