package game;

import java.util.ArrayList;
import java.util.Collections;

public class RandomBot extends Player {

	public RandomBot(Board board) {
		super(board);
	}

	@Override
	public void makeMove() {
		ArrayList<Piece> piecesThatCanMove = new ArrayList<>();
		for(Piece movingPiece : this.getBoard().blackPieces) {
			for(Square targetSquare : movingPiece.getLegalSquaresToMoveTo()) {
				if(!this.getBoard().getCheckControl().doesMoveIntoCheck(false, movingPiece, targetSquare)) {
					piecesThatCanMove.add(movingPiece);
				}
			}
		}
		Collections.shuffle(piecesThatCanMove);
		
		if(!piecesThatCanMove.isEmpty()) {
			ArrayList<Square> targetsquares = new ArrayList<>();
			for(Square targetSquare : piecesThatCanMove.get(0).getLegalSquaresToMoveTo()) {
				if(!this.getBoard().getCheckControl().doesMoveIntoCheck(false, piecesThatCanMove.get(0), targetSquare)) {
					targetsquares.add(targetSquare);
				}
			}
			Collections.shuffle(targetsquares);
			
			piecesThatCanMove.get(0).move(targetsquares.get(0));
		}
	}

}
