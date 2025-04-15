package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.TurnResult;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;

public abstract class PlayerObject extends SceneObject implements IGamePlayer {
    private boolean onTurn = false;
    @Override
    public final void startTurn() {
        onTurn = true;
    }

    @Override
    public final TurnResult endTurn() {
        onTurn = false;
        //TODO: Replace this
        return TurnResult.Place;
    }

    @Override
    public final boolean isActive() {
        return onTurn;
    }
}
