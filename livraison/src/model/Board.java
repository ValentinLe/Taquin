package model;

public class Board {
    
    private int width;
    private int height;
    
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
}