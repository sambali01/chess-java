package game;

import java.util.LinkedList;

public class Queen extends Piece {

	public Queen(Square square, boolean coloredWhite, String nameOfPiece) {
		super(square, coloredWhite, nameOfPiece);
	}
	
	@Override
	public LinkedList<Square> getLegalSquaresToMoveTo() {
		Board board = this.getSquare().getBoard();
		
		LinkedList<Square> legalSquaresToMoveTo = getLegalSquaresDiagonally(board, this.getSquare());
		legalSquaresToMoveTo.addAll(getLegalSquaresLinearly(board, this.getSquare()));
		
		return legalSquaresToMoveTo;
	}

}
