package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Board;
import edu.upc.prop.scrabble.utils.Direction;

public class WordPlacer {
    private final Board board;

    public WordPlacer(Board board) {
        this.board = board;
    }

    public void run(String word, int x, int y, Direction direction) {
        if (direction == Direction.Vertical)
            placeWordVertical(word, x, y);
        else
            placeWordHorizontal(word, x, y);
    }

    private void placeWordVertical(String word, int x, int y) {
        for (int i = 0; i < word.length(); i++) {
            String s = word.substring(i, i + 1);
            board.placePiece(s, x, y + i);
        }
    }

    private void placeWordHorizontal(String word, int x, int y) {
        for (int i = 0; i < word.length(); i++) {
            String s = word.substring(i, i + 1);
            board.placePiece(s, x + i, y);
        }
    }
}
