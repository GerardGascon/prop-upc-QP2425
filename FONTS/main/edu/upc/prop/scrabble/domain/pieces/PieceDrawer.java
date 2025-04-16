package edu.upc.prop.scrabble.domain.pieces;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;

/***
 * Handles the exchange of pieces between a player's hand and the game bag. It returns unwanted pieces to the bag and
 * draw new random pieces from the bag.
 * @author Gina Escofet González
 */
public class PieceDrawer {
    private final Bag bag;
    private final Player player;

    /***
     * Creates a PieceDrawer for the specified game bag and player.
     * @param bag The game piece bag.
     * @param player The player making the exchange.
     * @throws IllegalArgumentException if player parameter is null
     */
    public PieceDrawer(Bag bag, Player player) {
        this.bag = bag;
        this.player = player;
    }

    /***
     * Exchanges pieces between the player's hand and the bag.
     * @param piecesToSwap Pieces to return to the bag.
     * @return New pieces drawn from the bag.
     */
    public Piece[] run(Piece[] piecesToSwap) {
        ArrayList<Piece> drawnPieces = new ArrayList<>();
        if (bag.isEmpty()) return drawnPieces.toArray(new Piece[0]);

        Random rand = new Random();
        Vector<Piece> hand = player.getHand();

        int n = piecesToSwap.length;
        int i = 0;
        while (i < n && !bag.isEmpty()) {
            int r = rand.nextInt(bag.getSize());
            Piece drawnPiece = bag.draw(r);
            drawnPieces.add(drawnPiece);
            ++i;
        }
        if (hand.isEmpty()) {       //inici->omplir la mà i ja
            for (int j = 0; j < drawnPieces.size(); ++j) {
                Piece p = drawnPieces.get(i);
                hand.add(p);
            }
        }
        else{   // durant partida
            for (i = 0; i < drawnPieces.size(); i++) {
                Piece oldPiece = piecesToSwap[i];
                player.RemovePiece(oldPiece);
                bag.add(oldPiece);
                player.AddPiece(drawnPieces.get(i));
                hand = player.getHand();
            }
        }
        return drawnPieces.toArray(new Piece[0]);
    }
}
