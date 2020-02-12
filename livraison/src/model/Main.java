
package model;

public class Main {

    public static void main(String[] args) {
        Board b = new Board(3, 3);
        b.move(Direction.UP);
        b.move(Direction.UP);
        System.out.println(b);
        
    }
    
}
