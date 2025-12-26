/**
 * Classe Dijkstra
 * @author BALDE Asmaou
 * @see CodeMiniProjet.imagegraph.PixelNode
 * @see CodeMiniProjet.imagegraph.Edge
 **/

package CodeMiniProjet.imagegraph;


import java.util.*;


/**
 * Implémentation de l'algorithme de Dijkstra pour trouver
 * le plus court chemin entre deux pixels de l'image.
 */
public class Dijkstra
{
    /** Nombre de sommets extraits de la file de priorité lors du dernier calcul*/
    private static int exploredCount = 0;

    /**
     * @return Le nombre de sommets explorés
     */
    public static int getExploredCount()
    {
        return exploredCount;
    }

    /**
     * Calcule le plus court chemin entre un pixel source et un pixel cible.
     *
     * @param graph liste d'adjacence représentant le graphe d'image
     * @param start pixel de départ
     * @param goal pixel d'arrivée
     * @return une map des prédécesseurs permettant de reconstruire le chemin
     */
    public static Map<PixelNode, PixelNode> compute(Map<PixelNode, List<Edge>> graph, PixelNode start, PixelNode goal)
    {
        // Distance minimale connue pour chaque sommet
        Map<PixelNode, Integer> dist = new HashMap<>();

        // Pour reconstruire le chemin
        Map<PixelNode, PixelNode> previous = new HashMap<>();

        // File de priorité
        PriorityQueue<PixelNode> queue = new PriorityQueue<>(
                (a, b) -> dist.get(a) - dist.get(b)
        );

        // Initialisation
        for (PixelNode node : graph.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
        }
        dist.put(start, 0);

        queue.add(start);

        exploredCount = 0; // reset du compteur à 0

        // Boucle principale
        while (!queue.isEmpty()) {

            PixelNode u = queue.poll();
            exploredCount++;

            for (Edge edge : graph.get(u)) {
                PixelNode v = edge.getCible();
                int weight = edge.getWeight();

                int newDist = dist.get(u) + weight;

                if (newDist < dist.get(v)) {
                    dist.put(v, newDist);
                    previous.put(v, u);
                    queue.add(v);
                }
            }
        }

        return previous;
    }

    /**
     * Reconstruit le chemin final à partir de la map des prédécesseurs.
     *
     * @param previous map des prédécesseurs
     * @param start pixel de départ
     * @param goal pixel d'arrivée
     * @return une liste ordonnée de {@link PixelNode} représentant le chemin
     */
    public static List<PixelNode> reconstructPath(Map<PixelNode, PixelNode> previous, PixelNode start, PixelNode goal)
    {
        LinkedList<PixelNode> path = new java.util.LinkedList<>();

        PixelNode current = goal;

        while (current != null && !current.equals(start)) {
            path.addFirst(current);
            current = previous.get(current);
        }

        if (current != null) {
            path.addFirst(start);
        }

        return path;
    }
}
