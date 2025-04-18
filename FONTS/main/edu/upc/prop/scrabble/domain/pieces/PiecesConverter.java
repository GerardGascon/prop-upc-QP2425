
package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.ArrayList;

/***
 * Class that converts a string of letters into pieces in English.
 * @author Gina Escofet González
 */
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

            String character = Character.toString(Character.toUpperCase(c));
            pieces.add(new Piece(character, 0, Character.isLowerCase(c)));
            i++;
        }
        return pieces.toArray(new Piece[0]);
    }

    protected Piece parseSpecialPiece(char c, int i, String word) {
        return null;
    }
}
