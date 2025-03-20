package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.List;

public class WordGetter {
    private final Board board;

    public WordGetter(Board board) {
        this.board = board;
    }

    public Piece[] run(Piece newPiece, Vector2 position, Direction direction) {
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < board.getSize(); i++) {
            int x = getX(position, direction, i);
            int y = getY(position, direction, i);

            if (!board.isCellEmpty(x, y)) {
                pieces.add(board.getCellPiece(x, y));
                continue;
            }

            if (new Vector2(x, y).equals(position))
                pieces.add(newPiece);

            if (pieces.contains(newPiece))
                return pieces.toArray(new Piece[0]);

            pieces.clear();
        }

        return pieces.toArray(new Piece[0]);
    }

    private static int getY(Vector2 position, Direction direction, int offset) {
        return direction == Direction.Horizontal ? position.y : offset;
    }

    private static int getX(Vector2 position, Direction direction, int offset) {
        return direction == Direction.Horizontal ? offset : position.x;
    }
}
