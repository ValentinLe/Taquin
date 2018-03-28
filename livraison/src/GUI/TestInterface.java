package GUI;

import model.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

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

    for (int i = 0; i < 9; i++) {
      BufferedImage newv = this.image.getSubimage(i*50,i*50,200,200);
      this.add(new JLabel(new ImageIcon(newv)));
    }

    this.pack();
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
