package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.TurnResult;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;

public abstract class PlayerObject extends SceneObject implements IGamePlayer {
    private boolean onTurn = false;
    protected Player player;
    protected MovementMaker movementMaker;
    private TurnResult turnResult;
    private PlaceActionMaker placeActionMaker;

    public final void configure(PlaceActionMaker placeActionMaker, MovementMaker movementMaker, Player player) {
        this.placeActionMaker = placeActionMaker;
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

    protected final void placePiece(Movement movement) {
        turnResult = TurnResult.Place;
        placeActionMaker.run(movement);
    }

    protected final void drawPiece(Piece piece) {
        turnResult = TurnResult.Draw;
    }

    protected final void skipTurn() {
        turnResult = TurnResult.Skip;
    }

    @Override
    public final TurnResult endTurn() {
        onTurn = false;
        return turnResult;
    }

    @Override
    public final boolean isActive() {
        return onTurn;
    }
}
