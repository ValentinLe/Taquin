package GUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.*;
import model.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
  * Fenetre du mode de selection de l'image
  */
public class SelectImage extends JFrame {

  private JList<String> list;
  private BufferedImage image;
  private int indice;
  private String path;
  private String[] images;
  private int nbImages;

  /**
    * Constructeur de la classe
    */
  public SelectImage () {

    this.setResizable(true);
    this.setTitle("Select image");
    this.indice = 0;
    this.images = new File("ressources").list();
    this.nbImages = images.length;

    this.setPathAndImage();

    // les boutons
    JPanel zoneButton = new JPanel ();

    JButton bPlay = new JButton("Jouer");
    bPlay.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e){
        Board b = new Board(3,3);
        b.createGrid();
        String path = "espace.jpeg";
        SelectImage.this.dispose();
        new Interface(b,SelectImage.this.path);
      }
    });

    JButton bQuit = new JButton("Quitter");
    bQuit.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e){
          SelectImage.this.dispose();
      }
    });

    zoneButton.add(bPlay);
    zoneButton.add(bQuit);
    zoneButton.setLayout(new GridLayout(2,1,10,10));

    // la liste d'image
    Vector<String> vect = new Vector<>();
    for (String im : this.images) {
      vect.add(im.split("\\.")[0]);
    }
    this.list = new JList<>(vect);
    this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    DefaultListCellRenderer centrer = (DefaultListCellRenderer)this.list.getCellRenderer();
    centrer.setHorizontalAlignment(SwingConstants.CENTER);
    this.list.setSelectedIndex(0);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(list);

    this.list.addListSelectionListener(new ListSelectionListener () {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        SelectImage.this.indice = SelectImage.this.list.getSelectedIndex();
        SelectImage.this.setPathAndImage();
        repaint();
      }
    });

    JPanel listImage = new JPanel();
    scrollPane.setPreferredSize(new Dimension(100,300));
    listImage.add(scrollPane);

    // affichage de l'image
    JPanel visual = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        g.drawImage(SelectImage.this.image,0, 0,500, 250, this);
      }
    };
    visual.setPreferredSize(new Dimension(500,250));

    // gestion du positionnement de la frame
    this.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
    gc.fill = GridBagConstraints.HORIZONTAL;

    gc.gridx = 0;
    gc.gridy = 0;
    this.add(zoneButton,gc);
    gc.gridx = 1;
    this.add(listImage,gc);
    gc.gridx = 2;
    this.add(visual,gc);

    // parametres de la frame
    pack();

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

  /**
    * Modifie le chemin de l'image en fonction de l'element séléctionné de la liste et lit l'image selon ce chemin
    */
  public void setPathAndImage() {
    this.path = "ressources/" + this.images[this.indice];
    try {
      File file = new File(this.path);
      this.image = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
