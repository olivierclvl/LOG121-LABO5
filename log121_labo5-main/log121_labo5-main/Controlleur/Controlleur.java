package Controlleur;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import Modele.Image;
import Modele.Perspective;
import Modele.PerspectiveMomento;
import SauvegarderCopierColler.*;
import Vue.PerspectiveView;
import Vue.ThumbnailView;
import Vue.Vue;

import javax.swing.*;

/**
 *
 * Ce module permet de représenter le controlleur. elle permet de gérer les actions de la souris afin d'appliquer les
 * commandes et changement appropriés
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class Controlleur implements MouseListener, MouseMotionListener, ActionListener, MouseWheelListener {
    private boolean isDragging = false;
    private Image image;
    private Hashtable<PerspectiveView, Perspective> perspectives = new Hashtable<>();
    private Point dragStartPoint;
    private Vue vue;

    private Mediateur mediateur = new PerspectiveMediateur();
    private Perspective chosenPerspective;


    /**
     * Le constructeur de l'image
     */
    public Controlleur() {
        image = new Image();
    }

    /**
     * Annule les derniers changements appliqués à la perspective du panneau donné.
     * @param panel
     *        Le panneau donné
     */
    private void undo(PerspectiveView panel) {
        CommandManager.getInstance().undo(perspectives.get(panel));
    }

    /**
     * Reapplique les derniers changements appliqués à la perspective du panneau donné.
     * @param panel
     *        Le panneau donné
     */
    private void redo(PerspectiveView panel) {
        CommandManager.getInstance().redo(perspectives.get(panel));
    }

    /**
     * Remet l'image de la perspective à son état initial
     * @param panel
     *        Le panneau donné
     */
    private void reset(PerspectiveView panel) {
        CommandManager.getInstance().reset(perspectives.get(panel));
    }

    /**
     * Ajoute une image au panneau
     * @param thumbnailView
     *        Le panneau donné
     */
    public void attachImage(ThumbnailView thumbnailView) {
    	image.attachObserver(thumbnailView);
    }

    /**
     * Définie la vue
     * @param vue
     *        La vue passée en paramètre
     */
    public void setView(Vue vue) {
    	this.vue = vue;
    }

     /**
     * Ajoute une perspective à un panneau
     * @param ap
     *        Le panneau donné
     */
    public void attachPerspective(PerspectiveView ap) {
        Perspective p = new Perspective();
        p.setCommands(new Zoom(), new Translation());
        perspectives.put(ap, p);
        p.attachObserver(ap);
    }

    /**
     * Permet de charger l'image
     */
    private void loadImage() {
        File file = this.vue.getFile(image.getFilter());
        if (file == null) return;

        image.loadImage(file);
        for (PerspectiveView ap : perspectives.keySet()) {
            Point dimension = new Point(ap.getWidth(), ap.getHeight());
            perspectives.get(ap).setImageDimension(image.getScaledDimension(dimension));
            CommandManager.getInstance().setDefaultPerspective(perspectives.get(ap));
        }
    }

    /**
     * Permet de sauvegarder l'image et les Perspectives
     */
    private void save() {
        // Assuming `vue.getSaveLocation(...)` opens a file chooser dialog and returns the selected file.
        File file = vue.getSaveLocation(SauvegardeSerialisation.EXTENTION_FILTER);
        if (file == null) return; // If no file was selected, exit the method.

        ArrayList<Serializable> serializables = new ArrayList<>();
        serializables.add(image); // Assuming `image` is a Serializable object representing an image.
        for (Perspective p : perspectives.values()) {
            serializables.add(p.takeSnapshot()); // Assuming each Perspective's snapshot is Serializable.
        }

        // Create an instance of the serialization strategy class.
        SauvegardeSerialisation sauvegarde = new SauvegardeSerialisation();

        // Execute the save operation. Since `execute` returns null upon success, we check if the result is not null to determine failure.
        if (sauvegarde.execute(file, serializables.toArray()) == null) {
            vue.showNotification("Sauvegarde faite!");
        } else {
            vue.showNotification("Erreur! impossible de sauvegarder.");
        }
    }


    /**
     * Permet de charger une image et les perspectives d'un fichier
     */
    private void load() {
        // Assuming `vue.getFile(...)` opens a file chooser dialog and returns the selected file.
        File file = vue.getSaveLocation(SauvegardeSerialisation.EXTENTION_FILTER);

        if (file == null) return; // If no file was selected, exit the method.

        // Create an instance of the deserialization strategy class.
        SauvegardeDeserialisation sauvegardeDeserialisation = new SauvegardeDeserialisation();

        // Execute the load operation. Pass `null` as the second parameter since it's not used for loading.
        Object[] config = sauvegardeDeserialisation.execute(file, null);

        if (config == null || config.length < 1) {
            vue.showNotification("Erreur! impossible de lire le fichier.");
            return;
        }

        // Assuming the first object in the array is always an Image and the rest are PerspectiveMomento objects.
        // Reset current states or clear existing data if necessary.
        Object[] perspectivesArray = perspectives.values().toArray();
        int i = 0;
        for (Object s : config) {
            if (s instanceof Image) {
                image.copy((Image) s); // Assuming `image.copy(...)` is a method to copy image properties.
            } else if (s instanceof PerspectiveMomento) {
                if (i < perspectivesArray.length) {
                    ((Perspective) perspectivesArray[i]).restore((PerspectiveMomento) s); // Restore the state of each Perspective.
                    i++;
                }
            }
        }

        // Clear command history after loading new data.
        CommandManager.getInstance().clearHistory();
        vue.showNotification("Chargement réussi!");
    }


    /**
     * Coller la stratégie copiée à la perspective
     */
    public void paste() {
    	mediateur.paste(chosenPerspective);
    }


    /**
     * Défini la stratégie obtenue de la vue et applique la stratégie au médiateur
     * @param strategy
     *        La stratégie provenant de la vue
     */
    public void setStrategyAndCopy(String strategy) {
    	CopieStrategie copieStrategie;
    	switch (strategy) {
		    case "ZoomCopyStrategie":
		    	copieStrategie = new ZoomCopieStrategie();
		        break;
		    case "TranslationCopieStrategie":
		    	copieStrategie = new TranslationCopieStrategie();
		        break;
		    case "ZoomTranslationCopieStrategie":
		    	copieStrategie = new ZoomTranslationCopieStrategie();
		        break;
		    default:
		    	copieStrategie = new AucuneCopieStrategie();
		        break;
    	}
    	copieStrategie.copy(chosenPerspective);
    	mediateur.storeCopy(copieStrategie);
    }

    /**
     * Permet de gérer l'évènement lorsqu'un bouton de la souris est enfoncé.
     */
    @Override
    public void mousePressed(MouseEvent e) {
    	if(e.getButton() == MouseEvent.BUTTON3) {
    		chosenPerspective = perspectives.get((PerspectiveView)e.getSource());
    		vue.dialogWindow();
    	} else {
    		dragStartPoint = e.getPoint();
    	}
    }

    /**
     * Permet de gérer l'évènement de déplacement de la souris
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isDragging) {
            CommandManager.getInstance().save(perspectives.get((PerspectiveView)e.getSource()));
            isDragging = true;
        }

        int xTranslation = e.getPoint().x-dragStartPoint.x;
        int yTranslation = e.getPoint().y-dragStartPoint.y;
        perspectives.get((PerspectiveView)e.getSource()).executeTranslation(xTranslation,yTranslation);
        dragStartPoint = e.getPoint();
    }

    /**
     * Permet de gérer l'évènement de relachement de la souris
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
    }

    /**
     * Applique l'action nécessaire en fonction de l'évènement détecté par la souris
     * @param e
     *        l'évènement liée à l'action faite
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Sélectionner Image" :
                loadImage();
                break;
            case "Sauvegarder Configuration":
                save();
                break;
            case "Charger Configuration":
                load();
                break;
            case "Undo" :
                undo((PerspectiveView) ((JButton)e.getSource()).getParent());
                break;
            case "Redo" :
                redo((PerspectiveView) ((JButton)e.getSource()).getParent());
                break;
            case "Réinitialiser" :
                reset((PerspectiveView) ((JButton)e.getSource()).getParent());
                break;
        }
    }

    /**
     * Permet de gérer l'évènement lié à la rotation de la molette de la souris
     */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
        Perspective perspective = perspectives.get((PerspectiveView)e.getSource());
        CommandManager.getInstance().save(perspective);

        double factor = e.getPreciseWheelRotation();
        double modifiers = e.getModifiers();
        perspective.executeZoom(factor, modifiers);
	}

    /**
     * Permet de gérer l'évènement lié au clic de la souris
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * Permet de gérer l'évènement lorsque le curseur de la souris entre dans la zone d'un composant graphique
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * Permet de gérer l'évènement lorsque le curseur de la souris quitte la zone d'un composant graphique.
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Permet de gérer l'évènement lorsqu'un événement de déplacement de souris est détecté
     */
    @Override
    public void mouseMoved(MouseEvent e) {}
}
