/**
 * Classe PixelNode
 * @author BALDE Asmaou
 **/

package CodeMiniProjet.imagegraph;

/**
 * Représente un pixel de l'image sous forme de sommet du graphe.
 * <p>
 * Chaque pixel est identifié par ses coordonnées (x, y) dans l'image
 * ainsi que par son intensité lumineuse. Cette classe constitue l'unité
 * de base du graphe utilisé pour appliquer l'algorithme de Dijkstra.
 * </p>
 */
public class PixelNode
{
    /** Coordonnée horizontale du pixel (colonne). */
    private int x;
    /** Coordonnée verticale du pixel (ligne). */
    private int y;
    /** Intensité lumineuse du pixel (0 à 255). */
    private final int intensity;


    /**
     * Construit un sommet représentant un pixel de l'image.
     *
     * @param x coordonnée horizontale du pixel
     * @param y coordonnée verticale du pixel
     * @param intensity intensité lumineuse du pixel (0-255)
     */
    public PixelNode(int x, int y,  int intensity)
    {
        this.x = x;
        this.y = y;
        this.intensity = intensity;
    }

    /** @return la coordonnée x du pixel */
    public int getX()
    {
        return x;
    }

    /** @return la coordonnée y du pixel */
    public int getY()
    {
        return y;
    }

    /** @return la coordonnée y du pixel */
    public int getIntensity()
    {
        return intensity;
    }

    /**
     * Deux PixelNode sont égaux s'ils représentent le même pixel
     * dans l'image (mêmes coordonnées)
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof PixelNode p)) return false;
        return x == p.x && y == p.y;
    }

    /**
     * HashCode basé sur les coordonnées, nécessaire pour les structures
     * de données utilisées par Dijkstra (HashMap, HashSet...).
     */
    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    /**
     * @return une réprésentation textuelle de l'objet PixelNode
     */
    @Override
    public String toString()
    {
        return "PixelNode(" + x + ", " + y + ", I=" + intensity + ")";
    }
}
