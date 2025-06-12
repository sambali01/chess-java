package game;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KingTest {
	Board b;
	
	@Before
	public void setUp() throws Exception {
		b = new Board();
		b.clearBoard();
		
		King whiteKing = new King(b.getSquares()[4][0], true, "whiteking");
		b.getSquares()[4][0].setPiece(whiteKing);
		King blackKing = new King(b.getSquares()[4][7], false, "blackking");
		b.getSquares()[4][7].setPiece(blackKing);
		b.getSquares()[7][0].setPiece(new Rook(b.getSquares()[7][0], true, "whiterook"));
		
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
		LinkedList<Square> kingMoves = b.getSquares()[4][0].getPiece().getLegalSquaresToMoveTo();
		Assert.assertTrue(kingMoves.contains(b.getSquares()[6][0])); // castle square
		Assert.assertTrue(kingMoves.contains(b.getSquares()[5][0]));
		Assert.assertTrue(kingMoves.contains(b.getSquares()[5][1]));
		Assert.assertTrue(kingMoves.contains(b.getSquares()[4][1]));
	}
	
	@Test
	public void testMove() {
		b.getSquares()[4][0].getPiece().move(b.getSquares()[6][0]); // short castle
		Assert.assertTrue(b.getSquares()[6][0].getPiece().getClass().equals(King.class)
				&& b.getSquares()[6][0].getPiece().isColoredWhite());
		Assert.assertTrue(b.getSquares()[5][0].getPiece().getClass().equals(Rook.class)
				&& b.getSquares()[5][0].getPiece().isColoredWhite());
		
		King whiteKing = (King) b.getSquares()[6][0].getPiece();
		Assert.assertTrue(whiteKing.hasMoved() == true);
		Rook whiteRook = (Rook) b.getSquares()[5][0].getPiece();
		Assert.assertTrue(whiteRook.hasMoved() == true);
	}

}
