
package model;

/**
 * Classe abstraite representant une case
 */
public abstract class AbstractTile {
    
    private int x;
    private int y;
    private int id;
    
    /**
     * constructeur d'une case
     * @param x la coordonnee en x
     * @param y la coordonnee en y
     * @param id l'identifiant de la case
     */
    public AbstractTile(int x, int y, int id) {
	this.x = x;
	this.y = y;
	this.id = id;
    }
    
    /**
     * Setter sur la coordonnee X de la case
     * @param x la nouvelle coordonnee X de la case
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter sur la coordonnee Y de la case
     * @param y la nouvelle coordonnee Y de la case
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Getter sur la coordonnee X de la case
     * @return la coordonnee X de la case
     */
    public int getX() {
        return x;
    }

    /**
     * Getter sur la coordonnee Y de la case
     * @return la coordonnee Y de la case
     */
    public int getY() {
        return y;
    }
    
    /**
     * Getter sur l'identifiant de la case
     * @return l'identifiant de la case
     */
    public int getId() {
        return id;
    }
    
    /**
     * test pour savoir si  c'est une case vide ou non
     * @return true si c'est une case vide et false sinon
     */
    public abstract boolean isEmptyTile();
    
    /**
     * representation d'une case sous forme de string
     * @return la representation d'une case sous forme de string
     */
    public String toString() {
	return "Tile(x=" + x + ", y=" + y + ")";
    }
    
}
