package game;

import java.util.LinkedList;

public class Rook extends Piece {
	
	private boolean hasMoved;
	
	public Rook(Square square, boolean isWhite, String nameOfPiece) {
		super(square, isWhite, nameOfPiece);
		hasMoved = false;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}

	@Override
	public LinkedList<Square> getLegalSquaresToMoveTo() {
		Board board = this.getSquare().getBoard();
		
		LinkedList<Square> legalSquaresToMoveTo = new LinkedList<>();
		
		legalSquaresToMoveTo = getLegalSquaresLinearly(board, this.getSquare());
		
		return legalSquaresToMoveTo;
	}
	
	@Override
	public void move(Square targetSquare) {
		this.getSquare().setPiece(null);
		if(targetSquare.getPiece() != null && targetSquare.getPiece().isColoredWhite()) {
			targetSquare.getBoard().whitePieces.remove(targetSquare.getPiece());
		}
		if(targetSquare.getPiece() != null && !targetSquare.getPiece().isColoredWhite()) {
			targetSquare.getBoard().blackPieces.remove(targetSquare.getPiece());
		}
		targetSquare.setPiece(this);
		this.setSquare(targetSquare);
		
		if(this.getSquare().getBoard().getEnPassentSquare() != null) {
			this.getSquare().getBoard().setEnPassentSquare(null);
		}
		
		hasMoved = true;
	}

}
