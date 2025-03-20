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
        return null;
    }

    private Piece[] getVerticalWord(Piece newPiece, Vector2 position) {
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < board.getSize(); i++) {
            if (board.isCellEmpty(position.x, i)) {
                if (new Vector2(position.x, i).equals(position))
                    pieces.add(newPiece);

                if (pieces.contains(newPiece)){
                    return pieces.toArray(new Piece[0]);
                }
                pieces.clear();
                continue;
            }

            pieces.add(board.getCellPiece(position.x, i));
        }

        return pieces.toArray(new Piece[0]);
    }
}
