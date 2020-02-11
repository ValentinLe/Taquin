package model;

import java.util.ArrayList;
import java.util.Random;
import GUI.AbstractModeleEcoutable;

/**
	* Représentation du plateau de jeu.
	*/
public class Board extends AbstractModeleEcoutable {

		// dimentions de la grille
		private int width;
		private int height;

		// grille
		private Tile[][] grid;

		// on garde un pointer sur la case vide pour la retrouver plus rapidement
		private EmptyTile empty_tile;

		// compteur de coups
		private int nb_moves;

		// boolean pour savoir si le jeu est fini sans avoir a rappeller isSolved
		// c'est une erreur de conception, il devrai etre dans une fonction mais pas
		// en attribut de la classe comme ca...
		private boolean solving;

		// ça c'etait pour garder en memoire le mouvement qui a ete effectue pour
		// eviter au randomMove de prendre la direction opposee de celle qu'il vient
		// de prendre
		private Direction memory;

		/**
			* Constructeur de la classe.
			* @param width Longueur du taquin.
			* @param height Largeur du taquin.
		*/
		public Board(int width, int height) {
				this.width = width;
				this.height = height;
				this.solving = false;
				this.createGrid();
				this.memory = Direction.DOWN;
		}

		/**
			* Accesseur du nombre de coups effectués.
			* @return L'attribut nb_moves.
		*/
		public int getMoveCount() {
			return this.nb_moves;
		}

		/**
			* Accesseur de la longueur du taquin.
			* @return L'attribut width.
		*/
		public int getWidth() {
			return this.width;
		}

		/**
			* Accesseur de la largeur du taquin.
			* @return L'attribut height.
		*/
		public int getHeight() {
			return this.height;
		}

		/**
			* Accesseur du booleen qui permet de savoir si le jeu est en résolution.
			* @return L'attribut solving.
		*/
		public boolean getSolving() {
			return this.solving;
		}

		/**
			* Accesseur de l'espace vide.
			* @return L'attribut empty_tile.
		*/
		public EmptyTile getEmptyTile() {
			return this.empty_tile;
		}

		/**
			* Accesseur de la grille de jeu.
			* @return L'attribut grid.
		*/
		public Tile[][] getGrid() {
			return this.grid;
		}

		/**
			*	Génère un plateau de jeu. Le puzzle est initialement résolu.
			* Les dimensions du taquin sont définies par les attributs width et height.
		*/
		public void createGrid() {
			this.grid = new Tile[this.height][this.width];
			// le j correspond a la coordonnee y et le i a la coordonnee x
			// faire une boucle qui parcours la largeur dans celle qui parcours la hauteur
			// c'est pour faire un parcours dans le sens de lecture de gauche a droite
			// et de haut en bas
			for (int j = 0; j < this.height; j++){
				for (int i = 0; i < this.width; i++){
					if (i * j < (this.width-1) * (this.height-1)){
						this.grid[j][i] = new FullTile(i,j,j*this.width+i);
					} else {
						// case vide sera placee en bas a gauche car c'est la seule case
						// ou i*j == (this.width-1) * (this.height-1)
						this.empty_tile = new EmptyTile(i,j);
						this.grid[j][i] = this.empty_tile;
					}
				}
			}
		}

		/**
		 * Effectue un coup dans une direction aléatoire.
		*/
		public void randomMove() {
			Random gen = new Random();
			ArrayList<Direction> tab = this.neighbours(this.empty_tile.getX(), this.empty_tile.getY());
			// on retire l'inverse du coup precedant
			tab.remove(this.memory);
			// on choisi un coup au hasard
			Direction nextMove = tab.get(gen.nextInt(tab.size()));
			// on stocke dans la memoire l'inverse du coup qu'on a choisi pour ne
			// pas le reprendre au tour d'apres
			this.memory = nextMove.opposite();
			this.move(nextMove);
		}

