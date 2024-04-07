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
import SauvegarderCopierColler.Sauvegarder;
import SauvegarderCopierColler.ZoomTranslationCopieStrategie;
import SauvegarderCopierColler.CopieStrategie;
import SauvegarderCopierColler.Mediateur;
import SauvegarderCopierColler.AucuneCopieStrategie;
import SauvegarderCopierColler.PerspectiveMediateur;
import SauvegarderCopierColler.ZoomCopieStrategie;
import SauvegarderCopierColler.TranslationCopieStrategie;
import Vue.PerspectiveView;
import Vue.ThumbnailView;
import Vue.Vue;

import javax.swing.*;

public class Controlleur implements MouseListener, MouseMotionListener, ActionListener, MouseWheelListener {
    private boolean isDragging = false;
    private Image image;
    private Hashtable<PerspectiveView, Perspective> perspectives = new Hashtable<>();
    private Point dragStartPoint;
    private Vue vue;

    private Mediateur mediateur = new PerspectiveMediateur();
    private Perspective chosenPerspective;


    /**
     * The contstructor of the controller
     */
    public Controlleur() {
        image = new Image();
    }

    /**
     * Undo the last changes applied to the perspective of the given panel.
     * @param panel The given panel
     */
    private void undo(PerspectiveView panel) {
        CommandManager.getInstance().undo(perspectives.get(panel));
    }

    /**
     * Redo the last undone changes from the perspective of the given panel.
     * @param panel The given panel
     */
    private void redo(PerspectiveView panel) {
        CommandManager.getInstance().redo(perspectives.get(panel));
    }

    /**
     * Reset the image perspective of the given panel
     * @param panel The given panel
     */
    private void reset(PerspectiveView panel) {
        CommandManager.getInstance().reset(perspectives.get(panel));
    }

    /**
     * Attach the image to the given panel
     * @param thumbnailView The given panel
     */
    public void attachImage(ThumbnailView thumbnailView) {
    	image.attachObserver(thumbnailView);
    }

    /**
     * Set the view
     * @param vue
     */
    public void setView(Vue vue) {
    	this.vue = vue;
    }

     /**
     * Attach a perspective to the given panel
     * @param ap the given panel
     */
    public void attachPerspective(PerspectiveView ap) {
        Perspective p = new Perspective();
        p.setCommands(new Zoom(), new Translation());
        perspectives.put(ap, p);
        p.attachObserver(ap);
    }

    /**
     * Load an image
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
     * Save the Image and Perspectives
     */
    private void save() {
        File file = vue.getSaveLocation(Sauvegarder.EXTENTION_FILTER);
        if (file == null) return;

        ArrayList<Serializable> serializables = new ArrayList<>();
        serializables.add(image);
        for (Perspective p : perspectives.values()) {
            serializables.add(p.takeSnapshot());
        }

        if (Sauvegarder.saveConfig(file, serializables.toArray())) {
            vue.showNotification("Saved");
        }
        else {
            vue.showNotification("Error! Couldn't save");
        }
    }

    /**
     * Load the Image and Perspectives from a file
     */
    private void load() {
        File file = vue.getFile(Sauvegarder.EXTENTION_FILTER);
        if (file == null) return;

        Object[] config = Sauvegarder.loadConfig(file);
        if (config.length < 1) {
            vue.showNotification("Error! Couldn't read file");
            return;
        }

        Object[] perspectives = this.perspectives.values().toArray();
        int i = 0;
        for (Object s : config) {
            if (s instanceof Image) {
                image.copy((Image)s);
            } else if (s instanceof PerspectiveMomento) {
                ((Perspective)perspectives[i]).restore((PerspectiveMomento)s);
                i++;
            }
        }
        CommandManager.getInstance().clearHistory();
    }

    /**
     * Paste the copied strategy to a perspective
     */
    public void paste() {
    	mediateur.paste(chosenPerspective);
    }

    /**
     * Set the strategy obtain from the view and apply the strategy to the mediator
     * @param strategy The strategy from the view
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

    @Override
    public void mousePressed(MouseEvent e) {
    	if(e.getButton() == MouseEvent.BUTTON3) {
    		chosenPerspective = perspectives.get((PerspectiveView)e.getSource());
    		vue.dialogWindow();
    	} else {
    		dragStartPoint = e.getPoint();
    	}
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isDragging) {
            CommandManager.getInstance().save(perspectives.get((PerspectiveView)e.getSource()));
            isDragging = true;
        }

        int xTranslation = e.getPoint().x-dragStartPoint.x;
        int yTranslation = e.getPoint().y-dragStartPoint.y;
        perspectives.get((PerspectiveView)e.getSource()).executeDrag(xTranslation,yTranslation);
        dragStartPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
    }

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

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
        Perspective perspective = perspectives.get((PerspectiveView)e.getSource());
        CommandManager.getInstance().save(perspective);

        double factor = e.getPreciseWheelRotation();
        double modifiers = e.getModifiers();
        perspective.executeZoom(factor, modifiers);
	}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
