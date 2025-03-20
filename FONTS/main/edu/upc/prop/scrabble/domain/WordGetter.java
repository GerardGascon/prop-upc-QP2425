package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class WordGetter {
    private final Board board;

    public WordGetter(Board board) {
        this.board = board;
    }

    public Piece[] run(Piece[] newPieces, Vector2[] newPositions, Direction direction) {
        List<Piece> pieces = new ArrayList<>();
        List<Vector2> positions = new ArrayList<>();

        for (int i = 0; i < board.getSize(); i++) {
            int x = getXPosition(newPositions[0], direction, i);
            int y = getYPosition(newPositions[0], direction, i);

            if (!board.isCellEmpty(x, y)) {
                positions.add(new Vector2(x, y));
                pieces.add(board.getCellPiece(x, y));
                continue;
            }

            int positionInNewList = Arrays.asList(newPositions).indexOf(new Vector2(x, y));
            boolean containsPosition = positionInNewList != -1;
            if (containsPosition) {
                positions.add(newPositions[positionInNewList]);
                pieces.add(newPieces[positionInNewList]);
                continue;
            }

            if (new HashSet<>(positions).containsAll(Arrays.asList(newPositions)))
                return pieces.toArray(new Piece[0]);

            pieces.clear();
        }

        return pieces.toArray(new Piece[0]);
    }

    private static int getYPosition(Vector2 position, Direction direction, int offset) {
        return direction == Direction.Horizontal ? position.y : offset;
    }

    private static int getXPosition(Vector2 position, Direction direction, int offset) {
        return direction == Direction.Horizontal ? offset : position.x;
    }
}
