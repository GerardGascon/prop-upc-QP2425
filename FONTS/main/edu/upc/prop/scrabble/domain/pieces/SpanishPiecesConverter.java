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
        //CASTELLÀ -> RR
        if (c == 'R' && i < (word.length() - 1) && word.charAt(i + 1) == 'R')
            return new Piece(word.substring(i, i + 2), 0);
        //CASTELLÀ -> LL
        if (c == 'L' && i < (word.length() - 1) && word.charAt(i + 1) == 'L')
            return new Piece(word.substring(i, i + 2), 0);
        //CASTELLÀ -> CH
        if (c == 'C' && i < (word.length() - 1) && word.charAt(i + 1) == 'H')
            return new Piece(word.substring(i, i + 2), 0);
        return null;
    }
}
