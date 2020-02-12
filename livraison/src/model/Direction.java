
package model;

/**
* Enum symbolisant la direction de déplacement d'une case.
*/
public enum Direction {
    
    // chaque "variable" UP, DOWN, ... creer un objet de type Direction avec les
    // valeur dans les parentheses qui sera appeller de l'exterieur par Direction.UP
    UP (0,-1),
    LEFT (-1,0),
    DOWN (0,1),
    RIGHT (1,0);

    private final int x;
    private final int y;


    Direction(int x, int y) {
    	this.x = x;
    	this.y = y;
    }

    /**
     * permet de recuperer la coordonnee en X de la direction
     * @return la coordonnee en X de la direction
     */
    public int getX() {
	return this.x;
    }

    /**
     * permet de recuperer la coordonnee en Y de la direction
     * @return la coordonnee en Y de la direction
     */
    public int getY() {
	return this.y;
    }

    /**
    * Retourne la direction opposée celle appelant cette méthode.
    * @return La direction opposée celle appelant cette méthode.
    */
    public Direction opposite() {
	switch (this) {
	    case UP:
		return DOWN;
	    case LEFT:
		return RIGHT;
	    case DOWN:
		return UP;
	    case RIGHT:
		return LEFT;
	    }
	    return null;
    }

    public boolean equals(Direction other) {
	return this.x == other.getX() && this.y == other.getY();
    }
}
