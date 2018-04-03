package GUI;

import model.*;

public class Main {
    public static void main(String[] args) {
        Board b = new Board(3,3);
        b.createGrid();
        //new Interface(b);
        new TestInterface();
    }
}
