package edu.upc.prop.scrabble.data;

public class Board {
    private final int size;

    private final String[][] board;

    public Board(int size) {
        this.size = size;

        board = new String[size][size];
    }

    public int getSize() {
        return 4;
    }

    public void placePiece(String piece, int x, int y) {
        board[x][y] = piece;
    }

    public String getCell(int x, int y) {
        return board[x][y];
    }
}
