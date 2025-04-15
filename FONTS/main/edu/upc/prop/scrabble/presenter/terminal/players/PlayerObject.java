package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;

public class PlayerObject extends SceneObject implements IGamePlayer {
    private boolean onTurn = false;

    private Player player;
    private MovementMaker movementMaker;

    public void configure(Player player, MovementMaker movementMaker) {
        this.player = player;
        this.movementMaker = movementMaker;
    }

    @Override
    public void startTurn() {
        onTurn = true;
    }

    @Override
    public void endTurn() {
        onTurn = false;
    }

    @Override
    public void onProcess(float delta) {
        if (!onTurn)
            return;

        String movementRaw = Reader.getInstance().readLine();
        if (movementRaw == null)
            return;

        System.out.println(movementRaw);
        movementMaker.makeMove(movementRaw);
    }
}
