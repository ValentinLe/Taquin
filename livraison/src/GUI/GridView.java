
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Board;

public class GridView extends JPanel {
    
    private Board board;
    private int tileSize;
    private String imageName;
    
    public GridView(Board board, int tileSize, String imageName) {
	this.board = board;
	this.tileSize = tileSize;
	this.imageName = imageName;
    }
    
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
    }
}
