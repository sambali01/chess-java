package game;

import java.io.Serializable;

public abstract class Player implements Serializable {
	private Board board;
	
	public Player(Board board) {
		this.board = board;
	}
	
	public Board getBoard() {
		return board;
	}
	
	abstract public void makeMove();
}
