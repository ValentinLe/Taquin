package model;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    private int width;
    private int height;
		private Tile[][] grid;
		private EmptyTile empty_tile;

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
		}

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
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

		public void createGrid() {
			this.grid=new Tile[this.width][this.height];
			for (int i=0; i<this.width;i++){
				for (int j=0; j<this.height;j++){
					if (i*j<(this.width-1)*(this.height-1)){
						this.grid[i][j]=new FullTile(i,j,i*this.width+j);
					} else {
						this.empty_tile=new EmptyTile(i,j);
						this.grid[i][j]=this.empty_tile;
					}
				}
			}
		}

		public void randomMove() {
			Random gen = new Random();
			ArrayList<Direction> tab=this.neighbours(this.empty_tile.getX(), this.empty_tile.getY());
			this.move(tab.get(gen.nextInt(tab.size())));
		}

		public void shuffle(int nb_iter) {
			for (int i=1; i<nb_iter; i++) {
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
		}

		public boolean isSolved() {
			for (int i=0; i<this.width;i++) {
				for (int j=0; j<this.height;j++) {
					if (this.grid[i][j] instanceof FullTile && ((FullTile)this.grid[i][j]).getId()!=i*this.width+j) {
						return false;
					}
				}
			}
			return true;
		}

		public void solve() {
			while (!(this.isSolved())) {
					this.randomMove();
					System.out.println(this);
			}
			System.out.println("résolu");
		}

		public void move(Direction d){
			if (this.empty_tile.getX()+d.getCoords().get(0)<this.width){
				if (this.empty_tile.getY()+d.getCoords().get(1)<this.height){
					Tile tmp = new FullTile();
					int x=this.empty_tile.getX()+d.getCoords().get(0);
					int y=this.empty_tile.getY()+d.getCoords().get(1);
					tmp=this.grid[x][y];
					this.grid[x][y]=this.grid[this.empty_tile.getX()][this.empty_tile.getY()];
					this.grid[this.empty_tile.getX()][this.empty_tile.getY()]=tmp;
					}
				}
			}

			public ArrayList<Direction> neighbours(int x, int y) {
				ArrayList<Direction> listCoord = new ArrayList<>();
				if (x < this.width - 1) {
					listCoord.add(Direction.RIGHT);
				}
				if (x > 0) {
					listCoord.add(Direction.LEFT);
				}
				if (y < this.height - 1) {
					listCoord.add(Direction.DOWN);
					System.out.println(Direction.DOWN.getCoords());
				}
				if (y > 0) {
					listCoord.add(Direction.UP);
				}
				return listCoord;
			}

		public String toString() {
			String ch="";
			for (int i=0; i<this.width;i++){
				for (int j=0; j<this.height;j++) {
					ch+=this.grid[i][j].toString() + " ";
				}
					ch+="\n";
			}
			return ch;
		}
}
