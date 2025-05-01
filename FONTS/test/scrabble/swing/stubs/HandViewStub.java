package scrabble.swing.stubs;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IHandView;

public class HandViewStub implements IHandView {
    private boolean getPieceRequestCalled;

    public boolean getGetPieceRequestCalled() {
        return getPieceRequestCalled;
    }

    @Override
    public String getSelectedPiece() {
        getPieceRequestCalled = true;
        return null;
    }
}
