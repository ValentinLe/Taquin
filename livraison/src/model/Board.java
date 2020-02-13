
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import observer.AbstractListenableModel;

/**
 * represente le jeu du taquin, le board est un ecoutable donc il dira a ceux qui
 * l'ecoute quand il se passe quelque chose d'important (par exmple deplacement d'une
 * case), c'est nous qui choisissont quand leur dire en appellant firechange() et
 * ensuite eux (implementerons observer.ModelListener) appellerons leur fonction
 * update dans laquelle ils actualiserons ce qu'ils ont a actualiser
 */
public class Board extends AbstractListenableModel {
    
    // grille
    private AbstractTile[][] grid;
    // la largeur de la grille
    private int width;
    // la hauteur de la grille
    private int height;
    // on garde la case vide pour la retrouver plus facilement sans faire de 
    // parcours de grille
    private AbstractTile emptyTile;
    // compteur du nombre de coups effectues
    private int nbMoves;
    
    // nombre de deplacement aleatoire que le melange devra faire pour bien
    // melanger la grille
    private final int NB_MOVES_SHUFFLE = 10000;

    /**
     * constructeur
     * @param width la largeur de la grille
     * @param height la hauteur de la grille
     */
    public Board(int width, int height) {
	this.width = width;
	this.height = height;
	this.nbMoves = 0;
	
	// doit etre a la fin et respecter l'ordre suivant car la grid doit etre
	// generee avant de la melanger
	this.generateGrid();
	this.shuffle();
    }
    
    /**
     * Getter sur la largeur de la grille
     * @return la largeur de la grille
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter sur la hauteur de la grille
     * @return la hauteur de la grille
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Getter sur le nombre de coups joues
     * @return le nombre de coups joues
     */
    public int getNbMoves() {
        return nbMoves;
    }
    
    /**
     * Getter sur la case etant en (x, y)
     * @param x la coordonnee en x de la case
     * @param y la coordonnee en y de la case
     * @return la case etant en (x, y)
     */
    public AbstractTile getTileAt(int x, int y) {
	// le premier crochet correspond a la hauteur et le deuxieme a la largeur
	// faut faire gaffe a ce truc
       return this.grid[y][x];
    }
    
    /**
     * Setter d'une case en (x, y)
     * @param tile la case a placee
     * @param x la coordonnee en x de la case
     * @param y la coordonnee en y de la case
     */
    private void setTileAt(AbstractTile tile, int x, int y) {
	this.grid[y][x] = tile;
    }
    
