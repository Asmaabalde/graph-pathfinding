/**
 * Classe Graph
 * @author BALDE Asmaou
 * @see CodePartB.graph.Edge
 * @see CodePartB.graph.Vertex
 **/

package CodePartB.graph;
import java.util.*;

/**
 * Représente un graphe pondéré composé de sommets ({@link Vertex})
 * et d'arêtes ({@link Edge}).
 * <p>
 * Le graphe est stocké sous forme de liste d'adjacence :
 * chaque sommet possède une liste d'arêtes sortantes.
 * </p>
 */
public class Graph
{
    /** Liste de tous les sommets du graphe. */
    private final List<Vertex> vertexlist;
    /** Compteur interne permettant d'attribuer un identifiant unique à chaque sommet. */
    private int num_v;

    /**
     * Construit un graphe vide.
     **/
    public Graph()
    {
        vertexlist = new ArrayList<>();
        num_v = 0;
    }

    /**
     * Ajoute un nouveau sommet au graphe.
     *
     * @param indivTime coût individuel de traversée du sommet
     */
    public void addVertex(double indivTime)
    {
        vertexlist.add(new Vertex(num_v++, indivTime));
    }


    /**
     * Ajoute une arête orientée entre deux sommets du graphe.
     *
     * @param source      identifiant du sommet source
     * @param destination identifiant du sommet destination
     * @param weight      poids (coût) de l'arête
     */
    public void addEgde(int source, int destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        vertexlist.get(source).addNeighbor(edge);
    }

    /**
     * @return la liste de tous les sommets du graphe
     */
    public List<Vertex> getVertexlist()
    {
        return vertexlist;
    }

    /**
     * @return le nombre total de sommets dans le graphe
     */
    public int  getNumV()
    {
        return num_v;
    }
}
