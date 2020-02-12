
package model;

public class FullTile extends AbstractTile {

    private int id;
    
    public FullTile(int x, int y, int id) {
        super(x, y);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean isEmptyTile() {
        return false;
    }
    
}