    /**
     * creer la grille dans la variable en attribut de la classe 
     */
    private void generateGrid() {
       this.grid = new AbstractTile[this.height][this.width];
       for (int j = 0; j < this.height; j++) {
	    for (int i = 0; i < this.width; i++) {
		// on place des case pleines partout et apres les boucles on remplacera 
		// la case en bas a droite par une case vide
		this.setTileAt(
			new FullTile(i, j, j * this.width + i), 
			i, 
			j
		);
	    }
       }
       // ajout de la case vide en bas a droite
	int emptyX = this.width - 1;
	int emptyY = this.height - 1;
	int emptyId = emptyY * this.width + emptyX;
	AbstractTile empty = new EmptyTile(emptyX, emptyY, emptyId);
	this.setTileAt(empty, emptyX, emptyY);
	// on la stocke pour pouvoir la recuperer facilement dans les autres fonctions
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
    
    /**
     * Test si le taquin est resolu
     * @return true si le taquin est resolu
     */
    public boolean isSolved() {
        for (int j = 0; j < this.height; j++) {
           for (int i = 0; i < this.width; i++) {
               AbstractTile tile = this.getTileAt(i, j);
               int goodId = j * this.width + i;
               if (tile.getId() != goodId) {
		   // si l'id de la case n'est pas au bon endroit, elle est mal
		   // placee
		   return false;
               }
           }
       }
       return true;
    }
    
    /**
     * Donne la coordonnee en X de la case dans la direction donnee par rapport
     * a la case vide
     * @param dir la direction de la case dont on veut les coordonnes
     * @return la coordonnee en X de la case dans la direction souhaitee
     */
    private int getCoordXInDirectionFromEmptyTile(Direction dir) {
	return this.emptyTile.getX() + dir.getX();
    }
    
    /**
     * Donne la coordonnee en Y de la case dans la direction donnee par rapport
     * a la case vide
     * @param dir la direction de la case dont on veut les coordonnes
     * @return la coordonnee en Y de la case dans la direction souhaitee
     */
    private int getCoordYInDirectionFromEmptyTile(Direction dir) {
	return this.emptyTile.getY() + dir.getY();
    }
    
    /**
     * recupere la case dans la direction donnee par rapport a la case vide
     * @param dir la direction dans laquelle on veut la case
     * @return la case dans la direction souhaitee
     */
    private AbstractTile getTileInDirectionFromEmptyTile(Direction dir) {
	int destX = this.getCoordXInDirectionFromEmptyTile(dir);
	int destY = this.getCoordYInDirectionFromEmptyTile(dir);
	return this.getTileAt(destX, destY);
    }
    
    /**
     * test si on peut deplacee la case vide dans la direction donnee
     * @param dir la direction dans laquelle on veut deplacer la case vide
     * @return true si on peut deplacer la case vide dans cette direction false sinon
     */
    public boolean movePossible(Direction dir) {
	int destX = this.getCoordXInDirectionFromEmptyTile(dir);
	int destY = this.getCoordYInDirectionFromEmptyTile(dir);
	return this.isInIndex(destX, destY);
    }
    
    /**
     * deplace la case vide si c'est possible dans la direction donnee
     * @param dir la direction dans laquelle deplacer la case vide
     * @return true si la case a bien ete deplacee, false sinon (cela pourrait servir
     * dans certain cas de renvoyer ce booleen mais je ne l'ai pas utilise)
     */
    public boolean move(Direction dir) {
	return this.move(dir, true);
    }
    
    /**
     * deplace la case vide si c'est possible dans la direction donnee
     * @param dir la direction dans laquelle deplacer la case vide
     * @param countMove true si on doit compter le mouvement comme un coup
     * @return true si la case a bien ete deplacee, false sinon (cela pourrait servir
     * dans certain cas de renvoyer ce booleen mais je ne l'ai pas utilise)
     */
    public boolean move(Direction dir, boolean countMove){
	if (this.movePossible(dir)) {
	    AbstractTile destTile = this.getTileInDirectionFromEmptyTile(dir);
	    this.switchTile(this.emptyTile, destTile);
	    if (countMove) {
		this.nbMoves += 1;
	    }
	    return true;
	} else {
	    return false;
	}
    }
    
    /**
     * permutte les deux cases donnees
     * @param tile1 la premiere case a permutter
     * @param tile2 la seconde case a permutter
     */
    private void switchTile(AbstractTile tile1, AbstractTile tile2) {
	this.switchTile(tile1.getX(), tile1.getY(), tile2.getX(), tile2.getY());
    }
    
    /**
    * permutte deux cases de la grille aux coordonnees donnees
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
    
    /**
     * Donne tous les deplacements possibles de la case vide
     * @return la liste de tous les deplacements possibles de la case vide
     */
    public List<Direction> getMovesPossible() {
	List<Direction> movesPossible = new ArrayList<Direction>();
	for (Direction dir : Direction.values()) {
	    // pour chaque valeurs de l'enum Direction : UP, DOWN, ...
	    if (this.movePossible(dir)) {
		movesPossible.add(dir);
	    }
	}
	return movesPossible;
    }
    
    /**
     * Renvoi un coup aleatoire parmi les mouvements possible de la case vide 
     * @return une direction aleatoire parmi les mouvements possible de la case vide
     */
    public Direction getRandomMovePossible() {
	List<Direction> movesPossible = this.getMovesPossible();
	Random r = new Random();
	int randomIndice = r.nextInt(movesPossible.size());
	return movesPossible.get(randomIndice);
    }
    
    /**
     * permet de melanger la grille
     */
    public void shuffle() {
	// on garde en memoire la derniere direction (enfin l'oppose)
	Direction memory = null;
	// compteur pour s'assurer qu'on a bien fait le bon nombre de deplacement
	int nbRandomMoves = 0;
	while (nbRandomMoves < NB_MOVES_SHUFFLE) {
	    Direction randomDir = this.getRandomMovePossible();
	    if (memory == null || !randomDir.equals(memory)) {
		this.move(randomDir, false);
		// on garde en memoire l'oppose du coup qu'on vien de faire pour
		// ne pas faire a la prochaine iteration le coup inverse
		memory = randomDir.opposite();
		nbRandomMoves += 1;
	    }
	}
    }
    
    /**
     * remet le taquin a zero
     */
    public void restart() {
	this.nbMoves = 0;
	this.generateGrid();
	this.shuffle();
	// on dit aux listeners qu'il s'est passee quelque chose et donc il
	// fonts ce qu'ils ont a faire (par exemple s'actualiser pour le gui)
	// vient de observer.AbstractListenableModel qui est la classe abstrait
	// dont le Board herite
	this.fireChange();
    }
    
    /**
     * Joue le deplacement dans la direction donnee
     * @param dir la direction dans laquelle la case vide souhaite etre deplacee
     */
    public void playMove(Direction dir) {
	if (!this.isSolved()) {
	    this.move(dir);
	    // on dit aux listeners qu'il s'est passee quelque chose et donc il
	    // fonts ce qu'ils ont a faire (par exemple s'actualiser pour le gui)
	    // vient de observer.AbstractListenableModel qui est la classe abstrait
	    // dont le Board herite
	    this.fireChange();
	}
    }
    
    /**
     * permet l'affichage sous forme de caractere du board, la on represente
     * que la grille
     * @return la chaine de caratere representant le board
     */
    @Override
    public String toString() {
        String gridStr = "";
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
               AbstractTile tile = this.getTileAt(i, j);
		// pour la case vide on met un espace et pour les autres cases leur id
               if (tile.isEmptyTile()) {
		   gridStr += "  ";
               } else {
		   gridStr += tile.getId() + " ";
               }
           }
	    // fin de la premiere ligne
           gridStr += "\n";
        }
        return gridStr;
    }
    
    
}
