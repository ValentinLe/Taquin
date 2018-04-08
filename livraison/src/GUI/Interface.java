package GUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import model.*;
import java.util.Scanner;
import java.io.*;

/**
  * Classe de l'interface de jeu
  */
public class Interface extends JFrame {

    private Board b;
    private View game;
    private JLabel counter;
    private Timer timer;
    private int tileSize;

    /**
      * Constructeur de l'Interface
      * @param b l'état de jeu
      * @param path le chemin d'une image de fond
      */
    public Interface(Board b, String path) {
        this.b = b;
        this.b.shuffle(10000);
        this.tileSize = 200;
        this.setTitle("Taquin");
        this.setResizable(false);

        // placement de la vue
        this.game = new View(this.b,this.tileSize,path);
        game.setPreferredSize(new Dimension(this.b.getWidth()*this.tileSize+1,this.b.getHeight()*this.tileSize+1));
        game.setBackground(Color.black);
        JPanel zoneBot = new JPanel();

        JButton bRestart = new JButton("Recommencer");
        bRestart.setFocusable(false);
        bRestart.addActionListener(new ActionListener () {
          @Override
          public void actionPerformed(ActionEvent e){
            if (!Interface.this.b.getSolving()) {
              Interface.this.b.shuffle(10000);
              Interface.this.counter.setText("Nombre de coups : " + Interface.this.b.getMoveCount());
              Interface.this.counter.updateUI();
            }
          }
        });

        this.counter = new JLabel("Nombre de coups : " + this.b.getMoveCount());

        JButton bSolve = new JButton("Resolution");
        bSolve.setFocusable(false);
        bSolve.addActionListener(new ActionListener () {
          @Override
          public void actionPerformed(ActionEvent e){
            Interface.this.b.solve();
            Interface.this.counter.setText("Nombre de coups : " + Interface.this.b.getMoveCount());
            Interface.this.counter.updateUI();
          }
        });

        // gestion du placement des éléments
        zoneBot.setLayout(new GridLayout(1,3,20,20));
        zoneBot.add(bRestart);
        zoneBot.add(counter);
        zoneBot.add(bSolve);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        this.add(game,gc);
        gc.gridy = 1;
        this.add(zoneBot,gc);

        // ajout des listener d'events
        addKeyListener(new KeyListener(){
           @Override
           public void keyPressed(KeyEvent e) {
               if (!Interface.this.b.isSolved() && !Interface.this.b.getSolving()) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        Interface.this.b.move(Board.Direction.UP);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        Interface.this.b.move(Board.Direction.DOWN);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        Interface.this.b.move(Board.Direction.LEFT);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        Interface.this.b.move(Board.Direction.RIGHT);
                    }
                    Interface.this.counter.setText("Nombre de coups : " + Interface.this.b.getMoveCount());
                    Interface.this.counter.updateUI();
                }
           }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        game.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
              if (!Interface.this.b.isSolved() && !Interface.this.b.getSolving()) {
                int x = e.getX();
                int y = e.getY();
                x = Math.round(x/Interface.this.tileSize);
                y = Math.round(y/Interface.this.tileSize);
                Tile[][] grid = Interface.this.b.getGrid();
                if (grid[y][x] instanceof FullTile) {
                  EmptyTile empty = Interface.this.b.getEmptyTile();
                  int emptyX = empty.getX();
                  int emptyY = empty.getY();
                  ArrayList<Board.Direction> listNeighbours = Interface.this.b.neighbours(emptyX,emptyY);
                  ArrayList<Integer> coordEmpty = new ArrayList<>();
                  coordEmpty.add(x-emptyX);
                  coordEmpty.add(y-emptyY);
                  for (Board.Direction dir : listNeighbours) {
                    if (dir.getCoords().equals(coordEmpty)) {
                      Interface.this.b.move(dir);
                      Interface.this.counter.setText("Nombre de coups : " + Interface.this.b.getMoveCount());
                      Interface.this.counter.updateUI();
                    }
                  }
                }
              }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
              Interface.this.game.setPosition(-1,-1);
              Interface.this.game.update(Interface.this.game);
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
                x = Math.round(x/Interface.this.tileSize);
                y = Math.round(y/Interface.this.tileSize);
                Interface.this.game.setPosition(x,y);
                Interface.this.game.update(Interface.this.game);
            }

        });

        // parametres de la frame
        pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
