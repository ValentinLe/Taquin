package model;

public class Main {

	public static void main(String[] args) {
		Board test= new Board(3,3);
		test.createGrid();
		test.shuffle(9000);
		System.out.println(test);
		test.solve();

	}
}
