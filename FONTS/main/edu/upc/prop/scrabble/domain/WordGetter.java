package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

public class WordGetter {
    private final Board board;

    public WordGetter(Board board) {
        this.board = board;
    }

    public Piece[] run(Piece newPiece, Vector2 position, Direction direction) {
        if (direction == Direction.Horizontal)
            return getHorizontalWord(newPiece, position);

        return getVerticalWord(newPiece, position);
    }

    private Piece[] getHorizontalWord(Piece newPiece, Vector2 position) {
        return null;
    }

    private Piece[] getVerticalWord(Piece newPiece, Vector2 position) {
        return null;
    }
}
