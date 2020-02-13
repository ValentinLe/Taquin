
package model;

/**
 * Interface des resolveurs de taquin 
 */
public interface Solver {
    
    /**
     * resoud le taquin
     * @param board le board que le solver doit resoudre
     */
    public void solve(Board board);
}
