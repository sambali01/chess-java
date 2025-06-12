package game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public abstract class Piece implements Serializable {
	
	private Square square;
	private  boolean coloredWhite;
	transient private Image image;

	public Piece(Square square, boolean coloredWhite, String nameOfPiece) {
		this.square = square;
        this.coloredWhite = coloredWhite;
        try {
        	if (this.image == null) {
        		BufferedImage pieces = ImageIO.read(new File("src/res/pieces.png"));
        		
        		String[] names = new String[] {"whiteking", "whitequeen", "whitebishop", "whiteknight", "whiterook", "whitepawn",
        				"blackking", "blackqueen", "blackbishop", "blackknight", "blackrook", "blackpawn"};
        		
        		HashMap<String, Image> imagesWithNames = new HashMap<>();
        		int i = 0;
        		for(int y = 0; y < 400; y += 200) {
        			for(int x = 0; x < 1200; x += 200) {
        				imagesWithNames.put(names[i++], pieces.getSubimage(x, y, 200, 200).getScaledInstance(80, 80, BufferedImage.SCALE_SMOOTH));
        			}
        		}
        		this.image = imagesWithNames.get(nameOfPiece);
        	}
        } catch (IOException e) {
        	System.out.println("Error with pieces.png: " + e.getMessage());
        }
	}
	
	public Square getSquare() {
		return square;
	}
	public void setSquare(Square square) {
		this.square = square;
	}
	
	public boolean isColoredWhite() {
		return coloredWhite;
	}
	public void setColoredWhite(boolean b) {
		coloredWhite = b;
	}
	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	public void move(Square targetSquare) {
		square.setPiece(null);
		if(targetSquare.getPiece() != null && targetSquare.getPiece().isColoredWhite()) {
			targetSquare.getBoard().whitePieces.remove(targetSquare.getPiece());
		}
		if(targetSquare.getPiece() != null && !targetSquare.getPiece().isColoredWhite()) {
			targetSquare.getBoard().blackPieces.remove(targetSquare.getPiece());
		}
		targetSquare.setPiece(this);
		square = targetSquare;
		
		if(this.getSquare().getBoard().getEnPassentSquare() != null) {
			this.getSquare().getBoard().setEnPassentSquare(null);
		}
	}

	protected LinkedList<Square> getLegalSquaresDiagonally(Board board, Square initialSquare){
		int x = initialSquare.getCoordinateX();
		int y = initialSquare.getCoordinateY();
		
		int xNE = x + 1;
		int yNE = y + 1;
		int xSE = x + 1;
		int ySE = y - 1;
		int xSW = x - 1;
		int ySW = y - 1;
		int xNW = x - 1;
		int yNW = y + 1;
		
		LinkedList<Square> legalSquaresToMoveTo = new LinkedList<>();
		
		while(xNE < 8 && yNE < 8) {
			if(board.getSquares()[xNE][yNE].getPiece() != null) {
				if(board.getSquares()[xNE][yNE].getPiece().isColoredWhite() != this.isColoredWhite()) {
					legalSquaresToMoveTo.add(board.getSquares()[xNE][yNE]);
				}
				break;
			} else {
				legalSquaresToMoveTo.add(board.getSquares()[xNE++][yNE++]);
			}
		}
		
		while(xSE < 8 && 0 <= ySE) {
			if(board.getSquares()[xSE][ySE].getPiece() != null) {
				if(board.getSquares()[xSE][ySE].getPiece().isColoredWhite() != this.isColoredWhite()) {
					legalSquaresToMoveTo.add(board.getSquares()[xSE][ySE]);
				}
				break;
			} else {
				legalSquaresToMoveTo.add(board.getSquares()[xSE++][ySE--]);
			}
		}
		
		while(0 <= xSW && 0 <= ySW) {
			if(board.getSquares()[xSW][ySW].getPiece() != null) {
				if(board.getSquares()[xSW][ySW].getPiece().isColoredWhite() != this.isColoredWhite()) {
					legalSquaresToMoveTo.add(board.getSquares()[xSW][ySW]);
				}
				break;
			} else {
				legalSquaresToMoveTo.add(board.getSquares()[xSW--][ySW--]);
			}
		}
		
		while(0 <= xNW && yNW < 8) {
			if(board.getSquares()[xNW][yNW].getPiece() != null) {
				if(board.getSquares()[xNW][yNW].getPiece().isColoredWhite() != this.isColoredWhite()) {
					legalSquaresToMoveTo.add(board.getSquares()[xNW][yNW]);
				}
				break;
			} else {
				legalSquaresToMoveTo.add(board.getSquares()[xNW--][yNW++]);
			}
		}
		
		return legalSquaresToMoveTo;
	}
	
	protected LinkedList<Square> getLegalSquaresLinearly(Board board, Square initialSquare){
		LinkedList<Square> legalSquaresToMoveTo = new LinkedList<>();
		
		int x = initialSquare.getCoordinateX();
		int y = initialSquare.getCoordinateY();
		
		for(int N = y + 1; N < 8; N++) {
			if(board.getSquares()[x][N].getPiece() != null) {
				if(board.getSquares()[x][N].getPiece().isColoredWhite() != this.isColoredWhite()) {
					legalSquaresToMoveTo.add(board.getSquares()[x][N]);
				}
				break;
			} else {
				legalSquaresToMoveTo.add(board.getSquares()[x][N]);
			}
		}
		
		for(int E = x + 1; E < 8; E++) {
			if(board.getSquares()[E][y].getPiece() != null) {
				if(board.getSquares()[E][y].getPiece().isColoredWhite() != this.isColoredWhite()) {
					legalSquaresToMoveTo.add(board.getSquares()[E][y]);
				}
				break;
			} else {
				legalSquaresToMoveTo.add(board.getSquares()[E][y]);
			}
		}
		
		for(int S = y - 1; 0 <= S; S--) {
			if(board.getSquares()[x][S].getPiece() != null) {
				if(board.getSquares()[x][S].getPiece().isColoredWhite() != this.isColoredWhite()) {
					legalSquaresToMoveTo.add(board.getSquares()[x][S]);
				}
				break;
			} else {
				legalSquaresToMoveTo.add(board.getSquares()[x][S]);
			}
		}
		
		for(int W = x - 1; 0 <= W; W--) {
			if(board.getSquares()[W][y].getPiece() != null) {
				if(board.getSquares()[W][y].getPiece().isColoredWhite() != this.isColoredWhite()) {
					legalSquaresToMoveTo.add(board.getSquares()[W][y]);
				}
				break;
			} else {
				legalSquaresToMoveTo.add(board.getSquares()[W][y]);
			}
		}
		
		return legalSquaresToMoveTo;
	}
	
	public abstract LinkedList<Square> getLegalSquaresToMoveTo();
}
