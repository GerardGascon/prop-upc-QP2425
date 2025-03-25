package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.Player;

import java.util.ArrayList;
import java.util.Vector;

public class PiecesInHandVerifier {
    private final Player player;
    private final PiecesConverter piecesConverter;

    public PiecesInHandVerifier(Player p, PiecesConverter piecesConverter) {
        this.piecesConverter = piecesConverter;
        player = p;
    }

    public Piece[] run(String word) {
        Vector<Piece> hand = player.getHand();
        Piece[] piecesInWord = piecesConverter.run(word);
        ArrayList<Piece> piecesInHand = new ArrayList<>();

        for (Piece piece : piecesInWord) {
            for (int j = 0; j < hand.size(); j++) {
                if (hand.elementAt(j).equals(piece)) {
                    piecesInHand.add(hand.elementAt(j));
                }
            }
        }

        return piecesInHand.toArray(new Piece[0]);
    }

}
