
package model;

public class EmptyTile extends AbstractTile {

    public EmptyTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isEmptyTile() {
        return true;
    }
    
}
