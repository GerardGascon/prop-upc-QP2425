
package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

/***
 * Class that converts a string of letters into pieces in English.
 * @author Gina Escofet González
 */
public class EnglishPiecesConverter extends PiecesConverter {
    public EnglishPiecesConverter() {
        super();
    }

    public EnglishPiecesConverter(Piece[] dictionary) {
        super(dictionary);
    }
}
