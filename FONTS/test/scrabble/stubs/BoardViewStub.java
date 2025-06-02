package scrabble.stubs;

import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.domain.board.IBoard;

public class BoardViewStub implements IBoard {
    private boolean updateCallReceived = false;

    public boolean getUpdateCallReceived() {
        return updateCallReceived;
    }

    private int setTileTypeCallReceived;

    public int getSetTileTypeCallReceived() {
        return setTileTypeCallReceived;
    }

    @Override
    public void updateCell(String piece, int points, int x, int y) {
        updateCallReceived = true;
    }

    @Override
    public void setPremiumTile(PremiumTileType type, int x, int y) {
        setTileTypeCallReceived++;
    }
}
