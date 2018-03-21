package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Board test= new Board(3,3);
		test.createGrid();
		test.shuffle(10000);
		System.out.println(test);
		//test.solve();
		int nb_moves=0;
		while (!(test.isSolved())) {
			Scanner sc= new Scanner(System.in);
			EmptyTile empty = test.getEmptyTile();
			ArrayList<Board.Direction> possible_moves = test.neighbours(empty.getX(), empty.getY());
			System.out.println(test);
			System.out.println("voisins : " + possible_moves);
			System.out.println("position empty : " + test.getEmptyTile().getX() + ";" + test.getEmptyTile().getY());
			System.out.println("Choisissez un déplacement: z, q, s, d");
			String selectMove = sc.nextLine();

			if (selectMove.equals("z") && (test.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.UP))) {
				test.move(Board.Direction.UP);
			}
			if (selectMove.equals("q") && (test.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.LEFT))) {
				test.move(Board.Direction.LEFT);
			}
			if (selectMove.equals("s") && (test.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.DOWN))) {
				test.move(Board.Direction.DOWN);
			}
			if (selectMove.equals("d") && (test.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.RIGHT))) {
				test.move(Board.Direction.RIGHT);
			}
			nb_moves+=1;
		}
		System.out.println("résolu en " + nb_moves + " coups");
	}
}
