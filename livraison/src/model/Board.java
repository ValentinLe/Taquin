package model;

public class Board {

    private int width;
    private int height;
		public Tile[][] grid;

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

		public void createGrid() {
			this.grid=new Tile[width][height];
			for (int i=0; i==this.width-1;i++){
				for (int j=0; j==this.height-1;j++){
					if (i*j<=width*height-1){
						this.grid[i][j]=new FullTile(i,j,i*width+j);
					}else{
						this.grid[i][j]=new EmptyTile(i,j);
					}
				}
			}
		}

		public String toString() {
			String ch="";
			for (int i=0; i==this.width-1;i++){
				for (int j=0; j==this.height-1;j++) {
					ch+=this.grid[i][j].toString() + " ";
				}
					ch+="\n";
			}
			return ch;
		}
}
