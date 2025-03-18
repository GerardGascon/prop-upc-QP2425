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

    public void run(String word, Piece[] pieces, int x, int y, Direction direction) {
        if (direction == Direction.Vertical)
            placeWordVertical(word, x, y);
        else
            placeWordHorizontal(word, x, y);

        view.UpdateBoard();
    }

    private void placeWordVertical(String word, int x, int y) {
        for (int i = 0; i < word.length(); i++) {
            String s = word.substring(i, i + 1);
            board.placePiece(new Piece(s, 0), x, y + i);
        }
    }

    private void placeWordHorizontal(String word, int x, int y) {
        for (int i = 0; i < word.length(); i++) {
            String s = word.substring(i, i + 1);
            board.placePiece(new Piece(s, 0), x + i, y);
        }
    }
}
