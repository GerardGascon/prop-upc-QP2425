package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Pair;

/**
 * Class used to generate pieces based on the contents of the pieces file
 * @author Gerard Gascón
 */
public class PieceGenerator {
    /**
     * Convert the piece file into Piece classes
     * @param pieces The contents of the pieces file
     * @return A tuple containing the Piece info and the number of pieces of that type
     * @see Pair
     * @see Piece
     */
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
        boolean isBlank = character.equals("#");

        return new Pair<>(new Piece(character, value, isBlank), count);
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
