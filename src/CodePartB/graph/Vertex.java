/**
 * Classe Vertex
 * @author BALDE Asmaou
 * @see CodePartB.graph.Edge
 **/

package CodePartB.graph;
import java.util.LinkedList;

/**
 * Représente un sommet du graphe pondéré.
 * <p>
 * Un sommet possède :
 * <ul>
 *     <li>Un coût individuel de traversée (indivTime)</li>
 *     <li>Une distance temporaire depuis la source (timeFromSource)</li>
 *     <li>Un pointeur vers le sommet précédent dans le chemin (prev)</li>
 *     <li>Une liste d'arêtes sortantes (adjacencylist)</li>
 * </ul>
 * Ces valeurs sont modifiées par les algorithmes de plus court chemin.
 * </p>
 */
public class Vertex
{
    /** Identifiant unique du sommet dans le graphe. */
    private final int num;
    /** Temps nécessaire pour traverser cette case. */
    private final double indivTime;
    /** Distance temporaire depuis la source. */
    private double timeFromSource;
    /** Sommet précédent dans le chemin optimal. */
    private Vertex prev;
    /** Liste des arêtes sortantes depuis ce sommet. */
    private final LinkedList<Edge> adjacencylist;


    /**
     * Construit un sommet du graphe.
     *
     * @param num identifiant unique du sommet
     */
    public Vertex(int num, double indivTime)
    {
        this.num = num;
        this.indivTime = indivTime;
        this.timeFromSource = Double.POSITIVE_INFINITY;
       // this.heuristic = -1;
        this.prev = null;
        this.adjacencylist = new LinkedList<>();

    }

    /**
     * Ajoute une arête sortante à ce sommet.
     * @param e l'arête à ajouter
     * */
    public void addNeighbor(Edge e) {
        this.adjacencylist.addFirst(e);
    }

    /**
     * @return la liste des arêtes sortantes depuis ce sommet
     */
    public LinkedList<Edge> getAdjacencylist()
    {
        return this.adjacencylist;
    }

    /**
     * @return l'identifiant du sommet
     */
    public int getNum()
    {
        return num;
    }

    /**
     * @return le coût individuel de traversée de cette case
     * */
    public double getIndivTime()
    {
        return indivTime;
    }

    /**
     * @return la distance temporaire depuis la source
     */
    public double getTimeFromSource()
    {
        return timeFromSource;
    }

    /**
     * Met à jour la distance temporaire depuis la source.
     *
     * @param timeFromSource nouvelle distance
     * */
    public void setTimeFromSource(double timeFromSource)
    {
        this.timeFromSource = timeFromSource;
    }

    /**
     * @return le sommet précédent dans le chemin optimal
     **/
    public Vertex getPrev()
    {
        return prev;
    }

    /**
     * Met à jour le sommet précédent dans le chemin optimal.
     *
     * @param prev sommet précédent
     */
    public void setPrev(Vertex prev)
    {
        this.prev = prev;
    }
}
