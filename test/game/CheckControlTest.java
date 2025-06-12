package game;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckControlTest {
	Board b;

	@Before
	public void setUp() throws Exception {
		b = new Board();
		b.clearBoard();
	}

	@Test
	public void testDoesMoveIntoCheck() {
		King whiteKing = new King(b.getSquares()[4][0], true, "whiteking");
		b.getSquares()[4][0].setPiece(whiteKing);
		King blackKing = new King(b.getSquares()[4][7], false, "blackking");
		b.getSquares()[4][7].setPiece(blackKing);
		b.getSquares()[7][6].setPiece(new Rook(b.getSquares()[7][6], true, "whiterook"));
		b.getSquares()[2][6].setPiece(new Pawn(b.getSquares()[2][6], true, "whitepawn"));
		
		b.whitePieces = new LinkedList<>();
		b.blackPieces = new LinkedList<>();
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				if(b.getSquares()[x][y].getPiece() != null) {
					if(b.getSquares()[x][y].getPiece().isColoredWhite()) {
						b.whitePieces.add(b.getSquares()[x][y].getPiece());
					} else {
						b.blackPieces.add(b.getSquares()[x][y].getPiece());
					}
				}
			}
		}
		
		b.setCheckControl(new CheckControl(b, b.whitePieces, b.blackPieces, whiteKing, blackKing));
		b.setWhiteToMove(false);
		
		b.setCheckControl(new CheckControl(b, b.whitePieces, b.blackPieces, whiteKing, blackKing));
		b.setWhiteToMove(false);
		Assert.assertTrue(b.getCheckControl().doesMoveIntoCheck(false, b.getSquares()[4][7].getPiece(), b.getSquares()[4][6]));
		Assert.assertTrue(b.getCheckControl().doesMoveIntoCheck(false, b.getSquares()[4][7].getPiece(), b.getSquares()[3][7]));
	}
	
	@Test
	public void testBlackIsCheckMated() {
		King whiteKing = new King(b.getSquares()[4][5], true, "whiteking"); // Ke6
		b.getSquares()[4][5].setPiece(whiteKing);
		King blackKing = new King(b.getSquares()[4][7], false, "blackking"); // Ke8
		b.getSquares()[4][7].setPiece(blackKing);
		b.getSquares()[7][7].setPiece(new Rook(b.getSquares()[7][7], true, "whiterook")); // Rh8
		
		b.whitePieces = new LinkedList<>();
		b.blackPieces = new LinkedList<>();
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				if(b.getSquares()[x][y].getPiece() != null) {
					if(b.getSquares()[x][y].getPiece().isColoredWhite()) {
						b.whitePieces.add(b.getSquares()[x][y].getPiece());
					} else {
						b.blackPieces.add(b.getSquares()[x][y].getPiece());
					}
				}
			}
		}
		
		b.setCheckControl(new CheckControl(b, b.whitePieces, b.blackPieces, whiteKing, blackKing));
		b.setWhiteToMove(false);
		
		Assert.assertTrue(b.getCheckControl().blackIsCheckmated());
	}
	
	@Test
	public void testWhiteIsCheckMated() {
		b.setUpStartingPosition();
		b.getSquares()[5][1].getPiece().move(b.getSquares()[5][3]); // 1. f4
		b.getSquares()[4][6].getPiece().move(b.getSquares()[4][4]); // 1. ... e5
		b.getSquares()[6][1].getPiece().move(b.getSquares()[6][3]); // 2. g4
		b.getSquares()[3][7].getPiece().move(b.getSquares()[7][3]); // 2. ... Qh4#
		
		Assert.assertTrue(b.getCheckControl().whiteIsCheckmated());
	}
	
	@Test
	public void testStaleMateHappened() {
		King whiteKing = new King(b.getSquares()[7][3], true, "whiteking"); // Kh4
		b.getSquares()[7][3].setPiece(whiteKing);
		King blackKing = new King(b.getSquares()[4][7], false, "blackking"); // Ke8
		b.getSquares()[4][7].setPiece(blackKing);
		b.getSquares()[7][2].setPiece(new Pawn(b.getSquares()[7][2], true, "whitepawn")); // h3
		b.getSquares()[5][2].setPiece(new Pawn(b.getSquares()[5][2], true, "whitepawn")); // f3
		b.getSquares()[7][4].setPiece(new Pawn(b.getSquares()[7][4], false, "blackpawn")); // h5
		b.getSquares()[5][3].setPiece(new Pawn(b.getSquares()[5][3], false, "blackpawn")); // f4
		b.getSquares()[5][5].setPiece(new Pawn(b.getSquares()[5][5], false, "blackpawn")); // f6
		b.getSquares()[5][6].setPiece(new Bishop(b.getSquares()[5][6], false, "blackbishop")); // Bf7
		
		b.whitePieces = new LinkedList<>();
		b.blackPieces = new LinkedList<>();
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				if(b.getSquares()[x][y].getPiece() != null) {
					if(b.getSquares()[x][y].getPiece().isColoredWhite()) {
						b.whitePieces.add(b.getSquares()[x][y].getPiece());
					} else {
						b.blackPieces.add(b.getSquares()[x][y].getPiece());
					}
				}
			}
		}
		
		b.setCheckControl(new CheckControl(b, b.whitePieces, b.blackPieces, whiteKing, blackKing));
		
		Assert.assertTrue(b.getCheckControl().staleMateHappened());
	}

}
