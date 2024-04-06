package org.example.demo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Panneau extends Pane implements Observateur {
    protected BufferedImage image;
    protected Point imgPos = new Point(0, 0);
    protected Point imgDimension = new Point(0, 0);
    private Canvas canvas;

    public Panneau() {
        setPrefSize(200, 200);
        canvas = new Canvas();
        getChildren().add(canvas);
    }

    protected void drawImage(BufferedImage awtImage) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image fxImage = convertToFXImage(awtImage);
        gc.drawImage(fxImage, imgPos.x, imgPos.y, imgDimension.x, imgDimension.y);
    }

    private Image convertToFXImage(BufferedImage awtImage) {
        int width = awtImage.getWidth();
        int height = awtImage.getHeight();
        javafx.scene.image.WritableImage fxImage = new javafx.scene.image.WritableImage(width, height);
        try {
            javax.imageio.ImageIO.write(awtImage, "png", new java.io.ByteArrayOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fxImage;
    }

    @Override
    public void update(Sujet sujet) {
        if (sujet instanceof org.example.demo.Image) {
            this.image = ((org.example.demo.Image) sujet).getImage();
            this.imgDimension = ((org.example.demo.Image) sujet).getScaledDimension(new Point((int) getWidth(), (int) getHeight()));
            drawImage(image);
        }
    }
}
