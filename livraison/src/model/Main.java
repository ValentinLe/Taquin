package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Board mystic_square= new Board(3,3);
		mystic_square.createGrid();
		mystic_square.shuffle(10000);
		System.out.println(mystic_square);
		while (!(mystic_square.isSolved())) {
			Scanner sc= new Scanner(System.in);
			EmptyTile empty = mystic_square.getEmptyTile();
			ArrayList<Board.Direction> possible_moves = mystic_square.neighbours(empty.getX(), empty.getY());
			System.out.println(mystic_square);
			System.out.println("Choisissez un déplacement: z, q, s, d        Résolution automatique : r");
			String selectMove = sc.nextLine();

			if (selectMove.equals("z") && (mystic_square.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.UP))) {
				mystic_square.move(Board.Direction.UP);
			}
			if (selectMove.equals("q") && (mystic_square.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.LEFT))) {
				mystic_square.move(Board.Direction.LEFT);
			}
			if (selectMove.equals("s") && (mystic_square.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.DOWN))) {
				mystic_square.move(Board.Direction.DOWN);
			}
			if (selectMove.equals("d") && (mystic_square.neighbours(empty.getX(),empty.getY()).contains(Board.Direction.RIGHT))) {
				mystic_square.move(Board.Direction.RIGHT);
			}
			if (selectMove.equals("r"))  {
				mystic_square.solve();
			}
		}
		System.out.println("résolu en " + mystic_square.getMoveCount() + " coups");
	}
}
