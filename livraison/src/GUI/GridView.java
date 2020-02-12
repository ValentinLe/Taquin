
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import model.AbstractTile;
import model.Board;
import model.FullTile;

public class GridView extends JPanel {
    
    private Board board;
    private int tileSize;
    private String imageName;
    private BufferedImage image;
    private HashMap<Integer, Image> mapImages;
    private int imageSize;
    
    public GridView(Board board, int tileSize, String imageName) {
	this.board = board;
	this.tileSize = tileSize;
	this.imageName = imageName;
	this.loadImage(imageName);
	this.setBackground(Color.BLACK);
    }
    
    public void loadImage(String imagePath) {
	// laisser dans cet ordre l'image doit etre setter avant de trouver sa
	// dimention et ensuite la decoupee pour creer les sous-images et les stocker
	// dans la map
	this.setImage(imageName);
	this.imageSize = findImageSize(this.image);
	this.fillMapImages();
    }
    
    /**
     * Taille preferee du JPanel, celle qu'il aura dans la fenetre
     * @return la dimention preferee du JPanel
     */
    @Override
    public Dimension getPreferredSize() {
	return new Dimension(
		tileSize * this.board.getWidth(), 
		tileSize * this.board.getWidth()
	);
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
    * Trouve la bonne dimension pour découper l'image en prenant le minimum 
    * entre la hauteur et la largeur
    * @param im l'image dont il faut trouver la dimension
    * @return la taille de chaque case dans l'image
    */
    public int findImageSize(BufferedImage im) {
	int width = im.getWidth();
	int height = im.getHeight();
	int x = this.board.getWidth();
	int y = this.board.getHeight();
	float lenX = width/x;
	float lenY = height/y;
	return Math.round(Math.min(lenX,lenY));
    }
    
    /**
    * Decoupe l'image et construit un HashMap idTile : image
    */
    public void fillMapImages() {
	this.mapImages = new HashMap<>();

	for (int j = 0; j < this.board.getHeight(); j++){
	    for (int i = 0; i < this.board.getWidth(); i++) {
		// creation d'une image etant la partie de l'image de la case
		// en (i, j) et qui sera mis dans la map des images avec comme
		// cle l'identifiant de la case qui correspondera a cette image
		BufferedImage subImage = this.image.getSubimage(
			i * this.imageSize,
			j * this.imageSize,
			this.imageSize,
			this.imageSize
		);
		Image image = new ImageIcon(subImage).getImage();
		this.mapImages.put(j * this.board.getWidth() + i, image);
	    }
	}
    }
    
    /**
     * Dessine le taquin avec les images selon leur position dans le board
     * @param g c'est l'objet qui permet de dessiner sur le JPanel 
     * (cette fonction est appellee automatiquement par le repaint())
     */
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	
	boolean solved = this.board.isSolved();
	
	for (int j = 0; j < this.board.getHeight(); j++) {
	    for (int i = 0; i < this.board.getWidth(); i++) {
		AbstractTile tile = this.board.getTileAt(i, j);
		if (!tile.isEmptyTile() || solved) {
		    // on dessine toutes les cases mais la case vide est dessinee
		    // que lorsque le board est resolu
		    int idTile = tile.getId();
		    Image im = this.mapImages.get(idTile);
		    g.drawImage(im, i * tileSize, j * tileSize, tileSize, tileSize, this);
		}
	    }
	}
	
    }
}
