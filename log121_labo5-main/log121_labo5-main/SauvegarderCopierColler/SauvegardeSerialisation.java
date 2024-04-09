package SauvegarderCopierColler;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 *
 * Ce module permet de représenter une stratégie de sauvegarde par sérialisation.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class SauvegardeSerialisation extends StrategieSauvegarde {

    public static final FileNameExtensionFilter EXTENTION_FILTER = new FileNameExtensionFilter(EXTENTION, EXTENTION.substring(1));

    @Override
    public Object[] execute(File file, Object[] objs) {
        String filePath = file.getPath();
        if (!filePath.endsWith(EXTENTION)) {
            filePath += EXTENTION;
        }
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Object obj : objs) {
                oos.writeObject(obj);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
        return null;
    }
}
