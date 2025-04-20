package scrabble.stubs;

import edu.upc.prop.scrabble.domain.turns.IGamePlayer;

public class GamePlayerStub implements IGamePlayer {
    private boolean onTurn = false;

    @Override
    public void startTurn() {
        onTurn = true;
    }

    @Override
    public void endTurn() {
        onTurn = false;
    }

    @Override
    public boolean isActive() {
        return onTurn;
    }
}
