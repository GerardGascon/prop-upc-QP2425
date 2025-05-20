package edu.upc.prop.scrabble.presenter.swing.objects;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;

public abstract class PlayerObject extends SceneObject implements IGamePlayer {
    private int playerIndex;
    private boolean onTurn = false;
    protected Player player;
    private PlaceActionMaker placeActionMaker;
    private DrawActionMaker drawActionMaker;
    private SkipActionMaker skipActionMaker;
    private IHandView handView;

    public final void configure(int playerIndex, PlaceActionMaker placeActionMaker, Player player, DrawActionMaker drawActionMaker, SkipActionMaker skipActionMaker, IHandView handView) {
        this.playerIndex = playerIndex;
        this.placeActionMaker = placeActionMaker;
        this.player = player;
        this.drawActionMaker = drawActionMaker;
        this.skipActionMaker = skipActionMaker;
        this.handView = handView;
    }

    @Override
    public void startTurn() {
        onTurn = true;
        handView.showPieces(player.getHand());
    }

    protected final void placePiece(Movement movement) {
        try {
            placeActionMaker.run(movement);
        } catch (ScrabbleException e) {
            skipActionMaker.run();
        }
    }

    protected final void drawPieces(Piece[] piece) {
        try {
            String[] word = new String[piece.length];
            for (int i = 0; i < piece.length; i++) {
                word[i] = piece[i].letter();
            }
            drawActionMaker.run(word);
        } catch (ScrabbleException e) {
            skipActionMaker.run();
        }
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
