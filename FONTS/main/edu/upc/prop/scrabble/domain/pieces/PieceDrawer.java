package edu.upc.prop.scrabble.domain.pieces;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.exceptions.NotEnoughPiecesInBagException;
import edu.upc.prop.scrabble.utils.IRand;

import java.lang.reflect.InaccessibleObjectException;
import java.util.*;

/***
 * Handles the exchange of pieces between a player's hand and the game bag. It returns unwanted pieces to the bag and
 * draw new random pieces from the bag.
 * @author Gina Escofet GonzÃ¡lez
 */
public class PieceDrawer {
    private final Bag bag;
    private final Player player;
    private final IRand rand;

    /***
     * Creates a PieceDrawer for the specified game bag and player.
     * @param bag The game piece bag.
     * @param player The player making the exchange.
     */
    public PieceDrawer(Bag bag, Player player, IRand rand) {
        this.bag = bag;
        this.player = player;
        this.rand = rand;
    }

    /***
     * Exchanges pieces between the player's hand and the bag.
     * @param piecesToSwap Pieces to return to the bag.
     */
    public void run(Piece[] piecesToSwap) {
        if (bag.getSize() < piecesToSwap.length)
            throw new NotEnoughPiecesInBagException("Not enough pieces in bag");

        List<Piece> drawnPieces = collectPiecesFromBag(piecesToSwap);
        swapPiecesWithPlayer(piecesToSwap);
        for (Piece piece : drawnPieces)
            player.addPiece(piece);
    }

    private void swapPiecesWithPlayer(Piece[] piecesToSwap) {
        for (Piece piece : piecesToSwap) {
            player.removePiece(piece);
            bag.add(piece);
        }
    }

    private List<Piece> collectPiecesFromBag(Piece[] piecesToSwap) {
        List<Piece> drawnPieces = new ArrayList<>();
        for (int i = 0; i < piecesToSwap.length; i++) {
            Piece randomBagPiece = bag.draw(rand.nextInt(bag.getSize()));
            drawnPieces.add(randomBagPiece);
        }
        return drawnPieces;
    }
}