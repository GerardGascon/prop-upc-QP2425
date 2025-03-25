package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

public class CatalanPiecesConverter extends PiecesConverter {
    @Override
    protected Piece parseSpecialPiece(char c, int i, String word) {
        // CATALÀ -> L·L
        if (c == 'L' && i < (word.length() - 2) && word.charAt(i + 1) == '·')
            return new Piece(word.substring(i, i + 3), 0);
        // CATALÀ -> NY
        if (c == 'N' && i < (word.length() - 1) && word.charAt(i + 1) == 'Y')
            return new Piece(word.substring(i, i + 2), 0);
        return null;
    }
}
