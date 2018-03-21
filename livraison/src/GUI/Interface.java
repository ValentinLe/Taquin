package GUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import model.*;

public class Interface extends JFrame {

    private Board b;
    private View game;
    private int tuileSize;

    public Interface(Board b) {
        this.b = b;
        this.tuileSize = 200;
        this.setTitle("Taquin");
        this.setResizable(false);

        this.game = new View(this.b,this.tuileSize);
        game.setPreferredSize(new Dimension(this.b.getWidth()*this.tuileSize+1,this.b.getHeight()*this.tuileSize+1));
        game.setBackground(Color.black);
        JLabel counter = new JLabel("Nombre de coups : ");
        counter.setBackground(Color.green);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        this.add(game,gc);
        gc.gridy = 1;
        this.add(counter,gc);
        
        addKeyListener(new KeyListener(){
           @Override
           public void keyPressed(KeyEvent e) {
               if (e.getKeyCode() == KeyEvent.VK_UP) {
                   System.out.println("haut");
               }
               if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                   System.out.println("bas");
               }
               if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                   System.out.println("gauche");
               }
               if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                   System.out.println("droit");
               }
           } 

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        
        game.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                x = Math.round(x/Interface.this.tuileSize);
                y = Math.round(y/Interface.this.tuileSize);
                Interface.this.game.setPosition(x,y);
                Interface.this.game.updateUI();
            }
            
        });
        
        pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


}
