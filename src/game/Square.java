package game;

import java.io.Serializable;

public class Square implements Serializable {
	private final Board board;
	private final int coordinateX;
	private final int coordinateY;
	private final boolean coloredWhite;
	private Piece piece;
	
	public Square(Board board, int coordinateX, int coordinateY, boolean coloredWhite) {
		this.board = board;
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.coloredWhite = coloredWhite;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public int getCoordinateX() {
		return coordinateX;
	}
	
	public int getCoordinateY() {
		return coordinateY;
	}
	
	public boolean isColoredWhite() {
		return coloredWhite;
	}
	
}
