package GUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import model.*;

public class Interface extends JFrame {

    private Board b;
    private TestInterface game;
    private JLabel counter;
    private int tuileSize;

    public Interface(Board b, String path) {
        this.b = b;
        this.b.shuffle(10000);
        this.tuileSize = 200;
        this.setTitle("Taquin");
        this.setResizable(false);

        this.game = new TestInterface(this.b,this.tuileSize,path);
        game.setPreferredSize(new Dimension(this.b.getWidth()*this.tuileSize+1,this.b.getHeight()*this.tuileSize+1));
        game.setBackground(Color.black);
        this.counter = new JLabel("Nombre de coups : " + this.b.getMoveCount());

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
               if (!Interface.this.b.isSolved()) {
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
                    Interface.this.game.update(Interface.this.game);
                    Interface.this.counter.setText("Nombre de coups : " + Interface.this.b.getMoveCount());
                }
               if (Interface.this.b.isSolved()) {
                        int askRestart = JOptionPane.showConfirmDialog (null, "Do you want to restart ?","End",JOptionPane.YES_NO_OPTION);
                        if (askRestart == JOptionPane.YES_OPTION) {
                            Interface.this.b.shuffle(10000);
                        } else {
                            Interface.this.dispose();
                        }
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
                int x = e.getX();
                int y = e.getY();
                x = Math.round(x/Interface.this.tuileSize);
                y = Math.round(y/Interface.this.tuileSize);
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
                            Interface.this.game.update(Interface.this.game);
                            Interface.this.counter.setText("Nombre de coups : " + Interface.this.b.getMoveCount());
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
                x = Math.round(x/Interface.this.tuileSize);
                y = Math.round(y/Interface.this.tuileSize);
                Interface.this.game.setPosition(x,y);
                Interface.this.game.update(Interface.this.game);
            }

        });

        pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
