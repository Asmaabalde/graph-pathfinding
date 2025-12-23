//package TP7;
//
//import TP7.graph.*;
//import TP7.ui.Board;
//import TP7.algorithms.*;
//import TP7.heuristique.*;
//import javax.swing.*;
//import java.io.*;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.Scanner;
//
//public class App
//{
//    // Méthode principale
//    public static void main(String[] args) {
//        //Lecture de la carte et création du graphe
//        try {
//            File myObj = new File("graph.txt");
//            Scanner myReader = new Scanner(myObj);
//            String data = "";
//
//            //On ignore les trois premières lignes
//            for (int i=0; i < 3; i++)
//                data = myReader.nextLine();
//
//            //Lecture du nombre de lignes
//            int nlines = Integer.parseInt(data.split("=")[1]);
//            //Et du nombre de colonnes
//            data = myReader.nextLine();
//            int ncols = Integer.parseInt(data.split("=")[1]);
//
//            //Initialisation du graphe
//            Graph graph = new Graph();
//
//            HashMap<String, Integer> groundTypes = new HashMap<>();
//            HashMap<Integer, String> groundColor = new HashMap<>();
//
//            data = myReader.nextLine();
//            data = myReader.nextLine();
//
//            //Lire les différents types de cases
//            while (!data.equals("==Graph=="))
//            {
//                String name = data.split("=")[0];
//                int time = Integer.parseInt(data.split("=")[1]);
//
//                data = myReader.nextLine();
//                String color = data;
//
//                groundTypes.put(name, time);
//                groundColor.put(time, color);
//
//                data = myReader.nextLine();
//            }
//
//            //On ajoute les sommets dans le graphe (avec le bon type)
//            for (int line=0; line < nlines; line++)
//            {
//                data = myReader.nextLine();
//                for (int col=0; col < ncols; col++)
//                {
//                    graph.addVertex(groundTypes.get(String.valueOf(data.charAt(col))));
//                }
//            }
//
//            // Ajout des arêtes (4 directions + diagonales)
//            for (int line=0; line < nlines; line++)
//            {
//                for (int col=0; col < ncols; col++)
//                {
//                    int source = line * ncols + col;
//
//                    // vers le haut
//                    if (line > 0)
//                    {
//                        int destUp = (line - 1) * ncols + col;
//                        double weightUp = computeWeight(graph,source,destUp,false);
//                        graph.addEgde(source, destUp, weightUp);
//                        graph.addEgde(destUp, source, weightUp);
//
//                        // Diagonale haut-gauche
//                        if (col > 0)
//                        {
//                            int destUpLeft = (line - 1)* ncols + (col - 1);
//                            double weightUpLeft = computeWeight(graph, source, destUpLeft,true);
//                            graph.addEgde(source, destUpLeft, weightUpLeft);
//                            graph.addEgde(destUpLeft, source, weightUpLeft);
//                        }
//
//                        // Diagonale haut-droite
//                        if(col < ncols - 1)
//                        {
//                            int  destUpRight = (line - 1) * ncols + (col + 1);
//                            double weightUpRight = computeWeight(graph, source, destUpRight,true);
//                            graph.addEgde(source, destUpRight, weightUpRight);
//                            graph.addEgde(destUpRight, source, weightUpRight);
//                        }
//                    }
//
//                    // Vers la gauche
//                    if(col > 0)
//                    {
//                        int destLeft = line * ncols + (col - 1);
//                        double weightLeft = computeWeight(graph, source, destLeft,false);
//                        graph.addEgde(source, destLeft, weightLeft);
//                        graph.addEgde(destLeft, source, weightLeft);
//                    }
//                }
//            }
//
//            // On obtient les noeuds de départ et d'arrivée
//            data = myReader.nextLine();
//            data = myReader.nextLine();
//            int startV = Integer.parseInt(data.split("=")[1].split(",")[0]) * ncols + Integer.parseInt(data.split("=")[1].split(",")[1]);
//            data = myReader.nextLine();
//            int endV = Integer.parseInt(data.split("=")[1].split(",")[0]) * ncols + Integer.parseInt(data.split("=")[1].split(",")[1]);
//
//            myReader.close();
//
//            // Affichage modifiable en fonction de la taille de la carte (en modifiant la valeur de pixelSize)
//            int pixelSize = 10;
//            Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
//            drawBoard(board, nlines, ncols, pixelSize);
//            board.repaint();
//
//            try {
//                Thread.sleep(100);
//            } catch(InterruptedException e) {
//                System.out.println("stop");
//            }
//
//            // Choix de l'algorithme
//
//            //On appelle Dijkstra
//            Djikstra.compute(graph,startV, endV, board);
//            //TODO: laisser le choix entre Dijkstra et A*
//
//            //�criture du chemin dans un fichier de sortie
//            try {
//                File file = new File("out.txt");
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
//                FileWriter fw = new FileWriter(file.getAbsoluteFile());
//                BufferedWriter bw = new BufferedWriter(fw);
//
//                for (int i: path)
//                {
//                    bw.write(String.valueOf(i));
//                    bw.write('\n');
//                }
//                bw.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }
//
//    //Initialise l'affichage
//    private static void drawBoard(Board board, int nlines, int ncols, int pixelSize)
//    {
//        JFrame window = new JFrame("Plus court chemin");
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setBounds(0, 0, ncols * pixelSize + 20, nlines * pixelSize + 40);
//        window.getContentPane().add(board);
//        window.setVisible(true);
//    }
//
//    /**
//     * Calcule le poids d'une arête entre deux sommets du graphe.
//     * Poids = moyenne des coûts individuels des deux cases,
//     * multipliée par √2 si le déplacement est diagonal.
//     */
//    private static double computeWeight(Graph graph, int source, int dest, boolean diagonal)
//    {
//        double tA = graph.getVertexlist().get(source).getIndivTime();
//        double tB = graph.getVertexlist().get(dest).getIndivTime();
//        double base = (tA + tB) / 2.0;
//
//        return diagonal ? base * Math.sqrt(2) : base;
//    }
//
//
//
//}
