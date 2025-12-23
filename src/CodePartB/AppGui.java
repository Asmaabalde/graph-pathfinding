package CodePartB;

import CodePartB.graph.*;
import CodePartB.ui.Board;
import CodePartB.algorithms.*;
import CodePartB.heuristique.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AppGui extends JFrame
{
    // Éléments de l'interface
    private JComboBox<String> algoComboBox;
    private JComboBox<String> heuristicComboBox;
    private JButton loadFileButton;
    private JButton runButton;
    private JLabel statusLabel;
    private JLabel fileLabel;

    // Données choisies / calculées
    private File inputFile = null;

    public AppGui()
    {
        super("GraphShortestPath - BALDE Asmaou");

        // Configuration générale de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Création des panneaux
        createTopBar();
        createCenterPanel();
        createBottomPanel();

        setVisible(true);
    }

    /**
     * Barre supérieure : Algorithme | Titre | Charger un fichier
     */
    private void createTopBar()
    {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        topBar.setBackground(new Color(245, 245, 245));

        // Partie gauche : Algorithme + combo
        JPanel algoPanel = new JPanel();
        algoPanel.setOpaque(false);
        JLabel algoLabel = new JLabel("Algorithme : ");
        algoComboBox = new JComboBox<>(new String[] { "Dijkstra", "A*" });
        algoComboBox.addActionListener(this::onAlgoChanged);
        algoPanel.add(algoLabel);
        algoPanel.add(algoComboBox);

        // Partie centrale : Titre
        JLabel titleLabel = new JLabel("GraphShortestPath", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        // Partie droite : bouton de chargement de fichier
        JPanel filePanel = new JPanel();
        filePanel.setOpaque(false);
        loadFileButton = new JButton("Charger un fichier");
        loadFileButton.addActionListener(this::onLoadFileClicked);
        filePanel.add(loadFileButton);

        topBar.add(algoPanel, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(filePanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
    }

    /**
     * Panneau central : fichier sélectionné, heuristique, état
     */
    private void createCenterPanel()
    {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Ligne fichier
        JPanel fileRow = new JPanel(new BorderLayout());
        JLabel fileTextLabel = new JLabel("Fichier de carte : ");
        fileLabel = new JLabel("Aucun fichier sélectionné");
        fileRow.add(fileTextLabel, BorderLayout.WEST);
        fileRow.add(fileLabel, BorderLayout.CENTER);

        // Ligne heuristique
        JPanel heuristicRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel heuristicLabel = new JLabel("Heuristique (A*) : ");
        heuristicComboBox = new JComboBox<>(new String[] { "Manhattan", "Euclidienne", "Octile" });
        heuristicComboBox.setEnabled(false); // activé seulement si A* choisi
        heuristicRow.add(heuristicLabel);
        heuristicRow.add(heuristicComboBox);

        // Ligne état
        JPanel statusRow = new JPanel(new BorderLayout());
        JLabel statusTextLabel = new JLabel("État : ");
        statusLabel = new JLabel("En attente de vos choix...");
        statusRow.add(statusTextLabel, BorderLayout.WEST);
        statusRow.add(statusLabel, BorderLayout.CENTER);

        centerPanel.add(fileRow);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(heuristicRow);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(statusRow);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Panneau inférieur : bouton Lancer
     */
    private void createBottomPanel()
    {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        runButton = new JButton("Lancer le calcul");
        runButton.addActionListener(this::onRunClicked);

        bottomPanel.add(runButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Callback : changement d'algorithme
     */
    private void onAlgoChanged(ActionEvent e)
    {
        String algo = (String) algoComboBox.getSelectedItem();
        if ("A*".equals(algo)) {
            heuristicComboBox.setEnabled(true);
            statusLabel.setText("Algorithme A* sélectionné. Choisissez une heuristique.");
        } else {
            heuristicComboBox.setEnabled(false);
            statusLabel.setText("Algorithme Dijkstra sélectionné.");
        }
    }

    /**
     * Callback : clic sur "Charger un fichier"
     */
    private void onLoadFileClicked(ActionEvent e)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choisissez un fichier de carte (graph.txt)");
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            inputFile = chooser.getSelectedFile();
            fileLabel.setText(inputFile.getName());
            statusLabel.setText("Fichier chargé : " + inputFile.getName());
        }
    }

    /**
     * Callback : clic sur "Lancer le calcul"
     */
    private void onRunClicked(ActionEvent e)
    {
        // Vérifications minimales
        if (inputFile == null) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez d'abord choisir un fichier de carte.",
                    "Fichier manquant",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        final String algo = (String) algoComboBox.getSelectedItem();

        final Heuristic heuristic;
        if ("A*".equals(algo)) {
            String h = (String) heuristicComboBox.getSelectedItem();
            if ("Manhattan".equals(h)) {
                heuristic = new ManhattanHeuristic();
            } else if ("Euclidienne".equals(h)) {
                heuristic = new EuclideanHeuristic();
            } else {
                heuristic = new OctileHeuristic();
            }
        } else {
            heuristic = null;
        }

        final String outputName = JOptionPane.showInputDialog(
                this,
                "Nom du fichier de sortie :",
                "nomFichier.txt"
        );
        if (outputName == null || outputName.isBlank()) {
            statusLabel.setText("Annulé : aucun fichier de sortie défini.");
            return;
        }


        statusLabel.setText("Chargement du graphe et exécution de l'algorithme...");
        runButton.setEnabled(false);

        // Lancement dans un thread séparé
        new Thread(() -> {
            try {
                runAlgorithm(algo, heuristic, outputName);

                SwingUtilities.invokeLater(() ->
                        statusLabel.setText("Calcul terminé. Résultat écrit dans " + outputName + ".")
                );
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this,
                                "Erreur lors du calcul : " + ex.getMessage(),
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE)
                );
                SwingUtilities.invokeLater(() ->
                        statusLabel.setText("Erreur pendant l'exécution.")
                );
            } finally {
                SwingUtilities.invokeLater(() ->
                        runButton.setEnabled(true)
                );
            }
        }).start();
    }


    /**
     * Charge le graphe depuis le fichier, lance l'algorithme choisi,
     * affiche le Board et écrit le chemin dans le fichier de sortie.
     */
    private void runAlgorithm(String algo, Heuristic heuristic, String outputName) throws IOException
    {
        // Lecture du fichier et construction du graphe
        Graph graph = new Graph();
        int nlines;
        int ncols;
        int startV;
        int endV;
        HashMap<Integer, String> groundColor = new HashMap<>();

        try (Scanner myReader = new Scanner(inputFile)) {
            String data = "";

            // On lit les trois premières lignes, la troisième contient nlines
            for (int i = 0; i < 3; i++) {
                data = myReader.nextLine();
            }
            nlines = Integer.parseInt(data.split("=")[1].trim());

            // ncol
            data = myReader.nextLine();
            ncols = Integer.parseInt(data.split("=")[1].trim());

            HashMap<String, Integer> groundTypes = new HashMap<>();

            // On lit jusqu'à "==Graph=="
            data = myReader.nextLine();
            data = myReader.nextLine();

            while (!data.equals("==Graph=="))
            {
                String name = data.split("=")[0].trim();
                name = name.replace("\uFEFF", ""); // <-- AJOUT
                int time = Integer.parseInt(data.split("=")[1].trim());

                data = myReader.nextLine();
                String color = data.trim();

                groundTypes.put(name, time);
                groundColor.put(time, color);

                data = myReader.nextLine();
            }

            // Ajout des sommets (cases) dans le graphe
            for (int line = 0; line < nlines; line++)
            {
                data = myReader.nextLine();
                for (int col = 0; col < ncols; col++)
                {
                    String terrainKey = String.valueOf(data.charAt(col)).trim();
                    Integer indivTime = groundTypes.get(terrainKey);

                    if (indivTime == null) {
                        throw new IllegalArgumentException("Type de terrain inconnu : '" + terrainKey + "' à la ligne " + line + ", colonne " + col);
                    }

                    graph.addVertex(indivTime);

                }
            }

            // Ajout des arêtes (4 directions + diagonales)
            for (int line = 0; line < nlines; line++)
            {
                for (int col = 0; col < ncols; col++)
                {
                    int source = line * ncols + col;

                    // Vers le haut
                    if (line > 0)
                    {
                        int destUp = (line - 1) * ncols + col;
                        double weightUp = computeWeight(graph, source, destUp, false);
                        graph.addEgde(source, destUp, weightUp);
                        graph.addEgde(destUp, source, weightUp);

                        // Diagonale haut-gauche
                        if (col > 0)
                        {
                            int destUpLeft = (line - 1) * ncols + (col - 1);
                            double weightUpLeft = computeWeight(graph, source, destUpLeft, true);
                            graph.addEgde(source, destUpLeft, weightUpLeft);
                            graph.addEgde(destUpLeft, source, weightUpLeft);
                        }

                        // Diagonale haut-droite
                        if (col < ncols - 1)
                        {
                            int destUpRight = (line - 1) * ncols + (col + 1);
                            double weightUpRight = computeWeight(graph, source, destUpRight, true);
                            graph.addEgde(source, destUpRight, weightUpRight);
                            graph.addEgde(destUpRight, source, weightUpRight);
                        }
                    }

                    // Vers la gauche
                    if (col > 0)
                    {
                        int destLeft = line * ncols + (col - 1);
                        double weightLeft = computeWeight(graph, source, destLeft, false);
                        graph.addEgde(source, destLeft, weightLeft);
                        graph.addEgde(destLeft, source, weightLeft);
                    }
                }
            }

            // Lecture du bloc ==Path==
            String line = myReader.nextLine(); // "==Path=="
            line = myReader.nextLine();        // Start=...
            String[] startParts = line.split("=")[1].split(",");
            int startLine = Integer.parseInt(startParts[0].trim());
            int startCol = Integer.parseInt(startParts[1].trim());
            startV = startLine * ncols + startCol;

            line = myReader.nextLine();
            String[] endParts = line.split("=")[1].split(",");
            int endLine = Integer.parseInt(endParts[0].trim());
            int endCol = Integer.parseInt(endParts[1].trim());
            endV = endLine * ncols + endCol;
        }

        // Affichage du board
        int pixelSize = 10;
        Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
        drawBoard(board, nlines, ncols, pixelSize);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}

        // Exécution de l'algorithme
        LinkedList<Integer> path;
        if ("Dijkstra".equals(algo)) {
            path = Djikstra.compute(graph, startV, endV, board);
        } else {
            // A*
            path = AStar.compute(graph, startV, endV, board, heuristic, ncols);
        }

        // Écriture du chemin dans le fichier de sortie
        File fichier = new File(outputName);
        File filePath;
        if(outputName.contains("/") || outputName.contains("\\") || fichier.isAbsolute())
        {
            if(!outputName.toLowerCase().endsWith(".txt"))
                 filePath = new File(fichier + ".txt");
            else
                filePath = fichier;
        }
        else
        {
            // on vérifie si le nom du fichier contient déjà l'extension .txt
            if(!outputName.toLowerCase().endsWith(".txt"))
                outputName += ".txt";
            File dossier = new File("src/TP7/sauvegardes");
            // Création du dossier s'il n'existe pas déjà
            if(!dossier.exists())
                dossier.mkdir();
            filePath = new File(dossier, outputName);
        }

        if (!filePath.exists()) {
            filePath.createNewFile();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Algorithme utilisé : " + algo + " (");
            if(!algo.equals("Dijkstra"))
                bw.write(heuristic.natureHeuristique() + ")\n");
            bw.newLine();

            bw.write("Liste des sommets du plus court chemin trouvé : ");
            bw.newLine();
            for (int v : path) {
                bw.write(String.valueOf(v));
                bw.newLine();
            }
        }
    }

    /**
     * Ouvre une fenêtre pour afficher le Board.
     */
    private static void drawBoard(Board board, int nlines, int ncols, int pixelSize)
    {
        JFrame window = new JFrame("Plus court chemin ");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setBounds(0, 0, ncols * pixelSize + 20, nlines * pixelSize + 40);
        window.getContentPane().add(board);
        window.setVisible(true);
    }

    /**
     * Calcule le poids d'une arête entre deux sommets du graphe.
     * Poids = moyenne des coûts individuels des deux cases,
     * multipliée par √2 si le déplacement est diagonal.
     */
    private static double computeWeight(Graph graph, int source, int dest, boolean diagonal)
    {
        List<Vertex> vertices = graph.getVertexlist();
        double tA = vertices.get(source).getIndivTime();
        double tB = vertices.get(dest).getIndivTime();
        double base = (tA + tB) / 2.0;
        return diagonal ? base * Math.sqrt(2) : base;
    }

    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(AppGui::new);
    }
}
