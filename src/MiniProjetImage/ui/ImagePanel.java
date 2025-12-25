/**
 * Classe ImagePanel
 * @author BALDE Asmaou
 * @see MiniProjetImage.imagegraph.GraphBuilder
 * @see MiniProjetImage.imagegraph.PixelNode
 * @see MiniProjetImage.ui.ControlPanel
 **/

package MiniProjetImage.ui;

import MiniProjetImage.imagegraph.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * Panneau principal affichant l'image et permettant à l'utilisateur
 * de sélectionner deux pixels (start et goal) afin de calculer
 * le plus court chemin entre eux via l'algorithme de Dijkstra.
 * <p>
 * Ce composant gère :
 * <ul>
 *     <li>l'affichage de l'image,</li>
 *     <li>la sélection des pixels par clic,</li>
 *     <li>le lancement de Dijkstra,</li>
 *     <li>le dessin du chemin trouvé,</li>
 *     <li>la mise à jour du panneau latéral {@link ControlPanel}.</li>
 * </ul>
 * </p>
 */
public class ImagePanel extends JPanel {

    /** Image affichée dans le panneau. */
    private final BufferedImage image;

    /** Graphe construit une seule fois à partir de l'image. */
    private final GraphBuilder graphBuilder;

    /** Liste d'adjacence du graphe. */
    private final Map<PixelNode, List<Edge>> graph;

    /** Panneau latéral contenant les informations. */
    private final ControlPanel controlPanel;

    /** Pixel de départ sélectionné par l'utilisateur. */
    private PixelNode startNode = null;

    /** Pixel d'arrivée sélectionné par l'utilisateur. */
    private PixelNode goalNode = null;

    /** Chemin calculé par Dijkstra. */
    private List<PixelNode> currentPath = null;

    /**
     * Construit un panneau d'affichage interactif pour une image donnée.
     *
     * @param imgGraph     image convertie en intensités
     * @param controlPanel panneau latéral
     */
    public ImagePanel(ImageGraph imgGraph, ControlPanel controlPanel) {

        this.controlPanel = controlPanel;
        this.image = imgGraph.getImage();

        // Construction du graphe une seule fois
        this.graphBuilder = new GraphBuilder(imgGraph);
        this.graph = graphBuilder.getAdjacencyList();

        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));

        // Gestion des clics souris
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    /**
     * Gère la sélection des pixels start et goal.
     *
     * @param x coordonnée x cliquée
     * @param y coordonnée y cliquée
     */
    private void handleClick(int x, int y) {

        if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
            return;
        }

        PixelNode clicked = graphBuilder.getNodeAt(x, y);

        if (startNode == null) {
            startNode = clicked;
            goalNode = null;
            currentPath = null;

            controlPanel.startLabel.setText("Start : (" + x + ", " + y + ")");
            controlPanel.goalLabel.setText("Goal : -");
            controlPanel.lengthLabel.setText("Longueur : -");
            controlPanel.exploredLabel.setText("Explorés : -");

        } else if (goalNode == null) {
            goalNode = clicked;
            controlPanel.goalLabel.setText("Goal : (" + x + ", " + y + ")");
            lancerDijkstra();

        } else {
            // Reset automatique si les deux sont déjà définis
            reset();
            startNode = clicked;
            controlPanel.startLabel.setText("Start : (" + x + ", " + y + ")");
        }

        repaint();
    }

    /**
     * Lance l'algorithme de Dijkstra et met à jour les informations du panneau latéral.
     */
    private void lancerDijkstra() {

        Map<PixelNode, PixelNode> previous =
                Dijkstra.compute(graph, startNode, goalNode);

        currentPath = Dijkstra.reconstructPath(previous, startNode, goalNode);

        controlPanel.lengthLabel.setText("Longueur : " + currentPath.size());
        controlPanel.exploredLabel.setText("Explorés : " + Dijkstra.getExploredCount());
    }

    /**
     * Réinitialise la sélection et les informations affichées.
     */
    public void reset() {
        startNode = null;
        goalNode = null;
        currentPath = null;

        controlPanel.startLabel.setText("Start : -");
        controlPanel.goalLabel.setText("Goal : -");
        controlPanel.lengthLabel.setText("Longueur : -");
        controlPanel.exploredLabel.setText("Explorés : -");

        repaint();
    }


/**
 * Redessine le contenu du panneau, incluant l'image, les points sélectionnés
 * (start et goal) ainsi que le chemin calculé par Dijkstra.
 * <p>
 * Cette méthode surcharge {@link javax.swing.JComponent#paintComponent(Graphics)}
 * afin d'assurer un rendu personnalisé :
 * </p>
 * <ul>
 *     <li>affichage de l'image source,</li>
 *     <li>dessin du pixel de départ en vert,</li>
 *     <li>dessin du pixel d'arrivée en rouge,</li>
 *     <li>visualisation du chemin sous forme de pixels jaunes,</li>
 *     <li>rafraîchissement automatique après chaque interaction utilisateur.</li>
 * </ul>
 * <p>
 *      Le rendu est entièrement recalculé à chaque appel, ce qui garantit que
 *      l'affichage reste cohérent après un clic, un reset ou un nouveau calcul
 *      de chemin.
 * </p>
 *
 * @param g contexte graphique utilisé pour dessiner les éléments du panneau
 */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null);

        // Dessin du start
        if (startNode != null) {
            g.setColor(Color.GREEN);
            g.fillOval(startNode.getX() - 3, startNode.getY() - 3, 7, 7);
        }

        // Dessin du goal
        if (goalNode != null) {
            g.setColor(Color.RED);
            g.fillOval(goalNode.getX() - 3, goalNode.getY() - 3, 7, 7);
        }

        // Dessin du chemin
        if (currentPath != null) {
            g.setColor(Color.YELLOW);
            for (PixelNode p : currentPath) {
                g.fillRect(p.getX(), p.getY(), 1, 1);
            }
        }
    }
}
