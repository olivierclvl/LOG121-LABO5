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

import Commands.Drag;
import Utils.HistoryManager;
import Commands.Zoom;
import Utils.SaveManager;
import CopyPaste.BothCopyStrategy;
import CopyPaste.CopyStrategy;
import CopyPaste.Mediator;
import CopyPaste.NoCopyStrategy;
import CopyPaste.PerspectiveMediator;
import CopyPaste.ScaleCopyStrategy;
import CopyPaste.TranslationCopyStrategy;

import javax.swing.*;

public class Controller implements MouseListener, MouseMotionListener, ActionListener, MouseWheelListener {
    private boolean isDragging = false;
    private Image image;
    private Hashtable<AlterablePanel, Perspective> perspectives = new Hashtable<>();
    private Point dragStartPoint;
    private View view;

    private Mediator mediator = new PerspectiveMediator();
    private Perspective chosenPerspective;


    /**
     * The contstructor of the controller
     */
    public Controller() {
        image = new Image();
    }

    /**
     * Undo the last changes applied to the perspective of the given panel.
     * @param panel The given panel
     */
    private void undo(AlterablePanel panel) {
        HistoryManager.getInstance().undo(perspectives.get(panel));
    }

    /**
     * Redo the last undone changes from the perspective of the given panel.
     * @param panel The given panel
     */
    private void redo(AlterablePanel panel) {
        HistoryManager.getInstance().redo(perspectives.get(panel));
    }

    /**
     * Reset the image perspective of the given panel
     * @param panel The given panel
     */
    private void reset(AlterablePanel panel) {
        HistoryManager.getInstance().reset(perspectives.get(panel));
    }

    /**
     * Attach the image to the given panel
     * @param panel The given panel
     */
    public void attachImage(Panel panel) {
    	image.attachObserver(panel);
    }

    /**
     * Set the view
     * @param view
     */
    public void setView(View view) {
    	this.view = view;
    }

     /**
     * Attach a perspective to the given panel
     * @param ap the given panel
     */
    public void attachPerspective(AlterablePanel ap) {
        Perspective p = new Perspective();
        p.setCommands(new Zoom(), new Drag());
        perspectives.put(ap, p);
        p.attachObserver(ap);
    }

    /**
     * Load an image
     */
    private void loadImage() {
        File file = this.view.getFile(image.getFilter());
        if (file == null) return;

        image.loadImage(file);
        for (AlterablePanel ap : perspectives.keySet()) {
            Point dimension = new Point(ap.getWidth(), ap.getHeight());
            perspectives.get(ap).setImageDimension(image.getScaledDimension(dimension));
            HistoryManager.getInstance().setDefaultPerspective(perspectives.get(ap));
        }
    }

    /**
     * Save the Image and Perspectives
     */
    private void save() {
        File file = view.getSaveLocation(SaveManager.EXTENTION_FILTER);
        if (file == null) return;

        ArrayList<Serializable> serializables = new ArrayList<>();
        serializables.add(image);
        for (Perspective p : perspectives.values()) {
            serializables.add(p.takeSnapshot());
        }

        if (SaveManager.saveConfig(file, serializables.toArray())) {
            view.showNotification("Saved");
        }
        else {
            view.showNotification("Error! Couldn't save");
        }
    }

    /**
     * Load the Image and Perspectives from a file
     */
    private void load() {
        File file = view.getFile(SaveManager.EXTENTION_FILTER);
        if (file == null) return;

        Object[] config = SaveManager.loadConfig(file);
        if (config.length < 1) {
            view.showNotification("Error! Couldn't read file");
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
        HistoryManager.getInstance().clearHistory();
    }

    /**
     * Paste the copied strategy to a perspective
     */
    public void paste() {
    	mediator.paste(chosenPerspective);
    }

    /**
     * Set the strategy obtain from the view and apply the strategy to the mediator
     * @param strategy The strategy from the view
     */
    public void setStrategyAndCopy(String strategy) {
    	CopyStrategy copyStrategy;
    	switch (strategy) {
		    case "ScaleCopyStrategy":
		    	copyStrategy = new ScaleCopyStrategy();
		        break;
		    case "TranslationCopyStrategy":
		    	copyStrategy = new TranslationCopyStrategy();
		        break;
		    case "BothCopyStrategy":
		    	copyStrategy = new BothCopyStrategy();
		        break;
		    default:
		    	copyStrategy = new NoCopyStrategy();
		        break;
    	}
    	copyStrategy.copy(chosenPerspective);
    	mediator.storeCopy(copyStrategy);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	if(e.getButton() == MouseEvent.BUTTON3) {
    		chosenPerspective = perspectives.get((AlterablePanel)e.getSource());
    		view.dialogWindow();
    	} else {
    		dragStartPoint = e.getPoint();
    	}
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isDragging) {
            HistoryManager.getInstance().save(perspectives.get((AlterablePanel)e.getSource()));
            isDragging = true;
        }

        int xTranslation = e.getPoint().x-dragStartPoint.x;
        int yTranslation = e.getPoint().y-dragStartPoint.y;
        perspectives.get((AlterablePanel)e.getSource()).executeDrag(xTranslation,yTranslation);
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
                undo((AlterablePanel) ((JButton)e.getSource()).getParent());
                break;
            case "Redo" :
                redo((AlterablePanel) ((JButton)e.getSource()).getParent());
                break;
            case "Reset" :
                reset((AlterablePanel) ((JButton)e.getSource()).getParent());
                break;
        }
    }

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
        Perspective perspective = perspectives.get((AlterablePanel)e.getSource());
        HistoryManager.getInstance().save(perspective);

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
