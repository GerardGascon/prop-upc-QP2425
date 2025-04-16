package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.Movement;
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

        String alreadyPresentWords = getPresentWord(positions, pieces, direction);

        String[] result = new String[] {
                alreadyPresentWords
        };
        return result;
    }

    private String getPresentWord(Vector2[] positions, Piece[] pieces, Direction direction) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < positions.length; i++) {
            Piece[] presentPieces = getPresentPieces(positions[i], pieces[i], direction);
            if (presentPieces.length <= 1)
                continue;

            for (Piece presentPiece : presentPieces) {
                result.append(presentPiece.letter());
            }
        }

        return result.toString();
    }

    private Piece[] getPresentPieces(Vector2 position, Piece piece, Direction direction) {
        Direction directionToCheck = direction == Direction.Horizontal ? Direction.Vertical : Direction.Horizontal;

        return wordGetter.run(
                new Piece[]{piece}, new Vector2[]{position}, directionToCheck
        );
    }

    private Direction getWordDirection(Vector2[] positions) {
        if (positions.length == 1)
            return null;

        if (positions[0].x == positions[1].x)
            return Direction.Vertical;
        return Direction.Horizontal;
    }
}
