package GUI;

import java.awt.*;
import javax.swing.*;
import model.*;

public class View extends JPanel implements ModelListener {

    private Board model;
    private int tuileSize;

    public View(Board model, int tuileSize){
        this.model = model;
        this.tuileSize = tuileSize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = this.tuileSize;
        int emptyX = this.model.getEmptyTile().getX();
        int emptyY = this.model.getEmptyTile().getY();

        g.setFont(new Font("Monospace", Font.BOLD, 50));

        for (int i = 0; i < this.model.getWidth(); i++) {
            for (int j = 0; j < this.model.getHeight(); j++) {
                if (!(i == emptyX && j == emptyY)) {
                  g.setColor(Color.white);
                  g.fillRect(size*i, size*j,size, size);
                  g.setColor(Color.black);
                  g.drawRect(size*i, size*j,size, size);
                  g.drawString(Integer.toString(this.model.getWidth()*j + i + 1),size*i+(size/2)-15, size*j+(size/2)+15);
                }
            }
        }
    }

    @Override
    public void update(Object src) {
        repaint();
    }

}
