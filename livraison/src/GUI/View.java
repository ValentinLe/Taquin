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

public class View extends JPanel implements ModelListener {

  private BufferedImage image;
  private String pathImage;
  private HashMap<Integer,Image> hmap;
  private Board model;
  private int x = -1;
  private int y = -1;
  private int tileSize;
  private int imageSize;

  public View(Board model, int tileSize,String pathImage) {

    this.model = model;
    this.model.addListener(this);
    this.pathImage = pathImage;

    try {
      File file = new File(pathImage);
      this.image = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.hmap = new HashMap<>();
    this.tileSize = 200;
    this.imageSize = findImageSize(this.image);

    for (int j = 0; j < this.model.getHeight(); j++){
      for (int i = 0; i < this.model.getWidth(); i++) {
        BufferedImage newv = this.image.getSubimage(i*this.imageSize,j*this.imageSize,this.imageSize,this.imageSize);
        Image im_lab = new ImageIcon(newv).getImage();
        this.hmap.put(j*this.model.getWidth() + i,im_lab);
      }
    }

  }

  public void setPosition(int newX, int newY) {
      this.x = newX;
      this.y = newY;
  }

  public int findImageSize(BufferedImage im) {
    int width = im.getWidth();
    int height = im.getHeight();
    int x = this.model.getWidth();
    int y = this.model.getHeight();
    float lenX = width/x;
    float lenY = height/y;
    return Math.round(Math.min(lenX,lenY));
  }

  @Override
  public void paintComponent(Graphics g) {
      super.paintComponent(g);
      int size = this.tileSize;
      int emptyX = this.model.getEmptyTile().getX();
      int emptyY = this.model.getEmptyTile().getY();
      Tile[][] grid = this.model.getGrid();

      for (int j = 0; j < this.model.getHeight(); j++) {
        for (int i = 0; i < this.model.getWidth(); i++) {
              if (grid[j][i] instanceof FullTile) {
                Image im = this.hmap.get(((FullTile)grid[j][i]).getId());
                g.drawImage(im,size*i, size*j,size, size, this);
                g.drawRect(size*i, size*j,size, size);
              } else {
                if (this.model.isSolved()) {
                  Image im = this.hmap.get(j*this.model.getWidth() + i);
                  g.drawImage(im,size*i, size*j,size, size, this);
                  g.drawRect(size*i, size*j,size, size);
                }
              }
          }
      }
  }

  @Override
  public void update(Object src) {
      repaint();
  }
}
