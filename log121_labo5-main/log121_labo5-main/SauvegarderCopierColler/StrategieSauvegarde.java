package SauvegarderCopierColler;

import java.io.File;

/**
 *
 * Ce module permet de représenter la classe Sauvegarder abstraite définit la stratégie de sauvegarde et de chargement.
 * Les classes qui l'étendent doivent implémenter la méthode execute pour réaliser l'opération spécifique.
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public abstract class StrategieSauvegarde {

    protected static final String EXTENTION = ".save";

    /**
     * Execute la stratégie de sauvegarde ou de chargement.
     *
     * @param file Le fichier sur lequel effectuer l'opération.
     * @param objs Les objets à traiter, peut être null pour le chargement.
     * @return Les objets traités, pour la sauvegarde retourne null.
     */
    public abstract Object[] execute(File file, Object[] objs);
}
