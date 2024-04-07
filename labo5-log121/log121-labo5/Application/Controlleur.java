package Application;

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

import Commands.Translation;
import Utils.CommandeManager;
import Commands.Zoom;
import Utils.SaveManager;

import javax.swing.*;

public class Controlleur implements MouseListener, MouseMotionListener, ActionListener, MouseWheelListener {
    private boolean isDragging = false;
    private Image image;
    private Hashtable<PanneauModifiable, Perspective> perspectives = new Hashtable<>();
    private Point dragStartPoint;
    private Vue vue;
    private Perspective chosenPerspective;

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

    public void attachImage(Panneau panneau) {
    	image.addObserver(panneau);
    }

    public void setView(Vue vue) {
    	this.vue = vue;
    }

    public void attachPerspective(PanneauModifiable ap) {
        Perspective p = new Perspective();
        p.setCommands(new Zoom(), new Translation());
        perspectives.put(ap, p);
        p.addObserver(ap);
    }

    private void loadImage() {
        File file = this.vue.getFile(image.getFilter());
        if (file == null) return;

        image.loadImage(file);
        for (PanneauModifiable ap : perspectives.keySet()) {
            Point dimension = new Point(ap.getWidth(), ap.getHeight());
            perspectives.get(ap).setImageDimension(image.getScaledDimension(dimension));
            CommandeManager.getInstance().setDefaultPerspective(perspectives.get(ap));
        }
    }

    private void save() {
        File file = vue.getSaveLocation(SaveManager.EXTENTION_FILTER);
        if (file == null) return;

        ArrayList<Serializable> serializables = new ArrayList<>();
        serializables.add(image);
        for (Perspective p : perspectives.values()) {
            serializables.add(p.takeSnapshot());
        }

        if (SaveManager.saveConfig(file, serializables.toArray())) {
            vue.showNotification("Saved");
        }
        else {
            vue.showNotification("Error! Couldn't save");
        }
    }

    private void load() {
        File file = vue.getFile(SaveManager.EXTENTION_FILTER);
        if (file == null) return;

        Object[] config = SaveManager.loadConfig(file);
        if (config.length < 1) {
            vue.showNotification("Error! Couldn't read file");
            return;
        }

        Object[] perspectives = this.perspectives.values().toArray();
        int i = 0;
        for (Object s : config) {
            if (s instanceof Image) {
                image.copy((Image)s);
            } else if (s instanceof Perspective.Memento) {
                ((Perspective)perspectives[i]).restore((Perspective.Memento)s);
                i++;
            }
        }
        CommandeManager.getInstance().clearHistory();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	dragStartPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isDragging) {
            CommandeManager.getInstance().save(perspectives.get((PanneauModifiable)e.getSource()));
            isDragging = true;
        }

        int xTranslation = e.getPoint().x-dragStartPoint.x;
        int yTranslation = e.getPoint().y-dragStartPoint.y;
        perspectives.get((PanneauModifiable)e.getSource()).executeDrag(xTranslation,yTranslation);
        dragStartPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Load Image" :
                loadImage();
                break;
            case "Save Configuration":
                save();
                break;
            case "Load Configuration":
                load();
                break;
            case "Undo" :
                undo((PanneauModifiable) ((JButton)e.getSource()).getParent());
                break;
            case "Redo" :
                redo((PanneauModifiable) ((JButton)e.getSource()).getParent());
                break;
            case "Reset" :
                reset((PanneauModifiable) ((JButton)e.getSource()).getParent());
                break;
        }
    }

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
        Perspective perspective = perspectives.get((PanneauModifiable)e.getSource());
        CommandeManager.getInstance().save(perspective);

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
