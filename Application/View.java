package Application;

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

public class View {
    private Controller controller;
    private JFrame frame;

    /**
     * The constructor of the view class.
     * @param controller
     */
    public View(Controller controller) {
        this.controller = controller;
        controller.setView(this);
        setup();
    }

    /**
     * Create and setup the application's interface.
     */
    private void setup() {
        frame = new JFrame("Lab2 Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JButton loadImageButton = new JButton("Load Image");
        JButton saveConfigButton = new JButton("Save Configuration");
        JButton loadConfigButton = new JButton("Load Configuration");
        topPanel.add(loadImageButton);
        topPanel.add(saveConfigButton);
        topPanel.add(loadConfigButton);

        JPanel mainPanel = new JPanel(new GridLayout(1, 3));
        
        Panel panel1 = new Panel();
        AlterablePanel panel2 = new AlterablePanel(controller);
        AlterablePanel panel3 = new AlterablePanel(controller);
        
        controller.attachImage(panel1);
        controller.attachImage(panel2);
        controller.attachImage(panel3);
        
        controller.attachPerspective(panel2);
        controller.attachPerspective(panel3);
        
        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);

        loadImageButton.addActionListener(controller);
        saveConfigButton.addActionListener(controller);
        loadConfigButton.addActionListener(controller);
    }

    /**
     * Create a JFileChooser accepting only the specified extensions.
     * @param filter The extensions that can be selected
     * @return The JFileChooser created
     */
    private JFileChooser createFileChooser(FileNameExtensionFilter filter) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(filter);

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
    	JDialog dialog = new JDialog(frame, "Choose what to copy", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JButton StrategyScaleImageButton = new JButton("Scale copy");
        JButton StrategyTranslationImageButton = new JButton("Translation copy");
        JButton StrategyBothImageButton = new JButton("Scale and translation copy");
        JButton StrategyNoneImageButton = new JButton("No copy");
        JButton PasteImageButton = new JButton("Paste");

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
                controller.setStrategyAndCopy("ScaleCopyStrategy");
            }
        });
        
        StrategyTranslationImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
            	controller.setStrategyAndCopy("TranslationCopyStrategy");
            }
        });

        StrategyBothImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
            	controller.setStrategyAndCopy("BothCopyStrategy");
            }
        });
        
        StrategyNoneImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
            	controller.setStrategyAndCopy("NoCopyStrategy");
            }
        });
        
        PasteImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
            	controller.paste();
            }
        });
        dialog.setVisible(true);
    }
}
