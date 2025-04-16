package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.TurnResult;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;

public abstract class PlayerObject extends SceneObject implements IGamePlayer {
    private boolean onTurn = false;
    protected Player player;
    protected MovementMaker movementMaker;

    public final void configure(MovementMaker movementMaker, Player player) {
        this.player = player;
        this.movementMaker = movementMaker;
    }

    @Override
    public final void startTurn() {
        System.out.println("Player \"" + player.getName() + "\" starts turn");
        printPieces();
        onTurn = true;
    }

    private void printPieces() {
        System.out.print("Pieces:");
        for (Piece piece : player.getHand())
            System.out.print(" " + piece.letter() + ",");
        System.out.println();
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
