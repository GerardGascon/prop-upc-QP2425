package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;

public abstract class PlayerObject extends SceneObject implements IGamePlayer {
    protected boolean onTurn = false;
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
