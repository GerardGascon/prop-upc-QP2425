package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.utils.Pair;

public class PieceGenerator {
    public Pair<Piece, Integer>[] run(String pieces) {
        String[] pieceArray = pieces.lines().toArray(String[]::new);
        Pair<Piece, Integer>[] result = new Pair[pieceArray.length];

        for (int i = 0; i < pieceArray.length; i++) {
            result[i] = generatePiece(pieceArray[i]);
        }

        return result;
    }

    private Pair<Piece, Integer> generatePiece(String piece) {
        String character = parseCharacter(piece);
        int value = parseValue(piece);
        int count = parseCount(piece);

        return new Pair<>(new Piece(character, value), count);
    }

    private int parseCount(String piece) {
        String[] values = piece.split("\\s+");
        return Integer.parseInt(values[1]);
    }

    private int parseValue(String piece) {
        String[] values = piece.split("\\s+");
        return Integer.parseInt(values[2]);
    }

    private String parseCharacter(String piece) {
        String[] values = piece.split("\\s+");
        return values[0];
    }
}
