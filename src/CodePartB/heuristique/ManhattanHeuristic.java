package CodePartB.heuristique;

/**
 * Heuristique de Manhattan (distance L1).
 * <p>
 * Adaptée aux déplacements en 4 directions (haut, bas, gauche, droite).
 * Elle calcule la distance comme la somme des différences absolues
 * entre les coordonnées des deux sommets.
 * </p>
 */
public class ManhattanHeuristic implements Heuristic
{
    @Override
    public double estimate(int current, int goal, int ncols)
    {
        int x1 = current / ncols;
        int y1 = current % ncols;

        int x2 = goal / ncols;
        int y2 = goal % ncols;

        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }


    /**
     * @return une chaine de caractères correspondante à l'heuristique
     */
    @Override
    public String natureHeuristique()
    {
        return "Manhattan";
    }

}
