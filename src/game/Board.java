package game;

import java.io.Serializable;
import java.util.LinkedList;

public class Board implements Serializable {
	private Player playerWhite;
	private Player playerBlack;
	private final Square[][] squares;
	private boolean whiteToMove;
	private Square enPassentSquare;
	public LinkedList<Piece> whitePieces;
	public LinkedList<Piece> blackPieces;
	private CheckControl checkControl;
	
	public Board() {
		this.playerWhite = new Human(this);
		this.playerBlack = new Human(this);
		squares = new Square[8][8];
		
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				boolean colorIsWhite = (x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1) ? false : true;
				if(colorIsWhite) {
					squares[x][y] = new Square(this, x, y, true);
				} else {
					squares[x][y] = new Square(this, x, y, false);
				}
			}
		}
		
		this.whiteToMove = true;
		this.enPassentSquare = null;
		
		setUpStartingPosition();
	}
	
	public Player getPlayerWhite() {
		return playerWhite;
	}
	public void setPlayerWhite(Player playerWhite) {
		this.playerWhite = playerWhite;
	}
	
	public Player getPlayerBlack() {
		return playerBlack;
	}
	public void setPlayerBlack(Player playerBlack) {
		this.playerBlack = playerBlack;
	}
	
	public Square[][] getSquares() {
		return squares;
	}

	public boolean isWhiteToMove() {
		return whiteToMove;
	}
	public void setWhiteToMove(boolean b) {
		whiteToMove = b;
	}
	
	public Square getEnPassentSquare() {
		return enPassentSquare;
	}
	public void setEnPassentSquare(Square eps) {
		enPassentSquare = eps;
	}
	
	public CheckControl getCheckControl() {
		return checkControl;
	}
	public void setCheckControl(CheckControl checkcontrol) {
		this.checkControl = checkcontrol;
	}
	
	public void setUpStartingPosition() {
		for(int x = 0; x < 8; x++) {
			squares[x][1].setPiece(new Pawn(squares[x][1], true, "whitepawn"));
			squares[x][6].setPiece(new Pawn(squares[x][6], false, "blackpawn"));
		}
		squares[0][0].setPiece(new Rook(squares[0][0], true, "whiterook"));
		squares[1][0].setPiece(new Knight(squares[1][0], true, "whiteknight"));
		squares[2][0].setPiece(new Bishop(squares[2][0], true, "whitebishop"));
		squares[3][0].setPiece(new Queen(squares[3][0], true, "whitequeen"));
		King whiteKing = new King(squares[4][0], true, "whiteking");
		squares[4][0].setPiece(whiteKing);
		squares[5][0].setPiece(new Bishop(squares[5][0], true, "whitebishop"));
		squares[6][0].setPiece(new Knight(squares[6][0], true, "whiteknight"));
		squares[7][0].setPiece(new Rook(squares[7][0], true, "whiterook"));
		squares[0][7].setPiece(new Rook(squares[0][7], false, "blackrook"));
		squares[1][7].setPiece(new Knight(squares[1][7], false, "blackknight"));
		squares[2][7].setPiece(new Bishop(squares[2][7], false, "blackbishop"));
		squares[3][7].setPiece(new Queen(squares[3][7], false, "blackqueen"));
		King blackKing = new King(squares[4][7], false, "blackking");
		squares[4][7].setPiece(blackKing);
		squares[5][7].setPiece(new Bishop(squares[5][7], false, "blackbishop"));
		squares[6][7].setPiece(new Knight(squares[6][7], false, "blackknight"));
		squares[7][7].setPiece(new Rook(squares[7][7], false, "blackrook"));
		for(int x = 0; x < 8; x++) {
			for(int y = 2; y < 6; y++) {
				squares[x][y].setPiece(null);
			}
		}
		
		whitePieces = new LinkedList<>();
		blackPieces = new LinkedList<>();
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 2; y++) {
				whitePieces.add(squares[x][y].getPiece());
				blackPieces.add(squares[x][y + 6].getPiece());
			}
		}
		
		checkControl = new CheckControl(this, whitePieces, blackPieces, whiteKing, blackKing);
	}
	
	public void clearBoard() {
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				squares[x][y].setPiece(null);
			}
		}
		
		whitePieces = new LinkedList<>();
		blackPieces = new LinkedList<>();
	}
	
}
