package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Piece;

public class PieceGenerator {
    public Piece[] run(String pieces) {
        String[] pieceArray = pieces.lines().toArray(String[]::new);
        Piece[] result = new Piece[pieceArray.length];

        for (int i = 0; i < pieceArray.length; i++) {
            result[i] = generatePiece(pieceArray[i]);
        }

        return result;
    }

    private Piece generatePiece(String piece) {
        char c = parseCharacter(piece);
        int value = parseValue(piece);

        return new Piece(c, value);
    }

    private int parseValue(String piece) {
        String[] values = piece.split("\\s+");
        return Integer.parseInt(values[2]);
    }

    private char parseCharacter(String piece) {
        return piece.charAt(0);
    }
}
