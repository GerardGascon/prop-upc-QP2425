package scrabble.stubs;

import edu.upc.prop.scrabble.domain.IBoard;

public class BoardViewStub implements IBoard {
    private boolean UpdateCallReceived = false;

    public boolean getUpdateCallReceived() {
        return UpdateCallReceived;
    }

    @Override
    public void UpdateBoard() {
        UpdateCallReceived = true;
    }
}
