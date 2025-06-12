package game;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KnightTest {
	Board b;

	@Before
	public void setUp() throws Exception {
		b = new Board();
		b.clearBoard();
		
		King whiteKing = new King(b.getSquares()[4][0], true, "whiteking");
		b.getSquares()[4][0].setPiece(whiteKing);
		King blackKing = new King(b.getSquares()[4][7], false, "blackking");
		b.getSquares()[4][7].setPiece(blackKing);
		b.getSquares()[1][0].setPiece(new Knight(b.getSquares()[1][0], true, "whiteknight"));
		b.getSquares()[0][2].setPiece(new Pawn(b.getSquares()[0][2], false, "blackpawn"));
		b.getSquares()[0][1].setPiece(new Pawn(b.getSquares()[0][1], false, "blackpawn"));
		b.getSquares()[3][1].setPiece(new Rook(b.getSquares()[3][1], true, "whiterook"));
		
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
		LinkedList<Square> knightMoves = b.getSquares()[1][0].getPiece().getLegalSquaresToMoveTo();
		Assert.assertTrue(knightMoves.contains(b.getSquares()[0][2]));
		Assert.assertTrue(knightMoves.contains(b.getSquares()[2][2]));
		Assert.assertFalse(knightMoves.contains(b.getSquares()[3][1]));
	}

}
