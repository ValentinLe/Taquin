package model;

public class FullTile extends Tile {

    private int id;

    public FullTile(int x, int y, int id) {
        super(x,y);
        this.id = id;
    }

		public FullTile(){
			this(-1,-1,0);
		}

		public int getId() {
			return this.id;
		}

    public String toString() {
        return "" + this.id;
    }
}
