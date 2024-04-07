package Vue;

import Observer.Panneau;
import Observer.PanneauModifiable;
import Controlleur.Controlleur;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

public class Vue {
    private Controlleur controlleur;
    private JFrame frame;

    public Vue(Controlleur controlleur) {
        this.controlleur = controlleur;
        controlleur.setView(this);
        setup();
    }

    private void setup() {
        frame = new JFrame("Labo 5");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JButton loadImageButton = new JButton("Charger l'image");
        JButton saveConfigButton = new JButton("Enregistrer la configuration");
        JButton loadConfigButton = new JButton("Charger la configuration");
        topPanel.add(loadImageButton);
        topPanel.add(saveConfigButton);
        topPanel.add(loadConfigButton);

        JPanel mainPanel = new JPanel(new GridLayout(1, 3));
        
        Panneau panneau1 = new Panneau();
        PanneauModifiable panel2 = new PanneauModifiable(controlleur);
        PanneauModifiable panel3 = new PanneauModifiable(controlleur);
        
        controlleur.attachImage(panneau1);
        controlleur.attachImage(panel2);
        controlleur.attachImage(panel3);
        
        controlleur.attachPerspective(panel2);
        controlleur.attachPerspective(panel3);
        
        mainPanel.add(panneau1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);

        loadImageButton.addActionListener(controlleur);
        saveConfigButton.addActionListener(controlleur);
        loadConfigButton.addActionListener(controlleur);
    }

    private JFileChooser createFileChooser(FileNameExtensionFilter filter) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(filter);

        return fileChooser;
    }

    public File getFile(FileNameExtensionFilter filter) {
        File selectedFile = null;
        JFileChooser fileChooser = createFileChooser(filter);
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }
        return selectedFile;
    }

    public File getSaveLocation(FileNameExtensionFilter filter) {
        File selectedFile = null;
        JFileChooser fileChooser = createFileChooser(filter);
        int result = fileChooser.showSaveDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }
        return selectedFile;
    }

    public void showNotification(String message) {
        JOptionPane.showMessageDialog(frame, message, "Notification Labo 5", JOptionPane.INFORMATION_MESSAGE);
    }
}
