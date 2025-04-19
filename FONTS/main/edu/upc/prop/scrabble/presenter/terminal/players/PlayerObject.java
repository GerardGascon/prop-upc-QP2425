package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;

public abstract class PlayerObject extends SceneObject implements IGamePlayer {
    private boolean onTurn = false;
    protected Player player;
    private PlaceActionMaker placeActionMaker;
    private DrawActionMaker drawActionMaker;
    private SkipActionMaker skipActionMaker;

    public final void configure(PlaceActionMaker placeActionMaker, Player player, DrawActionMaker drawActionMaker, SkipActionMaker skipActionMaker) {
        this.placeActionMaker = placeActionMaker;
        this.player = player;
        this.drawActionMaker = drawActionMaker;
        this.skipActionMaker = skipActionMaker;
    }

    @Override
    public void startTurn() {
        System.out.println("Player \"" + player.getName() + "\" starts turn");
        onTurn = true;
    }

    protected void printPieces() {
        System.out.print("Pieces:");
        for (Piece piece : player.getHand())
            System.out.print(" " + piece.letter() + ",");
        System.out.println();
    }

    protected final void placePiece(Movement movement) {
        placeActionMaker.run(movement);
    }

    protected final void drawPieces(Piece[] piece) {
        drawActionMaker.run(piece);
    }

    protected final void skipTurn() {
        skipActionMaker.run();
    }

    @Override
    public final void endTurn() {
        onTurn = false;
    }

    @Override
    public final boolean isActive() {
        return onTurn;
    }
}
