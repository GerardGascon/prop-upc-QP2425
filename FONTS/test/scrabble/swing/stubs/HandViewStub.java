package scrabble.swing.stubs;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;

public class HandViewStub implements IHandView {
    private boolean getPieceRequestCalled;
    private final String pieceName;
    private final int pieceNumber;

    public HandViewStub(String pieceName, int pieceNumber) {
        this.pieceName = pieceName;
        this.pieceNumber = pieceNumber;
    }

    public boolean getGetPieceRequestCalled() {
        return getPieceRequestCalled;
    }

    @Override
    public String getSelectedPiece() {
        getPieceRequestCalled = true;
        return pieceName;
    }

    @Override
    public int getSelectedPiecePoints() {
        getPieceRequestCalled = true;
        return pieceNumber;
    }

    @Override
    public void showPieces(Piece[] pieces) {

    }
}
