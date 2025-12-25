/**
 * Classe ImagePanel
 * @author BALDE Asmaou
 * @see MiniProjetImage.ui.ControlPanel
 **/

package MiniProjetImage.ui;

import MiniProjetImage.imagegraph.ImageGraph;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Fenêtre principale de l'application.
 * <p>
 * Cette classe assemble l'interface graphique complète :
 * <ul>
 *     <li>Un panneau latéral contenant les contrôles et informations,</li>
 *     <li>Un panneau central affichant l'image et le chemin,</li>
 *     <li>Un sélecteur de fichier permettant de charger une image,</li>
 *     <li>Un bouton de réinitialisation.</li>
 * </ul>
 * </p>
 */
public class MainWindow extends JFrame {

    /** Panneau latéral contenant les boutons et informations. */
    private final ControlPanel controlPanel = new ControlPanel();

    /** Panneau d'affichage de l'image (créé après chargement). */
    private ImagePanel imagePanel = null;

    /**
     * Construit la fenêtre principale et initialise l'interface.
     */
    public MainWindow() {

        setTitle("Mini-Projet Image - Chemin le plus court");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Ajout du panneau latéral
        add(controlPanel, BorderLayout.WEST);

        // Action du bouton "Charger image"
        controlPanel.loadButton.addActionListener(e -> choisirImage());

        // Action du bouton "Réinitialiser"
        controlPanel.resetButton.addActionListener(e -> {
            if (imagePanel != null) {
                imagePanel.reset();
            }
        });

        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Ouvre un sélecteur de fichier pour charger une image.
     * <p>
     * Si une image est chargée avec succès, un nouveau {@link ImagePanel}
     * est créé et affiché dans la fenêtre.
     * </p>
     */
    private void choisirImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choisir une image");

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try {
                ImageGraph imgGraph = new ImageGraph(file.getAbsolutePath());

                // Retirer l'ancien panneau si nécessaire
                if (imagePanel != null) {
                    remove(imagePanel);
                }

                // Nouveau panneau d'image
                imagePanel = new ImagePanel(imgGraph, controlPanel);
                add(imagePanel, BorderLayout.CENTER);

                revalidate();
                repaint();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Impossible de charger l'image : " + ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
