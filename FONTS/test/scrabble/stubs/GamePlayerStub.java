package scrabble.stubs;

import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.TurnResult;

public class GamePlayerStub implements IGamePlayer {
    @Override
    public void startTurn() {

    }

    @Override
    public TurnResult endTurn() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
