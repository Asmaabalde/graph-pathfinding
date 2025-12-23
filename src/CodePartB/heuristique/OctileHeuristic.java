package CodePartB.heuristique;

/**
 * Heuristique d'Octile.
 * <p>
 * Adaptée aux déplacements en 8 directions lorsque les diagonales coûtent √2.
 * Elle combine la distance de Manhattan et la distance diagonale pour fournir
 * une estimation plus précise que l'euclidienne dans les grilles pondérées.
 * </p>
 */
public class OctileHeuristic implements Heuristic
{
    @Override
    public double estimate(int current, int goal, int ncols)
    {
        int x1 = current / ncols;
        int y1 = current % ncols;

        int x2 = goal / ncols;
        int y2 = goal % ncols;

        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);

        return (dx + dy) + (Math.sqrt(2) -2) * Math.min(dx, dy);
    }


    /**
     * @return une chaine de caractères correspondante à l'heuristique
     */
    @Override
    public String natureHeuristique() {
        return "Octile";
    }
}
