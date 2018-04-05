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

  public SelectImage () {

    this.setResizable(true);
    this.setTitle("Select map");
    this.indice = 0;
    this.images = new File("ressources").list();
    this.nbImages = images.length;

    this.setPathAndImage();

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
    listImage.setLayout(new GridBagLayout());
    GridBagConstraints cList = new GridBagConstraints();
    cList.gridx = 0;
    cList.gridy = 0;
    scrollPane.setPreferredSize(new Dimension(100,300));
    listImage.add(scrollPane,cList);
    cList.gridy = 1;

    JPanel visual = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        g.drawImage(SelectImage.this.image,0, 0,500, 250, this);
      }
    };
    visual.setPreferredSize(new Dimension(500,250));

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

    pack();

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

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
