package GUI;

import model.*;
import java.awt.*;
import javax.swing.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class TestInterface extends JPanel implements ModelListener {

  private BufferedImage image;
  private String pathImage;
  private HashMap<Integer,Image> hmap;
  private Board model;
  private int x = -1;
  private int y = -1;
  private int tuileSize;

  public TestInterface(Board model, int tuileSize,String pathImage) {

    this.model = model;
    this.pathImage = pathImage;
    this.tuileSize = tuileSize;

    try {
      File file = new File(pathImage);
      this.image = ImageIO.read(file);
    } catch (IOException e) {
      System.out.println(e);
    }

    this.hmap = new HashMap<>();
    for (int j = 0; j < this.model.getHeight(); j++){
      for (int i = 0; i < this.model.getWidth(); i++) {
        BufferedImage newv = this.image.getSubimage(i*200,j*200,200,200);
        Image im_lab = new ImageIcon(newv).getImage();
        this.hmap.put(j*this.model.getWidth() + i,im_lab);
      }
    }
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

      for (int j = 0; j < this.model.getHeight(); j++) {
        for (int i = 0; i < this.model.getWidth(); i++) {
              if (grid[j][i] instanceof FullTile) {
                Image im = this.hmap.get(((FullTile)grid[j][i]).getId());
                g.drawImage(im,size*i, size*j,size, size, this);
                g.drawRect(size*i, size*j,size, size);
              }
          }
      }
  }

  @Override
  public void update(Object src) {
      repaint();
  }
}
