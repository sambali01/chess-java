package game;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueenTest {
	Board b;

	@Before
	public void setUp() throws Exception {
		b = new Board();
		b.clearBoard();
		
		King whiteKing = new King(b.getSquares()[4][0], true, "whiteking");
		b.getSquares()[4][0].setPiece(whiteKing);
		King blackKing = new King(b.getSquares()[4][7], false, "blackking");
		b.getSquares()[4][7].setPiece(blackKing);
		b.getSquares()[5][6].setPiece(new Queen(b.getSquares()[5][6], false, "blackqueen"));
		b.getSquares()[3][4].setPiece(new Pawn(b.getSquares()[3][4], false, "blackpawn"));
		b.getSquares()[2][6].setPiece(new Rook(b.getSquares()[2][6], true, "whiterook"));
		
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
		LinkedList<Square> queenMoves = b.getSquares()[5][6].getPiece().getLegalSquaresToMoveTo();
		Assert.assertTrue(queenMoves.contains(b.getSquares()[6][7]));
		Assert.assertFalse(queenMoves.contains(b.getSquares()[4][7]));
		Assert.assertTrue(queenMoves.contains(b.getSquares()[2][6]));
		Assert.assertTrue(queenMoves.contains(b.getSquares()[3][6]));
		Assert.assertFalse(queenMoves.contains(b.getSquares()[0][6]));
		Assert.assertFalse(queenMoves.contains(b.getSquares()[2][3]));
	}

}
