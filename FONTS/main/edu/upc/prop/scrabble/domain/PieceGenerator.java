package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Piece;

public class PieceGenerator {
    public Piece generatePiece(String piece) {
        char c = parseCharacter(piece);
        int value = parseValue(piece);

        return new Piece(c, value);
    }

    private int parseValue(String piece) {
        return 1;
    }

    private char parseCharacter(String piece) {
        return piece.charAt(0);
    }
}
