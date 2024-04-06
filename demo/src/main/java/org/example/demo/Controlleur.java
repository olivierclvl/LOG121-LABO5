package org.example.demo;

import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Controlleur {
    private boolean isDragging = false;
    private Image image;
    private HashMap<PanneauModifiable, Perspective> perspectives = new HashMap<>();
    private Point2D dragStartPoint;
    private Vue view;
    public Controlleur() {
        image = new Image();
    }

    private void undo(PanneauModifiable panel) {
        CommandeManager.getInstance().undo(perspectives.get(panel));
    }
    private void redo(PanneauModifiable panel) {
        CommandeManager.getInstance().redo(perspectives.get(panel));
    }

    private void reset(PanneauModifiable panel) {
        CommandeManager.getInstance().reset(perspectives.get(panel));
    }
    public void attachImage(Panneau panel) {
        image.addObserver(panel);
    }

    public void setView(Vue view) {
        this.view = view;
    }
    public void attachPerspective(PanneauModifiable ap) {
        Perspective p = new Perspective();
        p.setCommands(new Zoom(), new Translation());
        perspectives.put(ap, p);
        p.addObserver(ap);
    }
    private void loadImage() {
        File file = this.view.getFile(String.valueOf(image.getFilter()));
        if (file == null) return;

        image.loadImage(file);
        for (PanneauModifiable ap : perspectives.keySet()) {
            Point dimension = new Point((int)ap.getWidth(), (int)ap.getHeight());
            perspectives.get(ap).setImageDimension(image.getScaledDimension(dimension));
            CommandeManager.getInstance().setDefaultPerspective(perspectives.get(ap));
        }
    }
    private void save() {
        view.showNotification("Pas encore défini");
    }

    private void load() {
        view.showNotification("Pas encore défini");
    }

    public void handleMousePressed(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            //view.dialogWindow();
        } else {
            dragStartPoint = new Point2D(event.getSceneX(), event.getSceneY());
        }
    }

    public void handleMouseDragged(MouseEvent event) {
        if (!isDragging) {
            isDragging = true;
        }
        double xTranslation = event.getSceneX() - dragStartPoint.getX();
        double yTranslation = event.getSceneY() - dragStartPoint.getY();
        dragStartPoint = new Point2D(event.getSceneX(), event.getSceneY());
    }

    public void handleMouseReleased(MouseEvent event) {
        if (isDragging) {
            isDragging = false;
        }
    }

    public void handleScroll(ScrollEvent event) {
        double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
    }

    public void handleAction(ActionEvent event) {
        String command = ((Button)event.getSource()).getText();
        switch (command) {
            case "Load Image":
                loadImage();
                break;
            case "Save Configuration":
                save();
                break;
            case "Load Configuration":
                load();
                break;
            case "Undo":
                undo((PanneauModifiable) ((Button)event.getSource()).getParent());
                break;
            case "Redo":
                redo((PanneauModifiable) ((Button)event.getSource()).getParent());
                break;
            case "Reset":
                reset((PanneauModifiable) ((Button)event.getSource()).getParent());
                break;
        }
    }
}
