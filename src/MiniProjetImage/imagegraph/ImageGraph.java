/**
 * Classe ImageGraph
 * @author BALDE Asmaou
 **/

package MiniProjetImage.imagegraph;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Classe permettant de charger une image et de construire
 * une matrice d'intensités utilisable pour la création du graphe
 */
public class ImageGraph
{
    /**
     * Image source chargée depuis le fichier. Elle est conservée sous forme
     * de {@link BufferedImage} afin de permettre l'extraction des intensités
     * et l'affichage dans l'interface graphique.
     */
    private BufferedImage image;

    /**
     * Largeur de l'image en nombre de pixels
     */
    private int width;

    /**
     * Hauteur de l'image en nombre de pixels
     */
    private int height;

    /**
     * Matrice des intensités de l'image.
     * <p>
     * Chaque case contient une valeur comprise entre 0 et 255 représentant
     * l'intensité lumineuse du pixel correspondant. Cette matrice est utilisée
     * pour construire le graphe de l'image, où chaque pixel devient un sommet.
     * </p>
     */
    private int [][] intensities;


    /**
     * Charge une image depuis un fichier.
     * @param path chemin du fichier image
     * @throws Exception si l'image ne peut pas être lue
     */
    public ImageGraph(String path) throws Exception
    {
        image = ImageIO.read(new File(path));
        this.image = resizeImage(image, 400, 400);
        width = image.getWidth();
        height = image.getHeight();
        intensities = new int[height][width];
        extractIntensities();

    }


    /**
     * Convertit chaque pixel en intensité (0-255)
     * Pour une image couleur, on prendra la moyenne RGB
     */
    private void extractIntensities()
    {
        for(int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                Color c = new Color(image.getRGB(x, y));
                int intensity = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                intensities[y][x] = intensity;
            }
        }
    }


    /**
     * @return la largeur de l'image
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return la hauteur de l'image
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retourne l'image originale chargée depuis le fichier.
     * <p>
     * Cette méthode permet d'accéder directement à l'objet {@link BufferedImage}
     * utilisé pour extraire les intensités et pour l'affichage dans l'interface
     * graphique. L'image n'est pas modifiée par la classe : il s'agit de la
     * version brute telle qu'elle a été lue sur le disque.
     * </p>
     *
     * @return l'image source sous forme de {@code BufferedImage}
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Retourne la matrice des intensités de l'image.
     * <p>
     * Chaque case du tableau correspond à l'intensité lumineuse d'un pixel,
     * comprise entre 0 (noir) et 255 (blanc). Cette matrice est utilisée pour
     * construire le graphe de l'image : chaque pixel devient un sommet et la
     * différence d'intensité entre deux pixels voisins sert à pondérer les arêtes.
     * </p>
     *
     * @return une matrice d'entiers représentant l'intensité de chaque pixel
     *         sous la forme {@code intensities[y][x]}
     */
    public int[][] getIntensites() {
        return intensities;
    }


    /**
     * Redimensionne une image si elle dépasse une taille maximale donnée
     *
     * Cette méthode est utilisée pour éviter que des images trop grandes
     * ne provoquent des ralentissements importants lors de la construction
     * du graphe ou du calcul de Dijkstra.
     *
     * @param original  image d'origine à redimensionner
     * @param maxWidth  largeur maximale autorisée
     * @param maxHeight hauteur maximale autorisée
     * @return une nouvelle image redimensionnée, ou l'image originale
     */
    private BufferedImage resizeImage(BufferedImage original, int maxWidth, int maxHeight)
    {
        int width = original.getWidth();
        int height = original.getHeight();

        // Pas besoin de redimensionner
        if(width <= maxWidth && height <= maxHeight)
            return original;

        // Calcul du facteur d'échelle
        double scale =Math.min((double) maxWidth / (double) width, (double) maxHeight / height);
        int newWidth = (int) (width * scale);
        int newHeight = (int) (height * scale);

        // Redimensionnement avec filtrage
        Image tmp = original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resized;
    }
}
