package CodePartB.graph;

public class TestWeightedGrpah
{
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addEgde(0, 1, 4);
        graph.addEgde(0, 2, 3);
        graph.addEgde(1, 3, 2);
        graph.addEgde(1, 2, 5);
        graph.addEgde(2, 3, 7);
        graph.addEgde(3, 4, 2);
        graph.addEgde(4, 0, 4);
        graph.addEgde(4, 1, 4);
        graph.addEgde(4, 5, 6);
        printGraph(graph);


    }

    private static void printGraph(Graph graph) {
        for (Vertex v : graph.getVertexlist())
        {
            System.out.print("Sommet " + v.getNum() + " -> ");
            v.getAdjacencylist().forEach(
                    e -> System.out.print("(" + e.getDestination() + ", w=" + e.getWeight() + ") ")
            );
            System.out.println();
        }
    }
}
