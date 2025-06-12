package game;

import java.io.Serializable;
import java.util.LinkedList;

public class CheckControl implements Serializable {
	private Board board;
	private LinkedList<Piece> whitePieces;
	private LinkedList<Piece> blackPieces;
	private King whiteKing;
	private King blackKing;
	
	public CheckControl(Board board, LinkedList<Piece> whitePieces, LinkedList<Piece> blackPieces, King whiteKing, King blackKing) {
		this.board = board;
		this.whitePieces = whitePieces;
		this.blackPieces = blackPieces;
		this.whiteKing = whiteKing;
		this.blackKing = blackKing;
	}
	
	public boolean whiteIsInCheck() {
		for(Piece p : blackPieces) {
			if(p.getLegalSquaresToMoveTo().contains(whiteKing.getSquare())) {
					return true;
			}
		}
		return false;
	}
	
	public boolean blackIsInCheck() {
		for(Piece p : whitePieces) {
			if(p.getLegalSquaresToMoveTo().contains(blackKing.getSquare())) {
					return true;
			}
		}
		return false;
	}
	
	public boolean doesMoveIntoCheck(boolean whiteToMove, Piece movingPiece, Square targetSquare) {
		targetSquare.getBoard().getSquares()[movingPiece.getSquare().getCoordinateX()][movingPiece.getSquare().getCoordinateY()].setPiece(null);
		Square initialSquare = movingPiece.getSquare();
		Piece capturedPiece = null;
		if(whiteToMove) {
			if(targetSquare.getPiece() != null && !targetSquare.getPiece().isColoredWhite()) {
				capturedPiece = targetSquare.getPiece();
				targetSquare.getBoard().blackPieces.remove(targetSquare.getPiece());
			}
		} else {
			if(targetSquare.getPiece() != null && targetSquare.getPiece().isColoredWhite()) {
				capturedPiece = targetSquare.getPiece();
				targetSquare.getBoard().whitePieces.remove(targetSquare.getPiece());
			}
		}
		targetSquare.setPiece(movingPiece);
		movingPiece.setSquare(targetSquare);
		
		boolean whiteInCheck = false;
		boolean blackInCheck = false;
		if(whiteToMove) {
			whiteInCheck = targetSquare.getBoard().getCheckControl().whiteIsInCheck();
		} else {
			blackInCheck = targetSquare.getBoard().getCheckControl().blackIsInCheck();
		}
		initialSquare.setPiece(movingPiece);
		movingPiece.setSquare(initialSquare);
		targetSquare.setPiece(capturedPiece);
		if(capturedPiece != null) {
			if(capturedPiece.isColoredWhite()) {
				targetSquare.getBoard().whitePieces.add(capturedPiece);
			} else {
				targetSquare.getBoard().blackPieces.add(capturedPiece);
			}
		}
		
		return whiteInCheck || blackInCheck;
	}
	
	public boolean whiteIsCheckmated() {
		if(board.isWhiteToMove() && whiteIsInCheck()) {
			for(Piece movingPiece : board.whitePieces) {
				for(Square targetSquare : movingPiece.getLegalSquaresToMoveTo()) {
					if(!doesMoveIntoCheck(true, movingPiece, targetSquare)) return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean blackIsCheckmated() {
		if(!board.isWhiteToMove() && blackIsInCheck()) {
			for(Piece movingPiece : board.blackPieces) {
				for(Square targetSquare : movingPiece.getLegalSquaresToMoveTo()) {
					if(!doesMoveIntoCheck(false, movingPiece, targetSquare)) return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean staleMateHappened() {
		if(board.isWhiteToMove() && !whiteIsInCheck()) {
			for(Piece movingPiece : board.whitePieces) {
				for(Square targetSquare : movingPiece.getLegalSquaresToMoveTo()) {
					if(!doesMoveIntoCheck(true, movingPiece, targetSquare)) return false;
				}
			}
			return true;
		} else if(!board.isWhiteToMove() && !blackIsInCheck()) {
			for(Piece movingPiece : board.blackPieces) {
				for(Square targetSquare : movingPiece.getLegalSquaresToMoveTo()) {
					if(!doesMoveIntoCheck(false, movingPiece, targetSquare)) return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
