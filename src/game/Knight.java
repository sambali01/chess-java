package game;

import java.util.LinkedList;

public class Knight extends Piece {
	
	public Knight(Square square, boolean isWhite, String nameOfPiece) {
		super(square, isWhite, nameOfPiece);
	}

	@Override
	public LinkedList<Square> getLegalSquaresToMoveTo() {
		Board board = this.getSquare().getBoard();
		
		LinkedList<Square> legalSquaresToMoveTo = new LinkedList<>();
		
		int x = this.getSquare().getCoordinateX();
		int y = this.getSquare().getCoordinateY();
		
		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j <= 2; j++) {
				if(((Math.abs(i) == 2 && Math.abs(j) == 1) || (Math.abs(i) == 1 && Math.abs(j) == 2))
				&& 0 <= x + i && x + i < 8 && 0 <= y + j && y + j < 8) {
					if(board.getSquares()[x + i][y + j].getPiece() != null
					&& board.getSquares()[x + i][y + j].getPiece().isColoredWhite() != this.isColoredWhite()) {
						legalSquaresToMoveTo.add(board.getSquares()[x + i][y + j]);
					} else if(board.getSquares()[x + i][y + j].getPiece() == null) {
						legalSquaresToMoveTo.add(board.getSquares()[x + i][y + j]);
					}
				}
			}
		}
		
		return legalSquaresToMoveTo; 
	}
	
}
