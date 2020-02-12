
package model;

public class EmptyTile extends AbstractTile {
    
    public EmptyTile(int x, int y, int id) {
	super(x, y, id);
    }
    
    /**
     * test pour savoir si  c'est une case vide ou non
     * @return true si c'est une case vide et false sinon
     */
    @Override
    public boolean isEmptyTile() {
        return true;
    }
    
}
