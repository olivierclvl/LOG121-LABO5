package org.example.demo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Vue {
    private Controlleur controlleur;
    private Stage primaryStage;

    public Vue(Controlleur controller, Stage primaryStage) {
        this.controlleur = controller;
        this.primaryStage = primaryStage;
        controller.setView(this);
        setup();
    }

    private void setup() {
        primaryStage.setTitle("Labo5 Application");

        BorderPane root = new BorderPane();
        HBox topPanel = new HBox(10);
        topPanel.setPadding(new Insets(15, 12, 15, 12));
        Button loadImageButton = new Button("Load Image");
        Button saveConfigButton = new Button("Save Configuration");
        Button loadConfigButton = new Button("Load Configuration");
        topPanel.getChildren().addAll(loadImageButton, saveConfigButton, loadConfigButton);
        topPanel.setAlignment(Pos.CENTER);

        GridPane mainPanel = new GridPane();
        mainPanel.setPadding(new Insets(10, 10, 10, 10));
        mainPanel.setHgap(10);
        mainPanel.setVgap(10);

        Panneau panneau = new Panneau();
        PanneauModifiable panneauModifiable1 = new PanneauModifiable(controlleur);
        PanneauModifiable panneauModifiable2 = new PanneauModifiable(controlleur);

        controlleur.attachImage(panneau);
        controlleur.attachImage(panneauModifiable1);
        controlleur.attachImage(panneauModifiable2);

        controlleur.attachPerspective(panneauModifiable1);
        controlleur.attachPerspective(panneauModifiable2);

        mainPanel.add(panneau,0,0);
        mainPanel.add(panneauModifiable1,1,0);
        mainPanel.add(panneauModifiable2,2,0);

        root.setTop(topPanel);
        root.setCenter(mainPanel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private FileChooser createFileChooser(String... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", extensions));
        return fileChooser;
    }

    public File getFile(String... extensions) {
        FileChooser fileChooser = createFileChooser(extensions);
        return fileChooser.showOpenDialog(primaryStage);
    }

    public File getSaveLocation(String... extensions) {
        FileChooser fileChooser = createFileChooser(extensions);
        return fileChooser.showSaveDialog(primaryStage);
    }

    public void showNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
