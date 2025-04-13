package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.Vector;

public class PiecesInHandGetter {
    private final Player player;
    private final Bag bag;

    public PiecesInHandGetter( Bag b, Player p) {
        player = p;
        bag = b;
    }

    public Piece[] run(Piece[] pieces) {
        try {
            Vector<Piece> hand = player.getHand();
            int count = 0;
            for (Piece requiredPieces : pieces) {
                for (Piece handPieces : hand) {
                    if (handPieces.equals(requiredPieces)) {
                        ++count;
                    }
                }
            }
            if (count == pieces.length) { // tinc totes les peces a la mà per fer la paraula
                // System.out.println("The player has the necessary pieces in hand to play the word.");
                // Printer.display(...);
                PieceDrawer pc = new PieceDrawer(bag, player);
                Piece[] newPieces = pc.run(pieces);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pieces;
    }
}
