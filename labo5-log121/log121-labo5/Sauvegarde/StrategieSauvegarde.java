package Sauvegarde;

import java.io.File;

public abstract class StrategieSauvegarde {
    public abstract boolean execute(File file, Object[] data);
}

