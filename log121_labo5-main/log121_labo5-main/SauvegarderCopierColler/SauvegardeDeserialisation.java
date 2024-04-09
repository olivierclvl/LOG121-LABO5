package SauvegarderCopierColler;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * Ce module permet de représenter une stratégie de chargement par désérialisation.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class SauvegardeDeserialisation extends StrategieSauvegarde {

    @Override
    public Object[] execute(File file, Object[] objs) {
        ArrayList<Object> config = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                config.add(ois.readObject());
            }
        } catch (EOFException eof) {
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return config.toArray();
    }
}
