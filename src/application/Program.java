package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
		final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
		final String ANSI_RESET = "\u001B[0m";
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosiiton(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosiiton(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if (chessMatch.getPromoted() != null) {
					System.out.print(ANSI_GREEN_BACKGROUND + "Enter piece for promotion (B/N/R/Q):" + ANSI_RESET + " ");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.print(ANSI_GREEN_BACKGROUND + "Invalid value! Enter piece for promotion (B/N/R/Q):" + ANSI_RESET + " ");
						type = sc.nextLine().toUpperCase();
					}
					chessMatch.replacePromotedPiece(type);
				}
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); //caso ocorra alguma exce��o o programa aguarda o usu�rio apertar enter.
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); //caso ocorra alguma exce��o o programa aguarda o usu�rio apertar enter.
			}
			
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
		
		
		
		
		
	}

}
