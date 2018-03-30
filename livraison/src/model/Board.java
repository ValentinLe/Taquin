package model;

import java.util.ArrayList;
import java.util.Random;

public class Board {

		private int width;
		private int height;
		private Tile[][] grid;
		private EmptyTile empty_tile;
		private Direction memory;
		private int nb_moves;

		public enum Direction {
			UP (-1,0),
			LEFT (0,-1),
			DOWN (1,0),
			RIGHT (0,1);

			private final int x;
			private final int y;


			Direction(int x, int y) {
				this.x=x;
				this.y=y;
			}

			public ArrayList<Integer> getCoords() {
				ArrayList<Integer> l = new ArrayList<Integer>();
				l.add(this.x);
				l.add(this.y);
				return l;
			}

			public Direction opposite() {
				Direction res= UP;
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

		public Board(int width, int height) {
				this.width = width;
				this.height = height;
				this.memory = Direction.DOWN;
		}

		public int getMoveCount() {
			return this.nb_moves;
		}

		public int getWidth() {
			return this.width;
		}

		public int getHeight() {
			return this.height;
		}

		public EmptyTile getEmptyTile() {
			return this.empty_tile;
		}

		public Tile[][] getGrid() {
			return this.grid;
		}

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

		public void randomMove() {
			Random gen = new Random();
			ArrayList<Direction> tab=this.neighbours(this.empty_tile.getX(), this.empty_tile.getY());
			tab.remove(this.memory);
			Direction nextMove = tab.get(gen.nextInt(tab.size()));
			this.memory=nextMove.opposite();
			this.move(nextMove);
		}

		public void shuffle(int nb_iter) {
			for (int i=1; i<nb_iter+1; i++) {
				this.randomMove();
			}
			ArrayList<Direction> tab=this.neighbours(this.empty_tile.getX(), this.empty_tile.getY());
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

		public void solve() {
			while (!(this.isSolved())) {
					this.randomMove();
			}
		}

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
					}
				}
			}

			public ArrayList<Direction> neighbours(int x, int y) {
				ArrayList<Direction> listCoord = new ArrayList<>();
				if (y < this.height -1) {
					listCoord.add(Direction.RIGHT);
				}
				if (y > 0) {
					listCoord.add(Direction.LEFT);
				}
				if (x < this.width -1) {
					listCoord.add(Direction.DOWN);
				}
				if (x > 0) {
					listCoord.add(Direction.UP);
				}
				return listCoord;
			}

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
