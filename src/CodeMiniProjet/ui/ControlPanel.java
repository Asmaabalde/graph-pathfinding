/**
 * Classe ControlPanel
 * @author BALDE Asmaou
 **/

package CodeMiniProjet.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau latéral affichant les informations relatives au chemin
 * ainsi que les contrôles de l'application (chargement, réinitialisation).
 * <p>
 * Ce panneau est mis à jour dynamiquement par {@link ImagePanel}
 * lorsque l'utilisateur sélectionne des pixels ou lance Dijkstra.
 * </p>
 */
public class ControlPanel extends JPanel {

    /** Bouton permettant de charger une nouvelle image. */
    public final JButton loadButton = new JButton("Charger une image");

    /** Bouton permettant de réinitialiser la sélection et le chemin. */
    public final JButton resetButton = new JButton("Réinitialiser");

    /** Labels d'information mis à jour par ImagePanel. */
    public final JLabel startLabel = new JLabel("Start : -");
    public final JLabel goalLabel = new JLabel("Goal : -");
    public final JLabel lengthLabel = new JLabel("Longueur : -");
    public final JLabel exploredLabel = new JLabel("Explorés : -");

    /**
     * Construit le panneau latéral avec une disposition verticale.
     */
    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(loadButton);
        add(Box.createVerticalStrut(10));
        add(resetButton);
        add(Box.createVerticalStrut(20));

        add(startLabel);
        add(goalLabel);
        add(lengthLabel);
        add(exploredLabel);
    }
}
