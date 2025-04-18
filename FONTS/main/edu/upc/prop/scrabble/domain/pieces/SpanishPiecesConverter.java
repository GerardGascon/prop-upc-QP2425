package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
/***
 * Class that converts a string of letters into pieces in Spanish.
 * It handles cases like RR, LL, CH
 * @author Gina Escofet González
 */
public class SpanishPiecesConverter extends PiecesConverter {
    @Override
    protected Piece parseSpecialPiece(char c, int i, String word) {
        boolean isBlank = Character.isLowerCase(c);
        //CASTELLÀ -> RR
        if ((c == 'R' || c == 'r') && i < (word.length() - 1) && (word.charAt(i + 1) == 'R' || word.charAt(i + 1) == 'r'))
            return new Piece(word.substring(i, i + 2).toUpperCase(), 0, isBlank);
        //CASTELLÀ -> LL
        if ((c == 'L' || c == 'l') && i < (word.length() - 1) && (word.charAt(i + 1) == 'L' || word.charAt(i + 1) == 'l'))
            return new Piece(word.substring(i, i + 2).toUpperCase(), 0, isBlank);
        //CASTELLÀ -> CH
        if ((c == 'C' || c == 'c') && i < (word.length() - 1) && (word.charAt(i + 1) == 'H' || word.charAt(i + 1) == 'h'))
            return new Piece(word.substring(i, i + 2).toUpperCase(), 0, isBlank);
        return null;
    }
}
