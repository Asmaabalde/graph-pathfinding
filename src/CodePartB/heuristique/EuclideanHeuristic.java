package CodePartB.heuristique;

/**
 * Heuristique euclidienne (distance L2).
 * <p>
 * Adaptée aux déplacements en 8 directions. Elle correspond à la distance
 * géométrique classique entre deux points du plan.
 * </p>
 */
public class EuclideanHeuristic implements Heuristic
{
    @Override
    public double estimate(int current, int goal, int ncols)
    {
        int x1 = current / ncols;
        int y1 = current % ncols;

        int x2 = goal / ncols;
        int y2 = goal % ncols;

        int dx = x1 - x2;
        int dy = y1 - y2;

        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * @return une chaine de caractères correspondante à l'heuristique
     */
    @Override
    public String natureHeuristique() {
        return "Euclidean";
    }
}
