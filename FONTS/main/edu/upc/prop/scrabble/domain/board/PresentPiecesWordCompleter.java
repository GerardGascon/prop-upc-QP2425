package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PresentPiecesWordCompleter {
    private final Board board;
    private final WordGetter wordGetter;

    public PresentPiecesWordCompleter(Board board, WordGetter wordGetter) {
        this.board = board;
        this.wordGetter = wordGetter;
    }

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

        if (direction == Direction.Horizontal){
            String word = getWord(positions, pieces, direction);
            if (word != null)
                words.add(word);
        }

        if (direction == Direction.Vertical){
            String word = getWord(positions, pieces, direction);
            if (word != null)
                words.add(word);
        }

        return words.toArray(String[]::new);
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
