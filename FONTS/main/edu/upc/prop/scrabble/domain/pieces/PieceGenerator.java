package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Pair;

/**
 * Class responsible for generating `Piece` objects based on the contents of a pieces file.
 * <p>
 * The `PieceGenerator` class parses the contents of a pieces file (a string) to create `Piece` instances.
 * Each `Piece` represents a letter, its value, and the number of occurrences of that letter. The result is returned
 * as an array of `Pair&lt;Piece, Integer&gt;`, where `Piece` represents the letter and its properties, and the integer represents
 * the count of that piece.
 * </p>
 *
 * @author Gerard Gascón
 */
public class PieceGenerator {
    /**
     * Converts the contents of a pieces file into an array of `Piece` objects and their respective counts.
     * <p>
     * The file is parsed line by line, and each line is expected to contain information about a specific piece's
     * character, count, and value. The result is an array of `Pair&lt;Piece, Integer&gt;`, where each pair contains a `Piece`
     * object and the number of occurrences of that piece.
     * </p>
     *
     * @param pieces The contents of the pieces file as a string.
     * @return An array of pairs, where each pair contains a `Piece` and the number of occurrences of that piece.
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
