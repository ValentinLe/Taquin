package GUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import model.*;

public class Interface extends JFrame {
    
    private Board b;
    private int tuileSize;
    
    public Interface(Board b) {
        this.b = b;
        this.tuileSize = 200;
        this.setTitle("Taquin");
        this.setResizable(false);
        
        
        
        View game = new View(this.b,this.tuileSize);
        game.setPreferredSize(new Dimension(this.b.getWidth()*this.tuileSize+1,this.b.getHeight()*this.tuileSize+1));
        JLabel counter = new JLabel("Nombre de coups joués : ");
        counter.setBackground(Color.green);
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        this.add(game,gc);
        gc.gridy = 1;
        this.add(counter,gc);
        pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    
}
