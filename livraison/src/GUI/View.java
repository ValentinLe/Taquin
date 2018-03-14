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
        
        for (int i = 0; i < this.model.getWidth(); i++) {
            for (int j = 0; j < this.model.getHeight(); j++) {
                g.drawRect(this.tuileSize*i, this.tuileSize*j,this.tuileSize, this.tuileSize);
                if ((i == this.model.getEmptyTile().getX()) && (j == this.model.getEmptyTile().getY())) {
                    g.setColor(Color.black);
                    g.fillRect(this.tuileSize*i, this.tuileSize*j,this.tuileSize, this.tuileSize);
                } else {
                    g.drawString(Integer.toString(this.model.getWidth()*j + i + 1),this.tuileSize*i+(this.tuileSize/2), this.tuileSize*j+(this.tuileSize/2));
                }
            }
        }
    }
    
    @Override
    public void update(Object src) {
        repaint();
    }
    
}
