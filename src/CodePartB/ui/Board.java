package CodePartB.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serial;
import java.util.HashMap;
import java.util.LinkedList;
import CodePartB.graph.*;

public class Board extends JComponent
{
    @Serial
    private static final long serialVersionUID = 1L;

    private Graph graph;
    private final int pixelSize;
    private final int ncols;
    private final int nlines;
    private final HashMap<Integer, String> colors;
    private final int start;
    private final int end;

    /** Heatmap : ordre d’exploration */
    private int[] explorationOrder;
    private int explorationStep = 0;

    /** Animation du chemin final */
    private boolean animatePath = false;
    private int pathAnimationIndex = 0;

    /** Sommet courant */
    private int current = -1;

    /** Chemin final */
    private LinkedList<Integer> path = null;

    public Board(Graph graph, int pixelSize, int ncols, int nlines,
                 HashMap<Integer, String> colors, int start, int end)
    {
        this.graph = graph;
        this.pixelSize = pixelSize;
        this.ncols = ncols;
        this.nlines = nlines;
        this.colors = colors;
        this.start = start;
        this.end = end;
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond
        g2.setColor(Color.cyan);
        g2.fill(new Rectangle2D.Double(0, 0,
                this.ncols * this.pixelSize,
                this.nlines * this.pixelSize));

        // --- 1) DESSIN DES CASES ---
        int num = 0;
        for (Vertex v : graph.getVertexlist())
        {
            int i = num / ncols;
            int j = num % ncols;

            String c = colors.get((int) v.getIndivTime());
            switch (c) {
                case "green" -> g2.setPaint(Color.green);
                case "gray" -> g2.setPaint(Color.gray);
                case "blue" -> g2.setPaint(Color.blue);
                case "yellow" -> g2.setPaint(Color.yellow);
            }

            g2.fill(new Rectangle2D.Double(
                    j * pixelSize, i * pixelSize,
                    pixelSize, pixelSize));

            num++;
        }

        // --- 2) HEATMAP DOUCE ---
        if (explorationOrder != null)
        {
            for (int k = 0; k < explorationOrder.length; k++)
            {
                int step = explorationOrder[k];
                if (step == 0) continue;

                float intensity = Math.min(1f, step / (float) explorationStep);

                int i = k / ncols;
                int j = k % ncols;

                g2.setPaint(new Color(intensity, 0f, 0f, 0.35f));
                g2.fill(new Rectangle2D.Double(
                        j * pixelSize, i * pixelSize,
                        pixelSize, pixelSize));
            }
        }

        // --- 3) POINT COURANT ---
        if (current != -1)
        {
            int i = current / ncols;
            int j = current % ncols;

            g2.setPaint(Color.red);
            g2.fill(new Ellipse2D.Double(
                    j * pixelSize + pixelSize / 2.0 - 3,
                    i * pixelSize + pixelSize / 2.0 - 3,
                    6, 6));
        }

        // --- 4) DÉPART & ARRIVÉE ---
        int si = start / ncols, sj = start % ncols;
        g2.setPaint(Color.white);
        g2.fill(new Ellipse2D.Double(
                sj * pixelSize + pixelSize / 2.0 - 2,
                si * pixelSize + pixelSize / 2.0 - 2,
                4, 4));

        int ei = end / ncols, ej = end % ncols;
        g2.setPaint(Color.black);
        g2.fill(new Ellipse2D.Double(
                ej * pixelSize + pixelSize / 2.0 - 2,
                ei * pixelSize + pixelSize / 2.0 - 2,
                4, 4));

        // --- 5) CHEMIN FINAL ANIMÉ ---
        if (path != null)
        {
            g2.setStroke(new BasicStroke(3f));
            g2.setPaint(Color.red);

            int limit = animatePath ? pathAnimationIndex : path.size() - 1;

            for (int k = 0; k < limit; k++)
            {
                int a = path.get(k);
                int b = path.get(k + 1);

                int ai = a / ncols, aj = a % ncols;
                int bi = b / ncols, bj = b % ncols;

                g2.draw(new Line2D.Double(
                        aj * pixelSize + pixelSize / 2.0,
                        ai * pixelSize + pixelSize / 2.0,
                        bj * pixelSize + pixelSize / 2.0,
                        bi * pixelSize + pixelSize / 2.0));
            }
        }
    }

    // --- MISE À JOUR EXPLORATION ---
    public void update(Graph graph, int current)
    {
        this.graph = graph;
        this.current = current;

        if (explorationOrder == null)
            explorationOrder = new int[graph.getVertexlist().size()];

        explorationOrder[current] = explorationStep++;
        repaint();
    }

    // --- ANIMATION DU CHEMIN FINAL ---
    public void addPath(Graph graph, LinkedList<Integer> path)
    {
        this.graph = graph;
        this.path = path;
        this.current = -1;

        animatePath = true;
        pathAnimationIndex = 0;

        new Thread(() -> {
            try {
                for (int i = 0; i < path.size(); i++) {
                    pathAnimationIndex = i;
                    repaint();
                    Thread.sleep(30);
                }
            } catch (InterruptedException ignored) {}
        }).start();
    }
}
