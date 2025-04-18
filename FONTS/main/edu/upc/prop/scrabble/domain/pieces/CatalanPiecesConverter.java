package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
/***
 * Class that converts a string of letters into pieces in Catalan.
 * It handles cases like L·L and NY
 * @author Gina Escofet González
 */
public class CatalanPiecesConverter extends PiecesConverter {
    @Override
    protected Piece parseSpecialPiece(char c, int i, String word) {
        boolean isBlank = Character.isLowerCase(c);
        // CATALÀ -> L·L
        if ((c == 'L' || c == 'l') && i < (word.length() - 2) && word.charAt(i + 1) == '·')
            return new Piece(word.substring(i, i + 3).toUpperCase(), 0, isBlank);
        // CATALÀ -> NY
        if ((c == 'N' || c == 'n') && i < (word.length() - 1) && (word.charAt(i + 1) == 'Y' || word.charAt(i + 1) == 'y'))
            return new Piece(word.substring(i, i + 2).toUpperCase(), 0, isBlank);
        return null;
    }
}
