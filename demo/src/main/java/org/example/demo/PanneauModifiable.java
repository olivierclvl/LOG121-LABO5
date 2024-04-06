package org.example.demo;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.awt.*;

public class PanneauModifiable extends Panneau implements Observateur {
    private Controlleur controller;

    public PanneauModifiable(Controlleur controller) {
        super();
        this.controller = controller;
        setupButtons();
        setupMouseHandlers();
    }

    private void setupButtons() {
        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER);

        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");
        Button resetButton = new Button("Reset");

        undoButton.setOnAction(event -> controller.handleAction(event));
        redoButton.setOnAction(event -> controller.handleAction(event));
        resetButton.setOnAction(event -> controller.handleAction(event));
        buttonBar.getChildren().addAll(undoButton, redoButton, resetButton);

        this.getChildren().add(buttonBar);
    }

    private void setupMouseHandlers() {
        this.setOnMousePressed(controller::handleMousePressed);
        this.setOnMouseDragged(controller::handleMouseDragged);
        this.setOnMouseReleased(controller::handleMouseReleased);
        this.setOnScroll(controller::handleScroll);
    }

    @Override
    public void update(Sujet sujet) {

        if (sujet instanceof Perspective) {
            Perspective perspective = (Perspective) sujet;

            this.imgPos = new Point(perspective.getPosition().x, perspective.getPosition().y);
            this.imgDimension = new Point(perspective.getDimension().x, perspective.getDimension().y);

            drawImage(this.image);
        }else {
            super.update(sujet);
        }
    }

}
