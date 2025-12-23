/**
 * Classe Edge
 * @author BALDE Asmaou
 **/

package CodePartB.graph;

/**
 * Représente une arête orientée dans un graphe pondéré.
 * <p>
 * Une arête relie un sommet source à un sommet destination,
 * et possède un poids représentant le coût du déplacement.
 * </p>
 */
public class Edge
{
    /** Identifiant du sommet source. */
    private final int source;
    /** Identifiant du sommet destination. */
    private final int destination;
    /** Poids (coût) associé à cette arête. */
    private final double weight;


    /**
     * Construit une arête orientée dans le graphe.
     *
     * @param source      identifiant du sommet source
     * @param destination identifiant du sommet destination
     * @param weight      poids (coût) de l'arête
     */
    public Edge(int source, int destination, double weight)
    {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }


    /**
     * @return l'identifiant du sommet source
     */
    public int getSource()
    {
        return source;
    }

    /**
     * @return l'identifiant du sommet destination
     */
    public int getDestination()
    {
        return destination;
    }

    /**
     * @return le poids (coût) de l'arête
     * */
    public double getWeight()
    {
        return weight;
    }
}