		/**
			* Mélange le taquin.
			* @param nb_iter Nombre de mouvements effectués pendant le mélange.
		*/
		public void shuffle(int nb_iter) {
			for (int i=1; i<nb_iter+1; i++) {
				this.randomMove();
			}
			ArrayList<Direction> tab=this.neighbours(this.empty_tile.getX(), this.empty_tile.getY());

			//Les deux boucles ci-dessous placent l'espace vide en bas à droite du taquin à la fin du mélange.
			while(tab.contains(Direction.DOWN)){
				this.move(Direction.DOWN);
				tab=this.neighbours(this.empty_tile.getX(), this.empty_tile.getY());
			}
			while(tab.contains(Direction.RIGHT)){
				this.move(Direction.RIGHT);
				tab=this.neighbours(this.empty_tile.getX(), this.empty_tile.getY());
			}
			this.nb_moves=0;
		}

		/**
			* Teste si la disposition du taquin est solution du puzzle.
			* @return Le résultat du test.
			*/
		public boolean isSolved() {
			for (int j = 0; j < this.height; j++) {
				for (int i = 0; i < this.width; i++) {
					if (this.grid[j][i] instanceof FullTile && ((FullTile)this.grid[j][i]).getId() != j*this.width+i) {
						// l'id est que sur les cases pleine et si l'id de la case ne correspond
						// pas a la numerotation de sa position j*largeur + i alors elle n'est pas
						// bien placee
						return false;
					}
				}
			}
			return true;
		}

		/**
			* Résout le puzzle de façon aléatoire.
		*/
		public void solve() {
			// solving sert pour bloquer le jeu quand il est en train de se resoudre
			this.solving = true;
			// resolution avec mouvement aleatoire pas optimale, il vaut mieux utiliser
			// A* avec la distance de manathan pour l'heuristique
			while (!(this.isSolved())) {
				this.randomMove();
			}
			this.solving = false;
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
			* Permute l'espace vide avec une pièce adjacente.
			* @param dir La direction du mouvement.
		*/
		public void move(Direction dir){
			int dest_x = this.empty_tile.getX() + dir.getX();
			int dest_y = this.empty_tile.getY() + dir.getY();
			if (this.isInIndex(dest_x, dest_y)) {
					int emptyX = this.empty_tile.getX();
					int emptyY = this.empty_tile.getY();
					this.switchTile(emptyX, emptyY, dest_x, dest_y);
					this.nb_moves++;
					// on dit aux listeners qu'il s'est passee quelque chose et donc il
					// fonts ce qu'ils ont a faire (par exemple s'actualiser pour le gui)
					fireChange();
				}
		}

			/**
			* permutte deux cases de la grille
			* @param t1x la coordonnee en X de la 1ere case
			* @param t1y la coordonnee en Y de la 1ere case
			* @param t2x la coordonnee en X de la 2eme case
			* @param t2y la coordonnee en Y de la 2eme case
			*/
			public void switchTile(int t1x, int t1y, int t2x, int t2y) {
				Tile temp = this.grid[t1y][t1x];

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
				* Calcule les directions de déplacement possibles de l'espace vide.
				* @param x L'abscisse de l'espace vide.
				* @param y L'ordonnée de l'espace vide.
				* @return Le tableau des directions de déplacement viables.
				*/
			public ArrayList<Direction> neighbours(int x, int y) {
				ArrayList<Direction> listCoord = new ArrayList<>();
				if (y < this.height - 1) {
					listCoord.add(Direction.DOWN);
				}
				if (y > 0) {
					listCoord.add(Direction.UP);
				}
				if (x < this.width - 1) {
					listCoord.add(Direction.RIGHT);
				}
				if (x > 0) {
					listCoord.add(Direction.LEFT);
				}
				return listCoord;
			}

		/**
			* Génère une représentation simple du taquin.
			* @return La chaîne de caractère correspondant au plateau de jeu.
			*/
		public String toString() {
			String ch="";
			for (int j = 0; j < this.height; j++) {
				for (int i = 0; i < this.width; i++){
					ch += this.grid[j][i].toString() + " ";
				}
				ch += "\n";
			}
			return ch;
		}
}
