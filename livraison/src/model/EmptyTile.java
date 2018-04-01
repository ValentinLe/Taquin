package model;

/**
	* Hérite de Tile.
	* Réprésente l'espace vide du taquin.
	*/
public class EmptyTile extends Tile {

		/**
			* Constructeur de la classe.
			* @param x Abscisse de la case.
			* @param y Ordonnée de la case.
		*/
    public EmptyTile(int x, int y) {
        super(x,y);
    }

		/**
			* Représentation console de l'espace vide.
			* @return La chaîne de caractères symbolisant l'espace vide. 
		*/
    public String toString() {
        return " ";
    }
}
