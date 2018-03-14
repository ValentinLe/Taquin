package model;

import java.util.ArrayList;

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

		public void shuffle() {

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

			public ArrayList<ArrayList<Integer>> neighbours(int x, int y) {
				ArrayList<ArrayList<Integer>> listCoord = new ArrayList<>();
				ArrayList<Integer> temp=new ArrayList<Integer>();
				if (x < this.width - 1) {
					temp.add(x+1);
					temp.add(y);
					listCoord.add(temp);
					temp.clear();
				}
				if (x > 0) {
					temp.add(x-1);
					temp.add(y);
					listCoord.add(temp);
					temp.clear();
				}
				if (y < this.height - 1) {
					temp.add(x);
					temp.add(y+1);
					listCoord.add(temp);
					temp.clear();
				}
				if (y > 0) {
					temp.add(x);
					temp.add(y-1);
					listCoord.add(temp);
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
