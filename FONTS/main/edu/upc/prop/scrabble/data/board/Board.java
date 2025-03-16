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

    public String getCell(int x, int y) {
        return placedTiles[x][y];
    }

    public PremiumTileType getTileType(int x, int y) {
        return premiumTiles[x][y];
    }

    protected void setPremiumTile(int x, int y, PremiumTileType type) {
        premiumTiles[x][y] = type;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < placedTiles.length; i++) {
            for (int j = 0; j < placedTiles[i].length; j++) {
                if (placedTiles[j][i] != null) {
                    ret.append(placedTiles[j][i]).append("  ");
                    continue;
                }

                if (premiumTiles[j][i] != null) {
                    ret.append(TileConverter.convert(premiumTiles[j][i])).append(" ");
                    continue;
                }

                ret.append(".  ");
            }
            ret.append("\n");
        }

        return ret.toString();
    }
}
