package model;

/**
	* Hérite de Tile.
	* Représente une pièce du taquin.
*/
public class FullTile extends Tile {

    private int id;

		/**
			* Constructeur de la classe.
			* @param x Abscisse de la case.
			* @param y Ordonnée de la case.
			* @param id Identifiant de la case.
		*/
    public FullTile(int x, int y, int id) {
        super(x,y);
        this.id = id;
    }

		/**
			* Constructeur sans argument.
		*/
		public FullTile(){
			this(-1,-1,0);
		}

		/**
			* Accesseur de l'identifiant.
			* @return L'attribut id.
		*/
		public int getId() {
			return this.id;
		}

		/**
			* Représentation console de la pièce.
			* @return La chaîne de caractères symbolisant la pièce.
		*/
    public String toString() {
        return "" + this.id;
    }
}
