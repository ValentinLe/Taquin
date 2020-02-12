
package model;

public abstract class AbstractTile {
    
    private int x;
    private int y;
    
    public AbstractTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public abstract boolean isEmptyTile();
    
    public String toString() {
	return "Tile(x=" + x + ", y=" + y + ")";
    }
    
}
