package Sauvegarde;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerialisationSauvegarde extends StrategieSauvegarde {
    private static final String EXTENTION = ".save";

    @Override
    public boolean execute(File file, Object[] objs) {
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
        } catch (IOException ioe) {
            ioe.printStackTrace();
            saveSuccessful = false;
        }

        return saveSuccessful;
    }
}


