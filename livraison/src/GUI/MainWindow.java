
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Board;
import model.Direction;
import observer.ModelListener;

/**
 * implemente observer.ModelListener donc des que le board (model) dira a ses listeners
 * qu'il s'est passe quelque chose (listener dont MainWindow fait partie voir ligne 36),
 * dans ce cas la fonction update sera appellee
 */
public class MainWindow extends JFrame implements ModelListener {
    
    // on les mets en attributs pour y acceder dans toutes les fonctions et classes
    // anonymes
    private Board board;
    private int tileSize;
    
    public MainWindow() {
	
	this.board = new Board(3, 3);
	// ajout au board du fait que MainWindow va ecouter le board et des que
	// le board fera un firechange() la methode update de MainWindow (voir plus bas)
	// sera executee et c'est dans cette fonction qu'on change ce qu'on veut
	// comme changer le text d'un label ou autre composants ou sinon comme un bourin 
	// actualiser la fenetre dans sa globalite
	// pour l'instant j'ai mis que la fenetre principale en listener mais on
	// peut mettre aussi GridView avec dans son update un this.repaint()
	// du coup dans le update de MainWindow on retirera le this.repaint() et
	// on mettera a jour que les composants qui sont necessaires genre je
	// vais mettre le compteur de coup dans un JLabel et du coup dans le update
	// je vais modifier son texte et je vais repaint le JLabel
	board.addModelListener(this);
	// taille d'une sous-image
	this.tileSize = 200;
	
	// COMPOSANTS
	
	GridView view = new GridView(board, tileSize, "src/ressources/espace.jpeg");
	
	JButton bRestart = new JButton("Restart");
	// pour ne pas que le boutton soit focus sinon quand on clic dessus pour
	// rejouer le focus passe dessus et les events de touche s'executerons sur
	// le boutton et non sur la MainWindow ou l'on fait jouer le coup du joeur
	// (voir KeyPress plus bas)
	bRestart.setFocusable(false);
	bRestart.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		MainWindow.this.board.restart();
	    }
	});
	
	this.addKeyListener(new KeyListener() {
	    // classe anonyme et on est obliger d'implementer toutes les methodes
	    // de la classe meme si on les utilise pas
	    @Override
	    public void keyPressed(KeyEvent e) {
		// recuperer l'objet board de la classe MainWindow depuis la classe
		// anonyme
		Board board = MainWindow.this.board;
		if (e.getKeyCode() == KeyEvent.VK_UP) {
		    board.playMove(Direction.UP);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		    board.playMove(Direction.DOWN);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		    board.playMove(Direction.LEFT);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		    board.playMove(Direction.RIGHT);
		}
	    }
	    
	    @Override
	    public void keyTyped(KeyEvent e) {
		
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		
	    }
	});
	
	// ASSEMBLAGE DE LA FENETRE
	
	// variable qui sert pour les padding qui seront par exemple 2*pt soit 16px
	// 1 pt = 8 px
	final int pt = 8;
	
	// les createRigidArea c'est pour ajouter une zone vide pour faire le padding
	
	// zone avec les bouttons et tout
	JPanel controlPanel = new JPanel();
	controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
	controlPanel.add(bRestart);
	
	// arrangement global du contenu
	JPanel mainContent = new JPanel();
	mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.LINE_AXIS));
	// ajout du JPanel sur lequel le taquin est dessine directement, sans passer 
	// par un Layout
	mainContent.add(view);
	mainContent.add(Box.createRigidArea(new Dimension(2*pt, 0)));
	mainContent.add(controlPanel);
	mainContent.add(Box.createRigidArea(new Dimension(2*pt, 0)));
	
	// placement du contenu dans la fenetre
	this.add(mainContent);
	pack();
	
	// CARACTERISTIQUES DE LA JFRAME
	
	this.setTitle("Taquin");
	// centrer la fenetre
	this.setLocationRelativeTo(null);
	// fermer la fenetre quand on press sur la croix
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
    }
    
    /**
     * fonction dont on dire ce qu'on veut actualiser, labels, JPanel ou autres
     * @param source l'object qui a appelle un firechange() (ici on en a pas besoin)
     */
    @Override
    public void update(Object source) {
	// pour l'instant j'ai fait mon bourrin et j'ai dis de repaindre toute la
	// fenetre
	this.repaint();
    }
    
}
