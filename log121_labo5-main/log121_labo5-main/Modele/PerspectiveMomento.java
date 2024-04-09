package Modele;

import java.awt.*;
import java.io.Serializable;

/**
 *
 * Ce module permet de capturer l'état d'une perspective à un moment donné
 *
 * @author Nguientchi Fokwe Djerelle Melissa, Pierre-Olivier Clervil, Omar Khudhair, Sif Din Marchane
 * @since H2024
 * @version Equipe K - H2024
 */
public class PerspectiveMomento implements Serializable {

    private Point position;
    private Point imageDimension;
    private double scale;

    /**
     * e constructeur crée une capture de l'état de la perspective en gardant en mémoire ses caractéristiques.
     * @param position
     *        La position de l'image
     * @param imageDimension
     *        Les dimensions de l'image
     * @param scale
     *        L'échelle du zoom
     * @return
     *        Crée une capture d'état
     */
    public PerspectiveMomento (Point position, Point imageDimension, double scale) {
        this.position = position;
        this.imageDimension = imageDimension;
        this.scale = scale;
    }

    /**
     * Récupère la position de l'image
     * @return
     *        la position de l'image
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Récupère la dimension de l'image
     * @return
     *        la nouvelle dimension de l'image
     */
    public Point getImageDimension() {
        return imageDimension;
    }

    /**
     * Récupère l'échelle du zoom
     * @return
     *        l'échelle du zoom
     */
    public double getScale() {
        return scale;
    }

}
