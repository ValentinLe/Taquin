package model;

/**
 	*Représentation d'un élément du taquin.
	*/
public abstract class Tile {

    private int x;
    private int y;

		/**
			* Constructeur de la classe.
			* @param x Abscisse de la case.
			* @param y Ordonnée de la case.
			*/
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

		/**
			* Accesseur de l'abcisse de l'élément.
			* @return L'attribut x.
		*/
    public int getX() {
        return this.x;
    }

		/**
			* Accesseur de l'ordonnée de l'élément.
			* @return L'attribut y.
		*/
    public int getY() {
        return this.y;
    }

		/**
			* Mutateur de l'abcisse de l'élément.
			* @param La nouvelle abscisse.
		*/
    public void setX(int newX) {
        this.x = newX;
    }

		/**
			* Mutateur de l'ordonée de l'élément.
			* @param La nouvelle ordonnée.
		*/
    public void setY(int newY) {
        this.y = newY;
    }
}
