package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for identifying and completing words formed by the newly placed pieces
 * on the Scrabble board, as well as adjacent words formed by interacting with existing pieces.
 *
 * @author Gerard Gascón
 */
public class PresentPiecesWordCompleter {
    private final WordGetter wordGetter;

    /**
     * Constructs a PresentPiecesWordCompleter instance using the provided word getter.
     *
     * @param board The board where the game is being played.
     */
    public PresentPiecesWordCompleter(Board board) {
        this.wordGetter = new WordGetter(board);
    }

    /**
     * Runs the word completion logic for the given positions and pieces. It first determines the direction of the word
     * (either horizontal or vertical) and then retrieves any present words formed by the new pieces placed on the board.
     *
     * @param positions The positions of the newly placed pieces on the board.
     * @param pieces    The newly placed pieces.
     * @return An array of strings representing the words formed, both horizontally and vertically.
     * @see Piece
     */
    public String[] run(Vector2[] positions, Piece[] pieces) {
        Direction direction = getWordDirection(positions);
        return getPresentWords(positions, pieces, direction);
    }

    private String[] getPresentWords(Vector2[] positions, Piece[] pieces, Direction direction) {
        List<String> words = new ArrayList<>();
        if (direction == null) {
            words.addAll(Arrays.stream(getPresentWords(positions, pieces, Direction.Horizontal)).toList());
            words.addAll(Arrays.stream(getPresentWords(positions, pieces, Direction.Vertical)).toList());
            return words.toArray(String[]::new);
        }

        if (direction == Direction.Horizontal)
            return getWords(positions, pieces, direction, words);
        if (direction == Direction.Vertical)
            return getWords(positions, pieces, direction, words);

        return words.toArray(String[]::new);
    }

    private String[] getWords(Vector2[] positions, Piece[] pieces, Direction direction, List<String> words) {
        String word = getWord(positions, pieces, direction);
        if (word != null)
            words.add(word);
        if (pieces.length > 1)
            words.addAll(Arrays.stream(completeAdjacentWords(positions, pieces, direction)).toList());
        return words.toArray(String[]::new);
    }

    private String[] completeAdjacentWords(Vector2[] positions, Piece[] pieces, Direction direction) {
        List<String> words = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            Piece[] presentPieces = getPresentPieces(positions[i], pieces[i], direction);
            if (presentPieces.length <= 1)
                continue;

            StringBuilder result = new StringBuilder();
            for (Piece wordPiece : presentPieces)
                result.append(wordPiece.letter());
            words.add(result.toString());
        }

        return words.toArray(String[]::new);
    }

    private Piece[] getPresentPieces(Vector2 position, Piece piece, Direction direction) {
        Direction directionToCheck = direction == Direction.Horizontal ? Direction.Vertical : Direction.Horizontal;

        return wordGetter.run(
                new Piece[]{piece}, new Vector2[]{position}, directionToCheck
        );
    }

    private String getWord(Vector2[] positions, Piece[] pieces, Direction direction) {
        Piece[] wordPieces = wordGetter.run(pieces, positions, direction);
        if (wordPieces.length <= 1)
            return null;

        StringBuilder result = new StringBuilder();
        for (Piece wordPiece : wordPieces)
            result.append(wordPiece.letter());
        return result.toString();
    }

    private Direction getWordDirection(Vector2[] positions) {
        if (positions.length == 1)
            return null;

        if (positions[0].x == positions[1].x)
            return Direction.Vertical;
        return Direction.Horizontal;
    }
}
