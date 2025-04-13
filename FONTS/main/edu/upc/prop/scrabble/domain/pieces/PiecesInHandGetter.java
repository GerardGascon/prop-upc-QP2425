package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.Objects;
import java.util.Vector;

public class PiecesInHandGetter {
    private final Player player;
    private final Bag bag;
    private final IPiecePrinter piecePrinter;

    public PiecesInHandGetter(Bag b, Player p, IPiecePrinter piecePrinter) {
        player = p;
        bag = b;
        this.piecePrinter = piecePrinter;
    }

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
            throw new RuntimeException(e);
        }
        return pieces;
    }
}
