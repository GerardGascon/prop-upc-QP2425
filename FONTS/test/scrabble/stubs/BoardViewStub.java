package scrabble.stubs;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.domain.board.IBoard;

public class BoardViewStub implements IBoard {
    private boolean UpdateCallReceived = false;

    public boolean getUpdateCallReceived() {
        return UpdateCallReceived;
    }

    @Override
    public void UpdateBoard(Board board) {
        UpdateCallReceived = true;
    }
}
