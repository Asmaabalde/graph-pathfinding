package CodePartB.algorithms;

import CodePartB.graph.*;
import CodePartB.ui.Board;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Implémentation de l'algorithme de Dijkstra pour le plus court chemin.
 */
public class Djikstra
{
    /**
     * Calcule le plus court chemin entre deux sommets du graphe.
     *
     * @param graph graphe pondéré
     * @param start indice du sommet de départ
     * @param end   indice du sommet d'arrivée
     * @param board composant d'affichage (peut être null)
     * @return liste ordonnée des sommets du chemin optimal
     */
    public static LinkedList<Integer> compute(Graph graph, int start, int end, Board board)
    {
        // Initialisation
        graph.getVertexlist().get(start).setTimeFromSource(0);
        // Nombre de sommets explorés pour mésurer la performance
        int number_tries = 0;
        int numberV = graph.getVertexlist().size();

        // Ensemble des sommets à visiter
        HashSet<Integer> to_visit = new HashSet<>();

        // Ajout des noeuds du graphe dans la liste des noeuds à visiter
        for(int i = 0; i < numberV; i++)
        {
            to_visit.add(i);
        }

        // Boucle principale
        while (to_visit.contains(end))
        {
            // trouver min_v (sommet avec la plus petite distance temporaire)
            int min_v = -1;
            double min_distance = Double.POSITIVE_INFINITY;
            for(int v :  to_visit)
            {
                double distance = graph.getVertexlist().get(v).getTimeFromSource();
                if(distance < min_distance)
                {
                    min_distance = distance;
                    min_v = v;
                }
            }

            // Si aucun sommet n'est atteignable
            if(min_v == -1)
            {
                System.out.println("Aucun chemin trouvé");
                return new LinkedList<>();
            }
            number_tries++;

            //Pour tous ses voisins, on vérifie si on est plus rapide en passant par ce noeud.
            for(Edge e : graph.getVertexlist().get(min_v).getAdjacencylist())
            {
                int to_try = e.getDestination();
                double weight = e.getWeight();

                double newDistance = graph.getVertexlist().get(min_v).getTimeFromSource() + weight;

                if(newDistance < graph.getVertexlist().get(to_try).getTimeFromSource())
                {
                    graph.getVertexlist().get(to_try).setTimeFromSource(newDistance);
                    graph.getVertexlist().get(to_try).setPrev(graph.getVertexlist().get(min_v));
                }
            }

            // Mise à jour de l'affichage
            if(board != null)
            {
                try {
                    board.update(graph, min_v);
                    Thread.sleep(10);
                } catch(InterruptedException e) {
                    System.out.println("stop");
                }
            }

            // Suppression de min_v
            to_visit.remove(min_v);
        }

        // Remplissage de la liste path avec le chemin
        LinkedList<Integer> path=new LinkedList<>();
        int current = end;
        path.addFirst(current);

        while (graph.getVertexlist().get(current).getPrev() != null)
        {
            current = graph.getVertexlist().get(current).getPrev().getNum();
            path.addFirst(current);
        }

        // Affichage du chemin final
        if(board != null)
        {
            board.addPath(graph, path);
        }

        // Statistiques
        System.out.println("Done! Using Dijkstra:");
        System.out.println("Number of nodes explored: " + number_tries);
        System.out.println("Total time of the path: " + graph.getVertexlist().get(end).getTimeFromSource());

        return path;
    }
}
