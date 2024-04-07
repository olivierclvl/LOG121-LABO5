package SauvegarderCopierColler;

import java.io.*;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Sauvegarder {
    private static final String EXTENTION = ".save";
    public static final FileNameExtensionFilter EXTENTION_FILTER = new FileNameExtensionFilter(EXTENTION,EXTENTION.substring(1));

    /**
     * Write the configuration to the specified file.
     * @param file The file to save the config to
     * @param objs The objects to save
     * @return True if the save was successful, False otherwise
     */
    public static boolean saveConfig(File file, Object[] objs) {
        boolean saveSuccessful = true;
        String filePath = file.getPath();
        if (!filePath.endsWith(EXTENTION)) {
            filePath += EXTENTION;
        }
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Object obj : objs) {
                oos.writeObject(obj);
            }
            oos.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            saveSuccessful = false;
        }

        return saveSuccessful;
    }

    /**
     * Read the configuration from the specified file.
     * @param file The file to load the config from
     * @return The Objects read from the file
     */
    public static Object[] loadConfig(File file) {
        ArrayList<Object> config = new ArrayList<>();
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            while(true) {
                config.add(ois.readObject());
            }

        } catch (EOFException eofe) {
            System.out.println("End of file");
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return config.toArray();
    }
}
