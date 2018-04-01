package model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

/**
	* Version console du taquin.
*/
public class Main {

	public static void main(String[] args) {
		boolean exit = false;
		long start= System.currentTimeMillis();
		Board mystic_square= new Board(3,3);
		mystic_square.shuffle(10000);
		while (!(mystic_square.isSolved())) {
			Scanner sc= new Scanner(System.in);
			EmptyTile empty = mystic_square.getEmptyTile();
			ArrayList<Board.Direction> possible_moves = mystic_square.neighbours(empty.getX(), empty.getY());
			System.out.println(mystic_square);
			System.out.println("Choisissez un déplacement: z, q, s, d   Résolution automatique : r   Quitter : e");
			String selectMove = sc.nextLine();

			if (selectMove.equals("z") && (possible_moves.contains(Board.Direction.UP))) {
				mystic_square.move(Board.Direction.UP);
			}
			if (selectMove.equals("q") && (possible_moves.contains(Board.Direction.LEFT))) {
				mystic_square.move(Board.Direction.LEFT);
			}
			if (selectMove.equals("s") && (possible_moves.contains(Board.Direction.DOWN))) {
				mystic_square.move(Board.Direction.DOWN);
			}
			if (selectMove.equals("d") && (possible_moves.contains(Board.Direction.RIGHT))) {
				mystic_square.move(Board.Direction.RIGHT);
			}
			if (selectMove.equals("r"))  {
				mystic_square.solve();
				System.out.println(mystic_square);
			}
			if (selectMove.equals("e"))  {
				exit=true;
				break;
			}
		}
		if (!(exit)) {
		System.out.println(mystic_square);
		System.out.println("résolu en " + mystic_square.getMoveCount() + " coups");
		System.out.println(System.currentTimeMillis()-start);
		}
	}
}
