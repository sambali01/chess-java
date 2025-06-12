package game;

import java.util.LinkedList;

public class Bishop extends Piece {
	
	public Bishop(Square square, boolean isWhite, String nameOfPiece) {
		super(square, isWhite, nameOfPiece);
	}

	@Override
	public LinkedList<Square> getLegalSquaresToMoveTo() {
		Board board = this.getSquare().getBoard();
		
		LinkedList<Square> legalSquaresToMoveTo = new LinkedList<>();
		
		legalSquaresToMoveTo = getLegalSquaresDiagonally(board, this.getSquare());
		
		return legalSquaresToMoveTo;
	}
	
}
