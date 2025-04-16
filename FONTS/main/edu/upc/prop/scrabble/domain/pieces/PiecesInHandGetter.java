package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;

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

    /***
     * Constructs a new PiecesInHandGetter with the specified bag, player and piecePrinter.
     * @param bag The game piece bag.
     * @param player The target player.
     * @param piecePrinter The piece display handler.
     */
    public PiecesInHandGetter(Bag bag, Player player, IPiecePrinter piecePrinter) {
        this.player = player;
        this.bag = bag;
        this.piecePrinter = piecePrinter;
    }

    /***
     * Verifies and exchanges pieces from the player's hand.
     * @param pieces Pieces needed for the current play.
     * @return The new pieces drawn from the bag.
     * @throws IllegalStateException if process fails.
     */
    public Piece[] run(Piece[] pieces) {
        try {
            Vector<Piece> hand = player.getHand();
            int count = 0;
            boolean[] visit = new boolean[hand.size()];

            for (int i = 0; i < pieces.length;++i) {
                for (int j = 0; j < hand.size(); ++j) {
                    if (!visit[j] && Objects.equals(pieces[i], hand.get(j))) {
                        visit[j] = true;
                        count += 1;
                        break;
                    }
                }
            }
            if (count == pieces.length) { // tinc totes les peces a la mà per fer la paraula

                PieceDrawer pc = new PieceDrawer(bag, player);
                return pc.run(pieces);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return pieces;
    }
}
