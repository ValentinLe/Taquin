package model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import GUI.*;

/**
	* Version console du taquin.
*/
public class Main {

	public static void main(String[] args) {
		boolean exit = false;
		Board mystic_square= new Board(3,3);
		mystic_square.shuffle(10000);
		new Interface(mystic_square,"ressources/espace.jpeg");
		while (!(mystic_square.isSolved())) {
			Scanner sc= new Scanner(System.in);
			EmptyTile empty = mystic_square.getEmptyTile();
			ArrayList<Board.Direction> possible_moves = mystic_square.neighbours(empty.getX(), empty.getY());
			System.out.println("\033[H\033[2J");
			System.out.println(mystic_square);
			System.out.println("Choisissez un déplacement: z, q, s, d   Résolution automatique : a   Recommencer : r  Quitter : e");
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
			if (selectMove.equals("a"))  {
				mystic_square.solve();
				System.out.println(mystic_square);
			}
			if (selectMove.equals("r"))  {
				mystic_square.shuffle(10000);
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
		}
	}
}
