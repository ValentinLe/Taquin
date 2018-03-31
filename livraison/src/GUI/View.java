package GUI;

import java.awt.*;
import javax.swing.*;
import model.*;

public class View extends JPanel implements ModelListener {

    private Board model;
    private int x = -1;
    private int y = -1;
    private int tuileSize;

    public View(Board model, int tuileSize){
        this.model = model;
        this.tuileSize = tuileSize;
    }

    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = this.tuileSize;
        int emptyX = this.model.getEmptyTile().getX();
        int emptyY = this.model.getEmptyTile().getY();
        Tile[][] grid = this.model.getGrid();

        g.setFont(new Font("Monospace", Font.BOLD, 50));

        for (int j = 0; j < this.model.getHeight(); j++) {
          for (int i = 0; i < this.model.getWidth(); i++) {
                if (grid[j][i] instanceof FullTile) {
                    if (i == this.x && j == this.y) {
                        g.setColor(Color.orange);
                    } else {
                        g.setColor(Color.white);
                    }
                    g.fillRect(size*i, size*j,size, size);
                    g.setColor(Color.black);
                    g.drawRect(size*i, size*j,size, size);
                    g.drawString(Integer.toString(((FullTile)grid[j][i]).getId()),size*i+(size/2)-15, size*j+(size/2)+15);
                }
            }
        }
    }

    @Override
    public void update(Object src) {
        repaint();
    }

}
