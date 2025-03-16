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

    public boolean placePiece(String piece, int x, int y) {
        if (board[x][y] != null) return false;

        board[x][y] = piece;
        return true;
    }

    public String getCell(int x, int y) {
        return board[x][y];
    }
}
