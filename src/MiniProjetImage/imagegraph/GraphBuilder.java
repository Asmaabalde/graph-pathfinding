/**
 * Classe GraphBuilder
 * @author BALDE Asmaou
 * @see MiniProjetImage.imagegraph.PixelNode
 * @see MiniProjetImage.imagegraph.Edge
 * @see MiniProjetImage.imagegraph.ImageGraph
 **/
package MiniProjetImage.imagegraph;

import java.util.*;

/**
 * Classe responsable de la construction du graphe de l'image.
 * Chaque pixel devient un sommet (PixelNode) et les arêtes sont créées
 * selon la 4-connexité. Le poids d'une arête correspond à la différence
 * d'intensité entre les deux pixels voisins.
 */
public class GraphBuilder {

    /** Graphe représenté sous forme de liste d'adjacence. */
    private final Map<PixelNode, List<Edge>> adjacencyList = new HashMap<>();

    /** Matrice interne des PixelNode pour retrouver les bons objets */
    private PixelNode[][] nodes;

    /**
     * Construit le graphe à partir d'un objet ImageGraph.
     *
     * @param imgGraph image chargée et convertie en intensités
     */
    public GraphBuilder(ImageGraph imgGraph) {
        buildGraph(imgGraph);
    }

    /**
     * Ajoute une arête pondérée entre deux sommets.
     */
    private void addEdge(PixelNode u, PixelNode v) {
        int weight = Math.abs(u.getIntensity() - v.getIntensity());
        adjacencyList.get(u).add(new Edge(v, weight));
    }

    /**
     * Construit le graphe complet à partir d'une image.
     */
    private void buildGraph(ImageGraph imgGraph) {

        int[][] intensities = imgGraph.getIntensites();
        int width = imgGraph.getWidth();
        int height = imgGraph.getHeight();

        nodes = new PixelNode[height][width];

        // Création des sommets
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                PixelNode node = new PixelNode(x, y, intensities[y][x]);
                nodes[y][x] = node;
                adjacencyList.put(node, new ArrayList<>());
            }
        }

        // Création des arêtes (4-connexité)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                PixelNode u = nodes[y][x];

                // Voisin de droite
                if (x + 1 < width) {
                    PixelNode v = nodes[y][x + 1];
                    addEdge(u, v);
                    addEdge(v, u);
                }

                // Voisin du bas
                if (y + 1 < height) {
                    PixelNode v = nodes[y + 1][x];
                    addEdge(u, v);
                    addEdge(v, u);
                }
            }
        }
    }

    /**
     * Permet de récupérer le PixelNode exact du graphe
     * correspondant aux coordonnées cliquées.
     */
    public PixelNode getNodeAt(int x, int y) {
        return nodes[y][x];
    }

    /**
     * @return la liste d'adjacence complète du graphe
     */
    public Map<PixelNode, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }
}
