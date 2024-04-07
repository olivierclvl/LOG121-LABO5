package Application;

import Utils.Subject;

import javax.swing.*;

public class AlterablePanel extends Panel{

    private Controller controller;

    /**
     * The constructor of the AlterablePanel class
     * @param controller
     */
    public AlterablePanel(Controller controller) {
        this.controller = controller;
        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);
        this.addMouseWheelListener(controller);
        setup();
    }

    /**
     * Setup the panel.
     */
    private void setup() {
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton resetButton = new JButton("Reset");
        this.add(undoButton);
        this.add(redoButton);
        this.add(resetButton);
        undoButton.addActionListener(controller);
        redoButton.addActionListener(controller);
        resetButton.addActionListener(controller);
    }
    
    /**
     * Update the panel with the given subject
     * @param s the given subject 
     */
    @Override
    public void update(Subject s) {
        if (s instanceof Perspective) {
            imgPos = ((Perspective)s).getPosition();
            imgDimension = ((Perspective)s).getDimension();
            repaint();
        }else {
        	super.update(s);
        }
    }
}
