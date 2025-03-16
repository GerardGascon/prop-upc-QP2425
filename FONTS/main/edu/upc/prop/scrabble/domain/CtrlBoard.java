package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Board;

public class CtrlBoard {
    private final Board board;

    public CtrlBoard(int size) {
        this.board = new Board(size);
    }

    public void placeWordVertical(String word, int x, int y) {
        for (int i = 0; i < word.length(); i++) {
            String s = word.substring(i, i + 1);
            board.placePiece(s, x, y + i);
        }
    }

    public void placeWordHorizontal(String word, int x, int y) {
        for (int i = 0; i < word.length(); i++) {
            String s = word.substring(i, i + 1);
            board.placePiece(s, x + i, y);
        }
    }

    public String getCell(int x, int y) {
        return board.getCell(x, y);
    }
}
