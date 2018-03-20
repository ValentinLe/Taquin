package GUI;

import model.*;

public class Main {
    public static void main(String[] args) {
        Board b = new Board(5,5);
        b.createGrid();
        new Interface(b);
    }
}
