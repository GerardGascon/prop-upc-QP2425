package scrabble.mocks;

import edu.upc.prop.scrabble.domain.IBoard;

public class BoardViewMock implements IBoard {
    private boolean UpdateCallReceived = false;

    public boolean getUpdateCallReceived() {
        return UpdateCallReceived;
    }

    @Override
    public void UpdateBoard() {
        UpdateCallReceived = true;
    }
}
