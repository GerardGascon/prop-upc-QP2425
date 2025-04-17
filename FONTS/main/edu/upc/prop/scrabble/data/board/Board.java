package edu.upc.prop.scrabble.data.board;

import edu.upc.prop.scrabble.data.pieces.Piece;

public abstract class Board {
    private final Piece[][] placedTiles;
    private final PremiumTileType[][] premiumTiles;
    private boolean empty = true;

    public Board(int size) {
        placedTiles = new Piece[size][size];
        premiumTiles = new PremiumTileType[size][size];
    }

    public int getSize() {
        return placedTiles.length;
    }

    public void placePiece(Piece piece, int x, int y) {
        if (placedTiles[x][y] != null) return;

        placedTiles[x][y] = piece;
        empty = false;
    }

    public boolean isCenter(int x, int y) {
        return x == y && x == getSize() / 2;
    }

    public boolean isCellEmpty(int x, int y) {
        if (!isCellValid(x, y))
            return false;
        return placedTiles[x][y] == null;
    }

    public Piece getCellPiece(int x, int y) {
        return placedTiles[x][y];
    }

    public boolean isPremiumTile(int x, int y) {
        return premiumTiles[x][y] != null;
    }

    public PremiumTileType getPremiumTileType(int x, int y) {
        return premiumTiles[x][y];
    }

    protected void setPremiumTile(int x, int y, PremiumTileType type) {
        premiumTiles[x][y] = type;
    }

    public boolean isCellValid(int x, int y) {
        return x >= 0 && x < getSize() && y >= 0 && y < getSize();
    }

    public boolean isEmpty() {
        return empty;
    }
}
