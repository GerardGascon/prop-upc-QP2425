
package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

/**
 * Classe que converteix una cadena de lletres en peces en anglès.
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
