package edu.upc.prop.scrabble.data;

public class Board {
    private final String[][] board;

    public Board(int size) {
        board = new String[size][size];
    }

    public int getSize() {
        return board.length;
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
