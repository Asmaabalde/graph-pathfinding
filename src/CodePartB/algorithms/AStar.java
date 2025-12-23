package CodePartB.algorithms;

import CodePartB.heuristique.*;
import CodePartB.ui.*;
import CodePartB.graph.*;

import java.util.HashSet;
import java.util.LinkedList;

public class AStar
{
    public static LinkedList<Integer> compute(Graph graph, int start, int end, Board board, Heuristic heuristic, int ncols)
    {
        int n = graph.getVertexlist().size();

        double[] gScore = new double[n];
        double[] fScore = new double[n];
        int[] cameFrom = new int[n];

        for (int i = 0; i < n; i++)
        {
            gScore[i] = Double.POSITIVE_INFINITY;
            fScore[i] = Double.POSITIVE_INFINITY;
            cameFrom[i] = -1;
        }

        gScore[start] = 0;
        fScore[start] = heuristic.estimate(start, end, ncols);

        HashSet<Integer> openSet = new HashSet<>();
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

                if (min_v == end)
                {
                    found = true;
                }
                else
                {
                    for (Edge e : graph.getVertexlist().get(min_v).getAdjacencylist())
                    {
                        int neighbor = e.getDestination();

                        if (!closedSet.contains(neighbor))
                        {
                            double tentative_g = gScore[min_v] + e.getWeight();

                            if (tentative_g < gScore[neighbor])
                            {
                                cameFrom[neighbor] = min_v;
                                gScore[neighbor] = tentative_g;
                                fScore[neighbor] =
                                        tentative_g + heuristic.estimate(neighbor, end, ncols);

                                openSet.add(neighbor);
                            }
                        }
                    }
                }

                if (board != null)
                {
                    try
                    {
                        board.update(graph, min_v);
                        Thread.sleep(10);
                    }
                    catch (InterruptedException ignored) {}
                }
            }
        }

        // Reconstruction du chemin
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

        // Statistiques
        System.out.println("Done! Using A*:" + "(" + heuristic.natureHeuristique() + ")");
        System.out.println("    Number of nodes explored: " + number_tries);
        System.out.println("    Total cost of the path: " + gScore[end]);

        return path;
    }
}
