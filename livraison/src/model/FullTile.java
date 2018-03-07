package model;

public class FullTile extends Tile {
    
    private int id;
    
    public FullTile(int x, int y, int id) {
        super(x,y);
        this.id = id;
    }
    
    public String toString() {
        return "" + this.id;
    }
}