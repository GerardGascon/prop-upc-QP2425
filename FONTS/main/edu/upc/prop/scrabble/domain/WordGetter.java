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
        if (direction == Direction.Horizontal)
            return getHorizontalWord(newPiece, position);

        return getVerticalWord(newPiece, position);
    }

    private Piece[] getHorizontalWord(Piece newPiece, Vector2 position) {
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < board.getSize(); i++) {
            if (!board.isCellEmpty(i, position.y)) {
                pieces.add(board.getCellPiece(i, position.y));
                continue;
            }

            tryAddNewPiece(newPiece, new Vector2(i, position.y), position, pieces);

            if (pieces.contains(newPiece))
                return pieces.toArray(new Piece[0]);

            pieces.clear();
        }

        return pieces.toArray(new Piece[0]);
    }

    private Piece[] getVerticalWord(Piece newPiece, Vector2 position) {
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < board.getSize(); i++) {
            if (!board.isCellEmpty(position.x, i)) {
                pieces.add(board.getCellPiece(position.x, i));
                continue;
            }

            tryAddNewPiece(newPiece, new Vector2(position.x, i), position, pieces);

            if (pieces.contains(newPiece))
                return pieces.toArray(new Piece[0]);

            pieces.clear();
        }

        return pieces.toArray(new Piece[0]);
    }

    private static void tryAddNewPiece(Piece newPiece, Vector2 offset, Vector2 position, List<Piece> pieces) {
        if (offset.equals(position))
            pieces.add(newPiece);
    }
}
