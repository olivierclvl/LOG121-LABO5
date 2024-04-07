package Vue;

import Controlleur.Controlleur;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Vue {
    private Controlleur controlleur;
    private JFrame frame;

    /**
     * The constructor of the view class.
     * @param controlleur
     */
    /**
     * Create and set up the application's interface.
     */
    public Vue(Controlleur controlleur) {
        this.controlleur = controlleur;
        controlleur.setView(this);

        frame = new JFrame("Laboratoire 5: Application images");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JButton loadImageButton = new JButton("Sélectionner Image");
        JButton saveConfigButton = new JButton("Sauvegarder Configuration");
        JButton loadConfigButton = new JButton("Charger Configuration");
        topPanel.add(loadImageButton);
        topPanel.add(saveConfigButton);
        topPanel.add(loadConfigButton);

        JPanel panneauPrincipal = new JPanel(new GridLayout(1, 3));
        
        ThumbnailView thumbnailView1 = new ThumbnailView();
        PerspectiveView panneau2 = new PerspectiveView(controlleur);
        PerspectiveView panneau3 = new PerspectiveView(controlleur);
        
        controlleur.attachImage(thumbnailView1);
        controlleur.attachImage(panneau2);
        controlleur.attachImage(panneau3);
        
        controlleur.attachPerspective(panneau2);
        controlleur.attachPerspective(panneau3);
        
        panneauPrincipal.add(thumbnailView1);
        panneauPrincipal.add(panneau2);
        panneauPrincipal.add(panneau3);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(panneauPrincipal, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);

        loadImageButton.addActionListener(controlleur);
        saveConfigButton.addActionListener(controlleur);
        loadConfigButton.addActionListener(controlleur);
    }

    /**
     * Create a JFileChooser accepting only the specified extensions.
     * @param filter The extensions that can be selected
     * @return The JFileChooser created
     */
    private JFileChooser createFileChooser(FileNameExtensionFilter filter) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Sélectionnez un fichier");
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setAcceptAllFileFilterUsed(false);


        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Image (.png)", "png");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("Image (.jif)", "jif");
        FileNameExtensionFilter filter4 = new FileNameExtensionFilter("Image (.tiff)", "tiff");

        // On ajoute ces filtres aux choisisseurs de fichier
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.addChoosableFileFilter(filter2);
        fileChooser.addChoosableFileFilter(filter3);
        fileChooser.addChoosableFileFilter(filter4);

        return fileChooser;
    }

    /**
     * Prompt the user to choose a file.
     * @param filter The accepted extensions
     * @return The file selected by the user
     */
    public File getFile(FileNameExtensionFilter filter) {
        File selectedFile = null;
        JFileChooser fileChooser = createFileChooser(filter);
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }
        return selectedFile;
    }

    /**
     * Prompt the user to choose a file to save to.
     * @param filter The accepted extensions
     * @return The file selected by the user
     */
    public File getSaveLocation(FileNameExtensionFilter filter) {
        File selectedFile = null;
        JFileChooser fileChooser = createFileChooser(filter);
        int result = fileChooser.showSaveDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }
        return selectedFile;
    }

    /**
     * Show a notification to the user.
     * @param message The message of the notification
     */
    public void showNotification(String message) {
        JOptionPane.showMessageDialog(frame, message, "", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Prompt the user to choose a type of strategy for the copy or paste.
     */
    public void dialogWindow() {
    	JDialog dialog = new JDialog(frame, "Choisir le type de copie", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JButton StrategyScaleImageButton = new JButton("Copie Zoom");
        JButton StrategyTranslationImageButton = new JButton("Copie Translation");
        JButton StrategyBothImageButton = new JButton("Copie Zoom et translation");
        JButton StrategyNoneImageButton = new JButton("Aucune copie");
        JButton PasteImageButton = new JButton("Coller");

        topPanel.add(StrategyScaleImageButton);
        topPanel.add(StrategyTranslationImageButton);
        topPanel.add(StrategyBothImageButton);
        topPanel.add(StrategyNoneImageButton);
        bottomPanel.add(PasteImageButton);

        dialog.add(topPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setSize(300, 150);
        dialog.pack();

        StrategyScaleImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
                controlleur.setStrategyAndCopy("ZoomCopyStrategie");
            }
        });
        
        StrategyTranslationImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
            	controlleur.setStrategyAndCopy("TranslationCopieStrategie");
            }
        });

        StrategyBothImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
            	controlleur.setStrategyAndCopy("ZoomTranslationCopieStrategie");
            }
        });
        
        StrategyNoneImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
            	controlleur.setStrategyAndCopy("AucuneCopieStrategie");
            }
        });
        
        PasteImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
            	controlleur.paste();
            }
        });
        dialog.setVisible(true);
    }
}
