
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    
    public MainWindow() {
	
	
	
	// assemblage fenetre

	// 1 pt = 8 px
	final int pt = 8;
	
	
	// CARACTERISTIQUES DE LA JFRAME
	this.setTitle("Taquin");
	this.setPreferredSize(new Dimension(400, 400));

	this.setMinimumSize(new Dimension(400, 400));
	// centrer la fenetre
	this.setLocationRelativeTo(null);
	// fermer la fenetre quand on press sur la croix
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
    }
    
}
