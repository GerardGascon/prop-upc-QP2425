package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.TurnResult;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;

public abstract class PlayerObject extends SceneObject implements IGamePlayer {
    private boolean onTurn = false;
    private Player player;
    protected MovementMaker movementMaker;

    public final void configure(MovementMaker movementMaker, Player player) {
        this.player = player;
        this.movementMaker = movementMaker;
    }

    @Override
    public final void startTurn() {
        System.out.println("Player \"" + player.getName() + "\" starts turn");
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
