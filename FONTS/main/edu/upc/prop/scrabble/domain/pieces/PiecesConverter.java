
package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.ArrayList;

// CASOS CATALÀ: L·L, NY
// CASOS CASTELLÀ: LL, RR, CH
// CASOS ANGLÈS: cap
public class PiecesConverter {
    public Piece[] run(String word) {
        ArrayList<Piece> pieces = new ArrayList<>();
        int i = 0;
        while (i < word.length()) {
            char c = word.charAt(i);

            Piece specialPiece = parseSpecialPiece(c, i, word);
            if (specialPiece != null) {
                pieces.add(specialPiece);
                i += specialPiece.letter().length();
                continue;
            }

            pieces.add(new Piece(Character.toString(c), 0));
            i++;
        }
        return pieces.toArray(new Piece[0]);
    }

    protected Piece parseSpecialPiece(char c, int i, String word) {
        return null;
    }
}
