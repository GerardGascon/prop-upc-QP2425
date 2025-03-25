package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PiecesInHandVerifier {
    private final Player player;

    public PiecesInHandVerifier(Player p) {
        player = p;
    }

    public ArrayList<Piece> run(String word) {
        Vector<Piece> hand = player.getHand();
        PiecesConverter converter = new PiecesConverter();
        Piece[] piecesInWord = converter.run(word);
        ArrayList<Piece> piecesInHand = new ArrayList<>();

        for (int i = 0; i < piecesInWord.length; i++) {
            for (int j = 0; j < hand.size(); j++) {
                if (hand.elementAt(j).equals(piecesInWord[i])) {
                    piecesInHand.add(hand.elementAt(j));
                }
            }
        }

        return piecesInHand;
    }

}
