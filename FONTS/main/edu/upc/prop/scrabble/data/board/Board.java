package edu.upc.prop.scrabble.data.board;

public abstract class Board {
    private final String[][] board;

    public Board(int size) {
        board = new String[size][size];
    }

    public int getSize() {
        return board.length;
    }

    public void placePiece(String piece, int x, int y) {
        if (board[x][y] != null) return;

        board[x][y] = piece;
    }

    public String getCell(int x, int y) {
        return board[x][y];
    }
}
