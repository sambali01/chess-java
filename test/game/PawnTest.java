package game;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PawnTest {
	Board b;

	@Before
	public void setUp() throws Exception {
		b = new Board();
		b.clearBoard();
		
		King whiteKing = new King(b.getSquares()[4][0], true, "whiteking");
		b.getSquares()[4][0].setPiece(whiteKing);
		King blackKing = new King(b.getSquares()[4][7], false, "blackking");
		b.getSquares()[4][7].setPiece(blackKing);
		b.getSquares()[2][6].setPiece(new Pawn(b.getSquares()[2][6], false, "blackpawn"));
		b.getSquares()[3][4].setPiece(new Pawn(b.getSquares()[3][4], true, "whitepawn"));
		b.getSquares()[3][5].setPiece(new Rook(b.getSquares()[3][5], true, "whiterook"));
		b.getSquares()[4][5].setPiece(new Knight(b.getSquares()[4][5], false, "blackknight"));
		b.getSquares()[7][1].setPiece(new Pawn(b.getSquares()[7][1], true, "whitepawn"));
		
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
	}

	@Test
	public void testGetLegalSquaresToMoveTo() {
		LinkedList<Square> blackPawnMoves = b.getSquares()[2][6].getPiece().getLegalSquaresToMoveTo();
		Assert.assertTrue(blackPawnMoves.contains(b.getSquares()[2][4])); // moves two squares
		Assert.assertTrue(blackPawnMoves.contains(b.getSquares()[2][5])); // moves one square
		
		LinkedList<Square> whiteh2PawnMoves = b.getSquares()[7][1].getPiece().getLegalSquaresToMoveTo();
		Assert.assertTrue(whiteh2PawnMoves.contains(b.getSquares()[7][2])); // moves one square
		Assert.assertTrue(whiteh2PawnMoves.contains(b.getSquares()[7][3])); // moves two squares
		
		LinkedList<Square> whited5PawnMoves = b.getSquares()[3][4].getPiece().getLegalSquaresToMoveTo();
		Assert.assertFalse(whited5PawnMoves.contains(b.getSquares()[3][5]));
	}
	
	@Test
	public void testMove() {
		b.getSquares()[2][6].getPiece().move(b.getSquares()[2][4]); // [2][5] will be enpassent square
		Assert.assertTrue(b.getSquares()[2][4].getPiece().getClass().equals(Pawn.class)
				&& !b.getSquares()[2][4].getPiece().isColoredWhite());
		LinkedList<Square> whited5PawnMoves = b.getSquares()[3][4].getPiece().getLegalSquaresToMoveTo();
		Assert.assertTrue(whited5PawnMoves.contains(b.getSquares()[2][5])); // enpassent
	}

}
