package Sauvegarde;

import java.io.File;

public interface StrategieSauvegarde {
    public boolean execute(File file, Object[] data);
}

