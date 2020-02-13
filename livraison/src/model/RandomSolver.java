
package model;

/**
 * solver aleatoire, resoud le taquin aleatoirement
 */
public class RandomSolver implements Solver {
    
    /**
     * resoud le taquin de maniere aleatoire, tant qu'on a pas la solution, on
     * joue un coup aleatoire
     * @param board le board que le solver doit resoudre
     */
    public void solve(Board board) {
	Direction memory = null;
	// comme pour le shuffle du board on garde en memoire ce qu'on a jouer
	// precedemment pour ne pas prendre le coup inverse
	while(!board.isSolved()) {
	    Direction randomDir = board.getRandomMovePossible();
	    if (memory == null ||!randomDir.equals(memory)) {
		board.move(randomDir);
		memory = randomDir.opposite();
	    }
	}
    }
}
