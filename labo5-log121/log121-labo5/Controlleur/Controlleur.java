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
import Observer.Panneau;
import Observer.PanneauModifiable;
import Sauvegarde.DeserialisationSauvegarde;
import Sauvegarde.SerialisationSauvegarde;
import Vue.Vue;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controlleur implements MouseListener, MouseMotionListener, ActionListener, MouseWheelListener {
    private Vue vue;
    private Image image;
    private Point TranslationDepart;
    private boolean isTranslation = false;
    private static final String EXTENSION = ".save";
    public static final FileNameExtensionFilter EXTENSION_FILTER = new FileNameExtensionFilter(EXTENSION, EXTENSION.substring(1));
    private Hashtable<PanneauModifiable, Perspective> mesPerspectives = new Hashtable<>();

    public Controlleur() {
        image = new Image();
    }

    private void undo(PanneauModifiable panneauModifiable) {
        CommandeManager.getInstance().undo(mesPerspectives.get(panneauModifiable));
    }

    private void redo(PanneauModifiable panneauModifiable) {
        CommandeManager.getInstance().redo(mesPerspectives.get(panneauModifiable));
    }

    private void reset(PanneauModifiable panneauModifiable) {
        CommandeManager.getInstance().reset(mesPerspectives.get(panneauModifiable));
    }

    public void attachImage(Panneau panneau) {
    	image.addObserver(panneau);
    }

    public void setView(Vue vue) {
    	this.vue = vue;
    }

    public void attachPerspective(PanneauModifiable panneauModifiable) {
        Perspective perspective = new Perspective();
        perspective.setCommands(new Zoom(), new Translation());
        mesPerspectives.put(panneauModifiable, perspective);
        perspective.addObserver(panneauModifiable);
    }

    private void loadImage() {
        File file = this.vue.getFile(image.getFilter());

        if (file == null) {
            return;
        }

        image.loadImage(file);

        for (PanneauModifiable panneauModifiable : mesPerspectives.keySet()) {
            Point dimension = new Point(panneauModifiable.getWidth(), panneauModifiable.getHeight());
            mesPerspectives.get(panneauModifiable).setImageDimension(image.getScaledDimension(dimension));
            CommandeManager.getInstance().setDefaultPerspective(mesPerspectives.get(panneauModifiable));
        }
    }

    private void save() {
        File file = vue.getSaveLocation(EXTENSION_FILTER);

        if (file == null) {
            return;
        }

        ArrayList<Serializable> serializables = new ArrayList<>();
        serializables.add(image);
        for (Perspective p : mesPerspectives.values()) {
            serializables.add(p.takeSnapshot());
        }

        SerialisationSauvegarde serialisationSauvegarde = new SerialisationSauvegarde();
        if (serialisationSauvegarde.execute(file, serializables.toArray())) {
            vue.showNotification("Sauvegarde effectué avec succès");
        } else {
            vue.showNotification("Erreur! Impossible de sauvegarder");
        }
    }

    private void load() {
        File file = vue.getFile(EXTENSION_FILTER);
        if (file == null) return;

        Object[] config = new Object[1];

        DeserialisationSauvegarde deserialisationSauvegarde = new DeserialisationSauvegarde();
        if (!deserialisationSauvegarde.execute(file, config)) {
            vue.showNotification("Erreur! Impossible de lire le fichier");
            return;
        }

        Object[] perspectives = this.mesPerspectives.values().toArray();
        int i = 0;
        for (Object s : (Object[]) config[0]) {
            if (s instanceof Image) {
                image.copy((Image) s);
            } else if (s instanceof Perspective.Memento) {
                ((Perspective) perspectives[i]).restore((Perspective.Memento) s);
                i++;
            }
        }
        CommandeManager.getInstance().clearHistory();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	TranslationDepart = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isTranslation) {
            CommandeManager.getInstance().save(mesPerspectives.get((PanneauModifiable)e.getSource()));
            isTranslation = true;
        }

        int xTranslation = e.getPoint().x- TranslationDepart.x;
        int yTranslation = e.getPoint().y- TranslationDepart.y;
        mesPerspectives.get((PanneauModifiable)e.getSource()).executeDrag(xTranslation,yTranslation);
        TranslationDepart = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isTranslation = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Charger l'image" :
                loadImage();
                break;
            case "Enregistrer la configuration":
                save();
                break;
            case "Charger la configuration":
                load();
                break;
            case "Undo" :
                undo((PanneauModifiable) ((JButton)e.getSource()).getParent());
                break;
            case "Redo" :
                redo((PanneauModifiable) ((JButton)e.getSource()).getParent());
                break;
            case "Réinitialiser" :
                reset((PanneauModifiable) ((JButton)e.getSource()).getParent());
                break;
        }
    }

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
        Perspective perspective = mesPerspectives.get((PanneauModifiable)e.getSource());
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
