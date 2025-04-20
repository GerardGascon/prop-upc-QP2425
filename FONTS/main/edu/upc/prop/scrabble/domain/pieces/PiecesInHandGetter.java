package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.exceptions.PlayerDoesNotHavePieceException;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.IRand;

import java.util.*;

/***
 * Manages the retrieval and verification of pieces from a player's hand.
 * @author Gina Escofet González
 */
public class PiecesInHandGetter {
    private final Player player;
    private final Bag bag;
    private final IRand rand;

    /***
     * Constructs a new PiecesInHandGetter with the specified bag, player and piecePrinter.
     * @param bag The game piece bag.
     * @param player The target player.
     */
    public PiecesInHandGetter(Bag bag, Player player, IRand rand) {
        this.player = player;
        this.bag = bag;
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
        if (!hasPieces(pieces))
            throw new PlayerDoesNotHavePieceException("Player " + player.getName() + " does not have all the pieces");

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

    private boolean hasPieces(Piece[] pieces) {
        List<Piece> hand = new ArrayList<>(Arrays.asList(player.getHand()));

        for (Piece piece : pieces) {
            if (!hasPiece(piece, hand))
                return false;
        }

        return true;
    }

    private boolean hasPiece(Piece piece, List<Piece> pieces) {
        if (piece.isBlank()){
            for (int i = 0; i < pieces.size(); i++) {
                if (pieces.get(i).isBlank()) {
                    pieces.remove(i);
                    return true;
                }
            }
            return false;
        }

        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).letter().equals(piece.letter())) {
                pieces.remove(i);
                return true;
            }
        }
        return false;
    }
}