package edu.upc.prop.scrabble.data.board;

public abstract class Board {
    private final String[][] board;
    private final TileType[][] tiles;

    public Board(int size) {
        board = new String[size][size];
        tiles = new TileType[size][size];
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

    public TileType getTileType(int x, int y) {
        return tiles[x][y];
    }

    protected void setTileType(int x, int y, TileType type) {
        tiles[x][y] = type;
    }
}
