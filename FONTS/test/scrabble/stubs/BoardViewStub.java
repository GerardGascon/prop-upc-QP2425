package scrabble.stubs;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.domain.board.IBoard;

public class BoardViewStub implements IBoard {
    private boolean UpdateCallReceived = false;

    public boolean getUpdateCallReceived() {
        return UpdateCallReceived;
    }

    @Override
    public void updateBoard() {
        UpdateCallReceived = true;
    }

    @Override
    public void updateCell(String piece, int points, int x, int y) {
        UpdateCallReceived = true;
    }
}
