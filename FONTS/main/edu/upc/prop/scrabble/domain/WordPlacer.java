package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.utils.Direction;

public class WordPlacer {
    private final Board board;
    private final IBoard view;

    public WordPlacer(Board board, IBoard view) {
        this.board = board;
        this.view = view;
    }

    public void run(Piece[] pieces, int x, int y, Direction direction) {
        if (direction == Direction.Vertical)
            placeWordVertical(pieces, x, y);
        else
            placeWordHorizontal(pieces, x, y);

        view.UpdateBoard();
    }

    private void placeWordVertical(Piece[] pieces, int x, int y) {
        for (int i = 0; i < pieces.length; i++) {
            board.placePiece(pieces[i], x, y + i);
        }
    }

    private void placeWordHorizontal(Piece[] pieces, int x, int y) {
        for (int i = 0; i < pieces.length; i++) {
            board.placePiece(pieces[i], x + i, y);
        }
    }
}
