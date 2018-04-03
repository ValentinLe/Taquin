package GUI;

import model.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      Scanner sc= new Scanner(System.in);
      System.out.println("Entrez le chemin d'une image");
      String path = sc.nextLine();
      Board b = new Board(2,2);
      b.createGrid();
      new Interface(b,path);
    }
}
