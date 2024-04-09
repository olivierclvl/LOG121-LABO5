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

/**
 *
 * Ce module permet de représenter la classe Vue. elle permet de gérer l'affichage des image en fotion des informations
 * reçu par le modèle.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class Vue {
    private Controlleur controlleur;
    private JFrame frame;

    /**
     * Ce constructeur par initialisation crée une interface avec les différentes vues(panneaux) nécessaires
     * @param controlleur
     *         Le controlleur de la vue
     *@return
     *         Crée l'interface utilisatur
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
     * Crée un sélectionneur de fichiers n'acceptant que les extensions spécifiées.
     * @param filter
     *       Les extensions qui peuvent être sélectionnées
     *
     * @return
     *       Le sélectionneur de fichiers créé
     */
    private JFileChooser createFileChooser(FileNameExtensionFilter filter) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Sélectionnez un fichier");
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setAcceptAllFileFilterUsed(false);

        //on cree d'autres filtres pour les fichers
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
     * Demander à l'utilisateur de choisir un fichier.
     * @param filter
     *        Les extensions acceptées
     * @return
     *       Le fichier sélectionné par l'utilisateur
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
     * Cette méthode permet de récupérer le fichier sélectionné par l'utilisateur
     * @param filter
     *        Les extensions acceptées
     * @return
     *        Le fichier sélectionné par l'utilisateur
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
     * Affiche une notification à l'utilisateur.
     * @param message
     *       Le contenu du message de la notification
     */
    public void showNotification(String message) {
        JOptionPane.showMessageDialog(frame, message, "", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Cette méthode crée la fenêtre qui contiendra les différentes options de copier-coller
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
