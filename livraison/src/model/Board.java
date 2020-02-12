
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    
    private AbstractTile[][] grid;
    private int width;
    private int height;
    private AbstractTile emptyTile;
    private int nbMoves;

    public Board(int width, int height) {
	this.width = width;
	this.height = height;
	this.nbMoves = 0;
	
	// doit etre a la fin et respecter l'ordre suivant car la grid doit etre
	// generee avant de la melanger
	this.generateGrid(width, height);
	this.shuffle(10000);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public AbstractTile[][] getGrid() {
        return grid;
    }
    
    public int getNbMoves() {
        return nbMoves;
    }
    
    public AbstractTile getTileAt(int x, int y) {
        return this.grid[y][x];
    }
    
    
    private void generateGrid(int width, int height) {
        this.grid = new AbstractTile[this.height][this.width];
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                this.grid[j][i] = new FullTile(i, j, j * this.width + i);
            }
        }
        AbstractTile empty = new EmptyTile(this.width - 1, this.height - 1);
        this.grid[this.height - 1][this.width - 1] = empty;
        this.emptyTile = empty;
    }
    
    /**
    * test si la coordonnee (x,y) est dans la grille
    * @param x la coordonnee en x
    * @param y la coordonnee en y
    * @return true si la coordonnee (x,y) est dans la grille
    */
    public boolean isInIndex(int x, int y) {
        return 0 <= x && x < this.width && 0 <= y && y < this.height;
    }
    
    public boolean isSolved() {
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                AbstractTile tile = this.getTileAt(i, j);
                int goodId = j * this.width + i;
                if (!tile.isEmptyTile() && ((FullTile) tile).getId() != goodId) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private int getCoordXInDirectionFromEmptyTile(Direction dir) {
	return this.emptyTile.getX() + dir.getX();
    }
    
    private int getCoordYInDirectionFromEmptyTile(Direction dir) {
	return this.emptyTile.getY() + dir.getY();
    }
    
    private AbstractTile getTileInDirectionFromEmptyTile(Direction dir) {
	int destX = this.getCoordXInDirectionFromEmptyTile(dir);
	int destY = this.getCoordYInDirectionFromEmptyTile(dir);
	return this.getTileAt(destX, destY);
    }
    
    public boolean movePossible(Direction dir) {
	int destX = this.getCoordXInDirectionFromEmptyTile(dir);
	int destY = this.getCoordYInDirectionFromEmptyTile(dir);
	return this.isInIndex(destX, destY);
    }
    
    public void move(Direction dir){
	if (this.movePossible(dir)) {
	    AbstractTile destTile = this.getTileInDirectionFromEmptyTile(dir);
	    this.switchTile(this.emptyTile, destTile);
	    this.nbMoves += 1;
	    // on dit aux listeners qu'il s'est passee quelque chose et donc il
	    // fonts ce qu'ils ont a faire (par exemple s'actualiser pour le gui)
	}
    }
    
    private void switchTile(AbstractTile tile1, AbstractTile tile2) {
	this.switchTile(tile1.getX(), tile1.getY(), tile2.getX(), tile2.getY());
    }
    
    /**
    * permutte deux cases de la grille
    * @param t1x la coordonnee en X de la 1ere case
    * @param t1y la coordonnee en Y de la 1ere case
    * @param t2x la coordonnee en X de la 2eme case
    * @param t2y la coordonnee en Y de la 2eme case
    */
    private void switchTile(int t1x, int t1y, int t2x, int t2y) {
	AbstractTile temp = this.grid[t1y][t1x];
	
	this.grid[t1y][t1x] = this.grid[t2y][t2x];
	// on change les coordonnees de l'objet en (t1x, t1y)
	this.grid[t1y][t1x].setX(t1x);
	this.grid[t1y][t1x].setY(t1y);
	
	this.grid[t2y][t2x] = temp;
	// on change les coordonnees de l'objet en (t2x, t2y)
	this.grid[t2y][t2x].setX(t2x);
	this.grid[t2y][t2x].setY(t2y);
    
    }
    
    public List<Direction> getMovesPossible() {
	List<Direction> movesPossible = new ArrayList<Direction>();
	for (Direction dir : Direction.values()) {
	    if (this.movePossible(dir)) {
		movesPossible.add(dir);
	    }
	}
	return movesPossible;
    }
    
    public Direction getRandomMovePossible() {
	List<Direction> movesPossible = this.getMovesPossible();
	Random r = new Random();
	int randomIndice = r.nextInt(movesPossible.size());
	return movesPossible.get(randomIndice);
    }
    
    public void shuffle(int nbMovesShuffle) {
	Direction memory = null;
	int cpt = 0;
	while (cpt < nbMovesShuffle) {
	    Direction randomDir = this.getRandomMovePossible();
	    if (memory == null || !randomDir.equals(memory)) {
		this.move(randomDir);
		cpt += 1;
	    }
	}
    }

    @Override
    public String toString() {
        String gridStr = "";
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                AbstractTile tile = this.getTileAt(i, j);
                if (tile.isEmptyTile()) {
                    gridStr += "  ";
                } else {
                    gridStr += ((FullTile) tile).getId() + " ";
                }
            }
            gridStr += "\n";
        }
        return gridStr;
    }
    
    
}
