package game;

import java.util.LinkedList;

public class Pawn extends Piece {

	public Pawn(Square square, boolean coloredWhite, String nameOfPiece) {
		super(square, coloredWhite, nameOfPiece);
	}

	@Override
	public LinkedList<Square> getLegalSquaresToMoveTo() {
		Board board = this.getSquare().getBoard();
		
		LinkedList<Square> legalSquaresToMoveTo = new LinkedList<>();
		
		int x = this.getSquare().getCoordinateX();
		int y = this.getSquare().getCoordinateY();
		
		if(this.isColoredWhite()) {
			if(board.getSquares()[x][y + 1].getPiece() == null) legalSquaresToMoveTo.add(board.getSquares()[x][y + 1]);
			if(y == 1 && board.getSquares()[x][y + 1].getPiece() == null && board.getSquares()[x][y + 2].getPiece() == null) {
				legalSquaresToMoveTo.add(board.getSquares()[x][y + 2]);
			}
			if(0 <= x - 1 && x - 1 < 8
			&& ((board.getSquares()[x - 1][y + 1].getPiece() != null && !board.getSquares()[x - 1][y + 1].getPiece().isColoredWhite())
			|| board.getSquares()[x - 1][y + 1].equals(board.getEnPassentSquare()))) {
				legalSquaresToMoveTo.add(board.getSquares()[x - 1][y + 1]);
			}
			if(0 <= x + 1 && x + 1 < 8
			&& ((board.getSquares()[x + 1][y + 1].getPiece() != null && !board.getSquares()[x + 1][y + 1].getPiece().isColoredWhite())
			|| board.getSquares()[x + 1][y + 1].equals(board.getEnPassentSquare()))) {
				legalSquaresToMoveTo.add(board.getSquares()[x + 1][y + 1]);
			}
		} else {
			if(board.getSquares()[x][y - 1].getPiece() == null) legalSquaresToMoveTo.add(board.getSquares()[x][y - 1]);
			if(y == 6 && board.getSquares()[x][y - 1].getPiece() == null && board.getSquares()[x][y - 2].getPiece() == null) {
				legalSquaresToMoveTo.add(board.getSquares()[x][y - 2]);
			}
			if(0 <= x - 1 && x - 1 < 8
			&& ((board.getSquares()[x - 1][y - 1].getPiece() != null && board.getSquares()[x - 1][y - 1].getPiece().isColoredWhite())
			|| board.getSquares()[x - 1][y - 1].equals(board.getEnPassentSquare()))) {
				legalSquaresToMoveTo.add(board.getSquares()[x - 1][y - 1]);
			}
			if(0 <= x + 1 && x + 1 < 8
			&& ((board.getSquares()[x + 1][y - 1].getPiece() != null && board.getSquares()[x + 1][y - 1].getPiece().isColoredWhite())
			|| board.getSquares()[x + 1][y - 1].equals(board.getEnPassentSquare()))) {
				legalSquaresToMoveTo.add(board.getSquares()[x + 1][y - 1]);
			}
		}
		
		return legalSquaresToMoveTo;
	}
	
	@Override
	public void move(Square targetSquare) {
		Board board = this.getSquare().getBoard();
		
		int x = this.getSquare().getCoordinateX();
		int y = this.getSquare().getCoordinateY();
		
		if(this.isColoredWhite()) {
			if(targetSquare.getCoordinateX() == x && targetSquare.getCoordinateY() == y + 2) {
				board.setEnPassentSquare(board.getSquares()[x][y + 1]);
			} else if(board.getEnPassentSquare() != null) {
				if(targetSquare.getCoordinateX() - x == 1 && board.getEnPassentSquare().equals(targetSquare)) {
					if(board.getSquares()[x + 1][y].getPiece() != null) {
						board.blackPieces.remove(board.getSquares()[x + 1][y].getPiece());
						board.getSquares()[x + 1][y].setPiece(null);
					}
				} else if(targetSquare.getCoordinateX() - x == -1 && board.getEnPassentSquare().equals(targetSquare)) {
					if(board.getSquares()[x - 1][y].getPiece() != null) {
						board.blackPieces.remove(board.getSquares()[x - 1][y].getPiece());
						board.getSquares()[x - 1][y].setPiece(null);
					}
				}
				board.setEnPassentSquare(null);
			} else {
				if(board.getEnPassentSquare() != null) {
					board.setEnPassentSquare(null);
				}
			}
		} else {
			if(targetSquare.getCoordinateX() == x && targetSquare.getCoordinateY() == y - 2) {
				board.setEnPassentSquare(board.getSquares()[x][y - 1]);
			} else if(board.getEnPassentSquare() != null) {
				if(targetSquare.getCoordinateX() - x == 1 && board.getEnPassentSquare().equals(targetSquare)) {
					if(board.getSquares()[x + 1][y].getPiece() != null) {
						board.whitePieces.remove(board.getSquares()[x + 1][y].getPiece());
						board.getSquares()[x + 1][y].setPiece(null);
					}
				} else if(targetSquare.getCoordinateX() - x == -1 && board.getEnPassentSquare().equals(targetSquare)) {
					if(board.getSquares()[x - 1][y].getPiece() != null) {
						board.whitePieces.remove(board.getSquares()[x - 1][y].getPiece());
						board.getSquares()[x - 1][y].setPiece(null);
					}
				}
				board.setEnPassentSquare(null);
			} else {
				if(board.getEnPassentSquare() != null) {
					board.setEnPassentSquare(null);
				}
			}
		}
		
		this.getSquare().setPiece(null);
		if(targetSquare.getPiece() != null && targetSquare.getPiece().isColoredWhite()) {
			targetSquare.getBoard().whitePieces.remove(targetSquare.getPiece());
		}
		if(targetSquare.getPiece() != null && !targetSquare.getPiece().isColoredWhite()) {
			targetSquare.getBoard().blackPieces.remove(targetSquare.getPiece());
		}
		targetSquare.setPiece(this);
		this.setSquare(targetSquare);
		
	}

}
