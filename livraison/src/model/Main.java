package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Board test= new Board(3,3);
		test.createGrid();
		test.shuffle(10);
		//System.out.println(test);
		//test.solve();
		while (test.isFinished() != true) {
			Scanner sc= new Scanner(System.in);
			EmptyTile empty = test.getEmptyTile();
			//ArrayList<String> possible_moves = test.neighbours();
			System.out.println("Choisissez un déplacement: z, q, s, d");
			String selectMove = sc.nextLine();

			if (selectMove.equals("z") && (test.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.UP))) {
				System.out.println("Up");
				test.move(Board.Direction.UP);
			}
			if (selectMove.equals("q") && (test.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.LEFT))) {
				System.out.println("Left");
				test.move(Board.Direction.LEFT);
			}
			if (selectMove.equals("s") && (test.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.DOWN))) {
				System.out.println("Down");
				test.move(Board.Direction.DOWN);
			}
			if (selectMove.equals("d") && (test.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.RIGHT))) {
				System.out.println("Right");
				test.move(Board.Direction.RIGHT);
			}
		}

	}
}
