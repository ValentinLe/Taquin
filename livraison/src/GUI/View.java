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

/**
  * Classe représentant la zone jouable
  */
public class View extends JPanel implements ModelListener {

  private BufferedImage image;
  private String pathImage;
  private HashMap<Integer,Image> hmap;
  private Board model;
  private int x = -1;
  private int y = -1;
  private int tileSize;
  private int imageSize;

  /**
    * Constructeur de la Classe
    * @param model l'état de jeu
    * @param tileSize taille des cases en pixels
    * @param pathImage chemin de l'image à dessiner en fond
    */
  public View(Board model, int tileSize,String pathImage) {

    this.model = model;
    this.model.addListener(this);
    this.tileSize = tileSize;
    this.pathImage = pathImage;

    setImage(this.pathImage);

    createHashMap();
  }

  /**
    * Modifie les positions x et y qui correspond aux coordonnées de la souris en cases
    * @param newX la nouvelle coordonnée en X
    * @param newY la nouvelle coordonnée en Y
    */
  public void setPosition(int newX, int newY) {
      this.x = newX;
      this.y = newY;
  }

  /**
    * Trouve la bonne dimension pour découper l'image en prenant le minimum entre la hauteur et la largeur
    * @param im l'image dont il faut trouver la dimension
    * @return la taille de chaque case dans l'image
    */
  public int findImageSize(BufferedImage im) {
    int width = im.getWidth();
    int height = im.getHeight();
    int x = this.model.getWidth();
    int y = this.model.getHeight();
    float lenX = width/x;
    float lenY = height/y;
    return Math.round(Math.min(lenX,lenY));
  }

  /**
    * Charge une image
    * @param newPath chemin de l'image a charger
    */
  public void setImage(String newPath) {
    try {
      File file = new File(newPath);
      this.image = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
    * Découpe l'image et construit un HashMap id : image
    */
  public void createHashMap() {
    this.hmap = new HashMap<>();
    this.imageSize = findImageSize(this.image);

    for (int j = 0; j < this.model.getHeight(); j++){
      for (int i = 0; i < this.model.getWidth(); i++) {
        BufferedImage newv = this.image.getSubimage(i*this.imageSize,j*this.imageSize,this.imageSize,this.imageSize);
        Image im_lab = new ImageIcon(newv).getImage();
        this.hmap.put(j*this.model.getWidth() + i,im_lab);
      }
    }
  }

  /**
    * Dessine le visuel du jeu
    * @param g le graphique de JPanel
    */
  @Override
  public void paintComponent(Graphics g) {
      super.paintComponent(g);
      int size = this.tileSize;
      int emptyX = this.model.getEmptyTile().getX();
      int emptyY = this.model.getEmptyTile().getY();
      Tile[][] grid = this.model.getGrid();
      boolean solved = this.model.isSolved();

      for (int j = 0; j < this.model.getHeight(); j++) {
        for (int i = 0; i < this.model.getWidth(); i++) {
              if (grid[j][i] instanceof FullTile) {
                try {
                  Image im = this.hmap.get(((FullTile)grid[j][i]).getId());
                  g.drawImage(im,size*i, size*j,size, size, this);
                  if (!solved) {
                    if (this.x == i && this.y == j) {
                      Color couleurHover = new Color(255,255,255,30);
                      g.setColor(couleurHover);
                      g.fillRect(size*i, size*j,size, size);
                    }
                    g.setColor(Color.black);
                    g.drawRect(size*i, size*j,size, size);
                  }
                } catch (Exception e) {
                  continue;
                }
              } else {
                if (solved) {
                  Image im = this.hmap.get(j*this.model.getWidth() + i);
                  g.drawImage(im,size*i, size*j,size, size, this);
                }
              }
          }
      }
  }

  /**
    * Actualise la vue
    * @param src l'object à actualiser
    */
  @Override
  public void update(Object src) {
      repaint();
  }
}
