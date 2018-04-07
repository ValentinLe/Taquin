package model;

import java.util.ArrayList;
import java.util.Random;
import GUI.AbstractModeleEcoutable;

/**
	* Représentation du plateau de jeu.
	*/
public class Board extends AbstractModeleEcoutable {

		private int width;
		private int height;
		private Tile[][] grid;
		private EmptyTile empty_tile;
		private Direction memory;
		private int nb_moves;
		private boolean solving;

		/**
		* Enum symbolisant la direction de déplacement d'une case.
		*/
		public enum Direction {
			UP (0,-1),
			LEFT (-1,0),
			DOWN (0,1),
			RIGHT (1,0);

			private final int x;
			private final int y;


			Direction(int x, int y) {
				this.x=x;
				this.y=y;
			}

			/**
				* Accesseur permettant de récupérer les coordonnées d'une direction.
				* @return Le tableau des coordonnées lié à l'instance de l'enum.
			*/
			public ArrayList<Integer> getCoords() {
				ArrayList<Integer> l = new ArrayList<Integer>();
				l.add(this.x);
				l.add(this.y);
				return l;
			}

			/**
				* Retourne la direction opposée celle appelant cette méthode.
				* @return La direction opposée celle appelant cette méthode.
			*/
			public Direction opposite() {
				Direction res=UP;
				switch (this) {
					case UP:
						res=DOWN;
						break;
					case LEFT:
						res=RIGHT;
						break;
					case DOWN:
						res=UP;
						break;
					case RIGHT:
						res=LEFT;
						break;
				}
				return res;
			}
		}

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
			this.grid=new Tile[this.height][this.width];
			for (int j=0; j<this.height;j++){
				for (int i=0; i<this.width;i++){
					if (i*j<(this.width-1)*(this.height-1)){
						this.grid[j][i]=new FullTile(i,j,j*this.width+i);
					} else {
						this.empty_tile=new EmptyTile(i,j);
						this.grid[j][i]=this.empty_tile;
					}
				}
			}
		}

		/**
		 * Effectue un coup dans une direction aléatoire.
		*/
		public void randomMove() {
			Random gen = new Random();
			ArrayList<Direction> tab=this.neighbours(this.empty_tile.getX(), this.empty_tile.getY());
			tab.remove(this.memory);
			Direction nextMove = tab.get(gen.nextInt(tab.size()));
			this.memory=nextMove.opposite();
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
			for (int j=0; j<this.height;j++) {
				for (int i=0; i<this.width;i++) {
					if (this.grid[j][i] instanceof FullTile && ((FullTile)this.grid[j][i]).getId()!=j*this.width+i) {
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
			this.solving = true;
			while (!(this.isSolved())) {
					this.randomMove();
			}
			this.solving = false;
		}

		/**
			* Permute l'espace vide avec une pièce adjacente.
			* @param d La direction du mouvement.
		*/
		public void move(Direction d){
			int dest_x=this.empty_tile.getX()+d.getCoords().get(0);
			int dest_y=this.empty_tile.getY()+d.getCoords().get(1);
			if (dest_x>=0 && dest_x<this.width){
				if (dest_y>=0 && dest_y<this.height){
					this.nb_moves++;
					int xp=this.empty_tile.getX();
					int yp=this.empty_tile.getY();
					Tile tmp = new FullTile(xp,yp,((FullTile)this.grid[dest_y][dest_x]).getId());
					this.grid[dest_y][dest_x]=this.grid[this.empty_tile.getY()][this.empty_tile.getX()];
					this.grid[this.empty_tile.getY()][this.empty_tile.getX()]=tmp;
					this.empty_tile.setX(dest_x);
					this.empty_tile.setY(dest_y);
					fireChange();
					}
				}
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
			for (int j=0; j<this.height;j++) {
				for (int i=0; i<this.width;i++){
					ch+=this.grid[j][i].toString() + " ";
				}
					ch+="\n";
			}
			return ch;
		}
}
