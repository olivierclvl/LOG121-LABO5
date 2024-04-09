package Sauvegarde;

import java.io.*;
import java.util.ArrayList;

public class DeserialisationSauvegarde extends StrategieSauvegarde {
    @Override
    public boolean execute(File file, Object[] data) {
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

        data[0] = config.toArray();
        return true;
    }
}


