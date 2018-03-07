package model;

public class EmptyTile extends Tile {
    
    public EmptyTile(int x, int y) {
        super(x,y);
    }
    
    public String toString() {
        return " ";
    }
}