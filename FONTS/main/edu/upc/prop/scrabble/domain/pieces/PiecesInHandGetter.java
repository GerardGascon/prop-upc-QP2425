package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.IRand;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

/***
 * Manages the retrieval and verification of pieces from a player's hand.
 * @author Gina Escofet González
 */
public class PiecesInHandGetter {
    private final Player player;
    private final Bag bag;
    private final IPiecePrinter piecePrinter;
    private IRand rand;

    /***
     * Constructs a new PiecesInHandGetter with the specified bag, player and piecePrinter.
     * @param bag The game piece bag.
     * @param player The target player.
     * @param piecePrinter The piece display handler.
     */
    public PiecesInHandGetter(Bag bag, Player player, IPiecePrinter piecePrinter, IRand rand) {
        this.player = player;
        this.bag = bag;
        this.piecePrinter = piecePrinter;
        this.rand = rand;
    }

    /***
     * Verifies and exchanges pieces from the player's hand.
     * @param pieces Pieces needed for the current play.
     * @return The new pieces drawn from the bag.
     * @throws IllegalStateException if process fails.
     */
    public Piece[] run(Piece[] pieces) {
        List<Piece> piecesList = new Vector<>();
        for (Piece piece : pieces) {
            piecesList.add(player.removePiece(piece));
            if (!bag.isEmpty()) {
                int r = rand.nextInt(bag.getSize());
                Piece newPiece = bag.draw(r);
                player.addPiece(newPiece);
            }
        }
        return piecesList.toArray(Piece[]::new);
    }
}