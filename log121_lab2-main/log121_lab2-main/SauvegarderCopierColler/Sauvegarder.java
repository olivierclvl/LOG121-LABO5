package SauvegarderCopierColler;

import java.io.*;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * Ce module permet de représenter la classe Sauvegarder . elle permet de gérer la sauvegarde de l'état actuel dans un
 * fichier et aussi de pouvoir charger ce fichier sauvegardé.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class Sauvegarder {
    private static final String EXTENTION = ".save";
    public static final FileNameExtensionFilter EXTENTION_FILTER = new FileNameExtensionFilter(EXTENTION,EXTENTION.substring(1));

    /**
     * Permet de sauvegarder la configuration dans le fichier ou dossier choisi
     * @param file
     *        Le fichier où sauvegarder la configuration
     * @param objs
     *        Les objets à sauvegarder
     * @return
     *        True si la sauvegarde a réussi, False sinon
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
     * Permet de Lire et charger la configuration à partir du fichier sélectionné.
     * @param file
     *        Le fichier à partir duquel charger la configuration
     * @return
     *        Les objets lus à partir du fichier. l'image est chargée.
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
            System.out.println("Fin du fichier");
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
