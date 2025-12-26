/**
 * Application principale permettant de charger un fichier de carte,
 * sélectionner un algorithme (Dijkstra ou A*), lancer le calcul
 * et afficher le résultat dans une interface graphique.
 *
 * @author BALDE Asmaou
 */
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
import java.util.*;
import java.util.List;

public class GraphShortestPath extends JFrame
{
    // Composants de l'interface
    private JComboBox<String> algoComboBox;
    private JComboBox<String> heuristicComboBox;
    private JButton loadFileButton;
    private JButton runButton;
    private JLabel statusLabel;
    private JLabel fileLabel;

    // Fichier de carte sélectionné
    private File inputFile = null;

    /**
     * Constructeur : initialise la fenêtre et les panneaux.
     */
    public GraphShortestPath()
    {
        super("GraphShortestPath - BALDE Asmaou");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createTopBar();
        createCenterPanel();
        createBottomPanel();

        setVisible(true);
    }

    /**
     * Barre supérieure : choix de l'algorithme et chargement du fichier.
     */
    private void createTopBar()
    {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        topBar.setBackground(new Color(245, 245, 245));

        // Choix de l'algorithme
        JPanel algoPanel = new JPanel();
        algoPanel.setOpaque(false);
        JLabel algoLabel = new JLabel("Algorithme : ");
        algoComboBox = new JComboBox<>(new String[] { "Dijkstra", "A*" });
        algoComboBox.addActionListener(this::onAlgoChanged);
        algoPanel.add(algoLabel);
        algoPanel.add(algoComboBox);

        // Titre centré
        JLabel titleLabel = new JLabel("GraphShortestPath", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        // Bouton de chargement
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
     * Panneau central : affichage du fichier, heuristique et état.
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

        // Ligne heuristique (activée seulement pour A*)
        JPanel heuristicRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel heuristicLabel = new JLabel("Heuristique (A*) : ");
        heuristicComboBox = new JComboBox<>(new String[] { "Manhattan", "Euclidienne", "Octile" });
        heuristicComboBox.setEnabled(false);
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
     * Panneau inférieur : bouton pour lancer le calcul.
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
     * Callback : changement d'algorithme.
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
     * Callback : chargement d'un fichier de carte.
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
     * Callback : lancement du calcul.
     */
    private void onRunClicked(ActionEvent e)
    {
        if (inputFile == null) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez d'abord choisir un fichier de carte.",
                    "Fichier manquant",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        final String algo = (String) algoComboBox.getSelectedItem();

        // Sélection de l'heuristique si A*
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

        // Nom du fichier de sortie
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

        // Exécution dans un thread séparé pour ne pas bloquer l'UI
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
     * Charge le graphe, exécute l'algorithme choisi,
     * affiche le Board et écrit le chemin dans un fichier.
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

        // Lecture structurée du fichier
        try (Scanner myReader = new Scanner(inputFile)) {
            String data = "";

            // Lecture des paramètres initiaux
            for (int i = 0; i < 3; i++) {
                data = myReader.nextLine();
            }
            nlines = Integer.parseInt(data.split("=")[1].trim());

            data = myReader.nextLine();
            ncols = Integer.parseInt(data.split("=")[1].trim());

            HashMap<String, Integer> groundTypes = new HashMap<>();

            // Lecture des types de terrain
            data = myReader.nextLine();
            data = myReader.nextLine();

            while (!data.equals("==Graph=="))
            {
                String name = data.split("=")[0].trim();
                name = name.replace("\uFEFF", "");
                int time = Integer.parseInt(data.split("=")[1].trim());

                data = myReader.nextLine();
                String color = data.trim();

                groundTypes.put(name, time);
                groundColor.put(time, color);

                data = myReader.nextLine();
            }

            // Ajout des sommets
            for (int line = 0; line < nlines; line++)
            {
                data = myReader.nextLine();
                for (int col = 0; col < ncols; col++)
                {
                    String terrainKey = String.valueOf(data.charAt(col)).trim();
                    Integer indivTime = groundTypes.get(terrainKey);

                    if (indivTime == null) {
                        throw new IllegalArgumentException("Type de terrain inconnu : '" + terrainKey + "'");
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

                    // Haut
                    if (line > 0)
                    {
                        int destUp = (line - 1) * ncols + col;
                        double weightUp = computeWeight(graph, source, destUp, false);
                        graph.addEgde(source, destUp, weightUp);
                        graph.addEgde(destUp, source, weightUp);

                        // Diagonales
                        if (col > 0)
                        {
                            int destUpLeft = (line - 1) * ncols + (col - 1);
                            double weightUpLeft = computeWeight(graph, source, destUpLeft, true);
                            graph.addEgde(source, destUpLeft, weightUpLeft);
                            graph.addEgde(destUpLeft, source, weightUpLeft);
                        }

                        if (col < ncols - 1)
                        {
                            int destUpRight = (line - 1) * ncols + (col + 1);
                            double weightUpRight = computeWeight(graph, source, destUpRight, true);
                            graph.addEgde(source, destUpRight, weightUpRight);
                            graph.addEgde(destUpRight, source, weightUpRight);
                        }
                    }

                    // Gauche
                    if (col > 0)
                    {
                        int destLeft = line * ncols + (col - 1);
                        double weightLeft = computeWeight(graph, source, destLeft, false);
                        graph.addEgde(source, destLeft, weightLeft);
                        graph.addEgde(destLeft, source, weightLeft);
                    }
                }
            }

            // Lecture du bloc Path
            String line = myReader.nextLine();
            line = myReader.nextLine();
            String[] startParts = line.split("=")[1].split(",");
            startV = Integer.parseInt(startParts[0].trim()) * ncols +
                    Integer.parseInt(startParts[1].trim());

            line = myReader.nextLine();
            String[] endParts = line.split("=")[1].split(",");
            endV = Integer.parseInt(endParts[0].trim()) * ncols +
                    Integer.parseInt(endParts[1].trim());
        }

        // Affichage du Board
        int pixelSize = 10;
        Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
        drawBoard(board, nlines, ncols, pixelSize);

        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        // Exécution de l'algorithme
        LinkedList<Integer> path;
        if ("Dijkstra".equals(algo)) {
            path = Dijkstra.compute(graph, startV, endV, board);
        } else {
            path = AStar.compute(graph, startV, endV, board, heuristic, ncols);
        }

        // Écriture du résultat dans un fichier
        String currentDir = System.getProperty("user.dir");

        // Dossier de sauvegarde portable
        File saveDir = new File(currentDir + File.separator + "sauvegardes");

        // Crée tous les dossiers nécessaires
        saveDir.mkdirs();

        // Nom du fichier (ajout .txt si absent)
        if (!outputName.toLowerCase().endsWith(".txt")) {
            outputName += ".txt";
        }

        // Fichier final
        File filePath = new File(saveDir, outputName);

        // S'assure que le dossier parent existe
        filePath.getParentFile().mkdirs();

        // Création du fichier si nécessaire
        filePath.createNewFile();



        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Algorithme utilisé : " + algo);
            if(!algo.equals("Dijkstra"))
                bw.write(" (" +heuristic.natureHeuristique() + ")");
            bw.newLine();
            bw.newLine();

            bw.write("Liste des sommets du plus court chemin trouvé :");
            bw.newLine();
            for (int v : path) {
                bw.write(String.valueOf(v));
                bw.newLine();
            }
        }
    }

    /**
     * Ouvre une fenêtre Swing contenant le Board.
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
     * Calcule le poids d'une arête entre deux sommets.
     * @param diagonal vrai si le déplacement est diagonal
     */
    private static double computeWeight(Graph graph, int source, int dest, boolean diagonal)
    {
        List<Vertex> vertices = graph.getVertexlist();
        double tA = vertices.get(source).getIndivTime();
        double tB = vertices.get(dest).getIndivTime();
        double base = (tA + tB) / 2.0;
        return diagonal ? base * Math.sqrt(2) : base;
    }

    /**
     * Point d'entrée de l'application.
     */
    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(GraphShortestPath::new);
    }
}
