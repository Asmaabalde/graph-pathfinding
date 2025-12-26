/**
 * Classe Edge
 * @author BALDE Asmaou
 * @see CodeMiniProjet.imagegraph.PixelNode
 **/

package CodeMiniProjet.imagegraph;

/**
 * Représente une arête sortante dans le graphe
 */
public class Edge
{
    /** Sommet cible de l'arête. */
    private final PixelNode cible;

    /** Poids de l'arête (différence d'intensité entre les deux pixels). */
    private final int weight;

    /**
     * Construit une arête sortante vers un sommet voisin.
     *
     * @param cible sommet cible de l'arête
     * @param weight poids associé à l'arête
     */
    public Edge(PixelNode cible, int weight)
    {
        this.cible = cible;
        this.weight = weight;
    }

    /**
     *  @return le sommet cible
     */
    public PixelNode getCible()
    {
        return cible;
    }

    /**
     * @return le poids de l'arête
     */
    public int getWeight()
    {
        return weight;
    }
}
