package edu.upc.prop.scrabble.data.board;

public abstract class Board {
    private final String[][] placedTiles;
    private final PremiumTileType[][] premiumTiles;

    public Board(int size) {
        placedTiles = new String[size][size];
        premiumTiles = new PremiumTileType[size][size];
    }

    public int getSize() {
        return placedTiles.length;
    }

    public void placePiece(String piece, int x, int y) {
        if (placedTiles[x][y] != null) return;

        placedTiles[x][y] = piece;
    }

    public boolean isCenter(int x, int y) {
        return x == y && x == getSize() / 2;
    }

    public boolean isCellEmpty(int x, int y) {
        return placedTiles[x][y] == null;
    }

    public String getCellPiece(int x, int y) {
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
}
