/**
 * Classe Astar
 * @author BALDE Asmaou
 **/

package CodePartB.algorithms;

import CodePartB.heuristique.*;
import CodePartB.ui.*;
import CodePartB.graph.*;

import java.util.*;


public class AStar
{
    /**
     * Calcule le plus court chemin entre deux sommets du graphe.
     *
     * @param graph     graphe pondéré
     * @param start     sommet de départ
     * @param end       sommet d'arrivée
     * @param board     composant graphique pour l'animation (peut être null)
     * @param heuristic heuristique utilisée pour estimer la distance restante
     * @param ncols     nombre de colonnes (utile pour l'heuristique)
     * @return          chemin optimal sous forme de liste ordonnée
     */
    public static LinkedList<Integer> compute(Graph graph, int start, int end, Board board, Heuristic heuristic, int ncols)
    {
        int n = graph.getVertexlist().size();

        // Coût réel depuis la source
        double[] gScore = new double[n];
        // Coût estimé total (g + h)
        double[] fScore = new double[n];
        // Reconstruction du chemin
        int[] cameFrom = new int[n];

        // Initialisation des tableaux
        for (int i = 0; i < n; i++)
        {
            gScore[i] = Double.POSITIVE_INFINITY;
            fScore[i] = Double.POSITIVE_INFINITY;
            cameFrom[i] = -1;
        }

        gScore[start] = 0;
        fScore[start] = heuristic.estimate(start, end, ncols);

        // Sommets à explorer
        HashSet<Integer> openSet = new HashSet<>();
        // Sommets déjà traités
        HashSet<Integer> closedSet = new HashSet<>();

        openSet.add(start);

        int number_tries = 0;
        boolean found = false;

        while (!openSet.isEmpty() && !found)
        {
            int min_v = -1;
            double min_f = Double.POSITIVE_INFINITY;

            // Recherche du sommet avec f minimal
            for (int v : openSet)
            {
                if (fScore[v] < min_f)
                {
                    min_f = fScore[v];
                    min_v = v;
                }
            }

            if (min_v != -1)
            {
                number_tries++;

                openSet.remove(min_v);
                closedSet.add(min_v);

                // Objectif atteint
                if (min_v == end)
                {
                    found = true;
                }
                else
                {
                    // Exploration des voisins
                    for (Edge e : graph.getVertexlist().get(min_v).getAdjacencylist())
                    {
                        int neighbor = e.getDestination();

                        if (!closedSet.contains(neighbor))
                        {
                            double tentative_g = gScore[min_v] + e.getWeight();

                            // Meilleur chemin trouvé vers neighbor
                            if (tentative_g < gScore[neighbor])
                            {
                                cameFrom[neighbor] = min_v;
                                gScore[neighbor] = tentative_g;
                                fScore[neighbor] =
                                        tentative_g + heuristic.estimate(neighbor, end, ncols);

                                // Mise à jour pour l'affichage (Board)
                                graph.getVertexlist().get(neighbor).setTimeFromSource(tentative_g);
                                graph.getVertexlist().get(neighbor).setPrev(graph.getVertexlist().get(min_v));

                                openSet.add(neighbor);
                            }
                        }
                    }
                }

                // Animation de l'exploration
                if (board != null)
                {
                    try
                    {
                        board.update(graph, min_v);
                        Thread.sleep(10);
                    }
                    catch(InterruptedException e) {
                        System.out.println("stop");
                    }
                }
            }
        }

        // Reconstruction du chemin final
        LinkedList<Integer> path = new LinkedList<>();

        if (found)
        {
            int current = end;
            path.addFirst(current);

            while (cameFrom[current] != -1)
            {
                current = cameFrom[current];
                path.addFirst(current);
            }

            if (board != null)
                board.addPath(graph, path);
        }

        // Statistiques (console)
        System.out.println("Done! Using A*:" + "(" + heuristic.natureHeuristique() + ")");
        System.out.println("    Number of nodes explored: " + number_tries);
        System.out.println("    Total cost of the path: " + gScore[end]);

        return path;
    }
}
