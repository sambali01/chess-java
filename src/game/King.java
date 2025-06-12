package game;

import java.util.LinkedList;

public class King extends Piece {
	private boolean hasMoved;

	public King(Square square, boolean coloredWhite, String nameOfPiece) {
		super(square, coloredWhite, nameOfPiece);
		hasMoved = false;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}

	@Override
	public LinkedList<Square> getLegalSquaresToMoveTo() {
		Board board = this.getSquare().getBoard();
		
		LinkedList<Square> legalSquaresToMoveTo = new LinkedList<>();
		
		int x = this.getSquare().getCoordinateX();
		int y = this.getSquare().getCoordinateY();
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if((!(i == 0 && j== 0)) && 0 <= x + i && x + i < 8 && 0 <= y + j && y + j < 8) {
					if(board.getSquares()[x + i][y + j].getPiece() == null) {
						legalSquaresToMoveTo.add(board.getSquares()[x + i][y + j]);
					} else {
						if(board.getSquares()[x + i][y + j].getPiece().isColoredWhite() != this.isColoredWhite()) {
							legalSquaresToMoveTo.add(board.getSquares()[x + i][y + j]);
						}
					}
				}
			}
		}
		
		// castle
		if(this.isColoredWhite() && !hasMoved) {
			Rook h1Rook = null, a1Rook = null;
			for(Piece p : board.whitePieces) {
				if(p.getClass().equals(Rook.class) && p.getSquare().equals(board.getSquares()[7][0])) {
					h1Rook = (Rook) p;
					break;
				}
			}
			for(Piece p : board.whitePieces) {
				if(p.getClass().equals(Rook.class) && p.getSquare().equals(board.getSquares()[0][0])) {
					a1Rook = (Rook) p;
					break;
				}
			}
			
			boolean f1IsCovered = false, d1IsCovered = false;
			for(Piece p : board.blackPieces) {
				if(!p.getClass().equals(King.class) && p.getLegalSquaresToMoveTo().contains(board.getSquares()[5][0])) {
					f1IsCovered = true;
					break;
				}
			}
			for(Piece p : board.blackPieces) {
				if(!p.getClass().equals(King.class) && p.getLegalSquaresToMoveTo().contains(board.getSquares()[3][0])) {
					d1IsCovered = true;
					break;
				}
			}
			
			if(x + 2 < 8 && 0 <= y && y < 8) {
			if(h1Rook != null && !h1Rook.hasMoved()
			&& board.getSquares()[x + 1][y].getPiece() == null && board.getSquares()[x + 2][y].getPiece() == null
			&& !f1IsCovered) {
				legalSquaresToMoveTo.add(board.getSquares()[x + 2][y]);
			}
			}
			if(x - 2 >= 0 && 0 <= y && y < 8) {
			if(a1Rook != null && !a1Rook.hasMoved()
			&& board.getSquares()[x - 1][y].getPiece() == null && board.getSquares()[x - 2][y].getPiece() == null && board.getSquares()[x - 3][y].getPiece() == null
			&& !d1IsCovered) {
				legalSquaresToMoveTo.add(board.getSquares()[x - 2][y]);
			}
			}
		}
		
		if(!this.isColoredWhite() && !hasMoved) {
			Rook h8Rook = null, a8Rook = null;
			for(Piece p : board.blackPieces) {
				if(p.getClass().equals(Rook.class) && p.getSquare().equals(board.getSquares()[7][7])) {
					h8Rook = (Rook) p;
					break;
				}
			}
			for(Piece p : board.blackPieces) {
				if(p.getClass().equals(Rook.class) && p.getSquare().equals(board.getSquares()[0][7])) {
					a8Rook = (Rook) p;
					break;
				}
			}
			
			boolean f8IsCovered = false, d8IsCovered = false;
			for(Piece p : board.whitePieces) {
				if(!p.getClass().equals(King.class) && p.getLegalSquaresToMoveTo().contains(board.getSquares()[5][7])) {
					f8IsCovered = true;
					break;
				}
			}
			for(Piece p : board.whitePieces) {
				if(!p.getClass().equals(King.class) && p.getLegalSquaresToMoveTo().contains(board.getSquares()[3][7])) {
					f8IsCovered = true;
					break;
				}
			}
			
			if(x + 2 < 8 && 0 <= y && y < 8) {
			if(h8Rook != null && !h8Rook.hasMoved()
			&& board.getSquares()[x + 1][y].getPiece() == null && board.getSquares()[x + 2][y].getPiece() == null
			&& !f8IsCovered) {
				legalSquaresToMoveTo.add(board.getSquares()[x + 2][y]);
			}
			}
			if(x - 2 >= 0 && 0 <= y && y < 8)
			if(a8Rook != null && !a8Rook.hasMoved()
			&& board.getSquares()[x - 1][y].getPiece() == null && board.getSquares()[x - 2][y].getPiece() == null && board.getSquares()[x - 3][y].getPiece() == null
			&& !d8IsCovered) {
				legalSquaresToMoveTo.add(board.getSquares()[x - 2][y]);
			}
		}
		
		return legalSquaresToMoveTo;
	}
	
	@Override
	public void move(Square targetSquare) {
		
		if(targetSquare.getCoordinateX() - this.getSquare().getCoordinateX() == 2) {
			if(this.isColoredWhite()) {
				System.out.println("ok");
				getSquare().getBoard().getSquares()[7][0].getPiece().move(getSquare().getBoard().getSquares()[5][0]);
			} else {
				getSquare().getBoard().getSquares()[7][7].getPiece().move(getSquare().getBoard().getSquares()[5][7]);
			}
		} else if(targetSquare.getCoordinateX() - this.getSquare().getCoordinateX() == -2) {
			if(this.isColoredWhite()) {
				getSquare().getBoard().getSquares()[0][0].getPiece().move(getSquare().getBoard().getSquares()[3][0]);
			} else {
				getSquare().getBoard().getSquares()[0][7].getPiece().move(getSquare().getBoard().getSquares()[3][7]);
			}
		}
		
		this.getSquare().setPiece(null);
		if(targetSquare.getPiece() != null && targetSquare.getPiece().isColoredWhite()) {
			targetSquare.getBoard().whitePieces.remove(targetSquare.getPiece());
		}
		if(targetSquare.getPiece() != null && !targetSquare.getPiece().isColoredWhite()) {
			targetSquare.getBoard().blackPieces.remove(targetSquare.getPiece());
		}
		this.setSquare(targetSquare);
		targetSquare.setPiece(this);
		
		if(this.getSquare().getBoard().getEnPassentSquare() != null) {
			this.getSquare().getBoard().setEnPassentSquare(null);
		}
		
		hasMoved = true;
	}
	
}
