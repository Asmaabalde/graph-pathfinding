package CodePartB.heuristique;

/** * Interface représentant une heuristique utilisée par l'algorithme A*.
 * <p>
 *    Une heuristique fournit une estimation du coût restant entre un sommet
 *    courant et le sommet d'arrivée. Elle doit être admissible (ne jamais surestimer le coût réel) pour garantir l'optimalité d'A*.
 * </p>
 */
public interface Heuristic
{
    /**
     * Estime la distance entre un sommet courant et le sommet d'arrivée.
     *
     * @param current index du sommet courant
     * @param goal index du sommet d'arrivée
     * @param ncols nombre de colonnes de la grille (pour convertir index en coordonnées)
     * @return estimation du coût restant
     */
    double estimate(int current, int goal, int ncols);

    String natureHeuristique();
}
