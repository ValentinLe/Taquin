package GUI;

import model.*;
import javax.swing.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class TestInterface extends JFrame {

  private BufferedImage image;

  public TestInterface() {
    this.setTitle("Taquin");
    this.setResizable(false);
    try {
      File file = new File("test.png");
      this.image = ImageIO.read(file);
    } catch (IOException e) {
      System.out.println(e);
    }

    HashMap<ArrayList<Integer>,Image> hmap = new HashMap<>();

    ArrayList<Integer> list = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++){
        BufferedImage newv = this.image.getSubimage(i*50,j*50,200,200);
        Image im_lab = new ImageIcon(newv).getImage();
        list.add(i);
        list.add(j);
        hmap.put(list,im_lab);
      }
    }
    


    this.pack();
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
