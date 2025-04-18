package edu.upc.prop.scrabble.data.board;

import edu.upc.prop.scrabble.data.pieces.Piece;

/**
 * Represents the game board for a Scrabble game.
 * Maintains the placed letter tiles and premium scoring tile information.
 * This class provides basic functionality such as placing tiles, checking cell status,
 * and rotating the board.
 *
 * @author Gerard Gascón
 */
public abstract class Board {
    /**
     * Grid of letter tiles placed on the board. Empty cells contain null.
     */
    protected Piece[][] placedTiles;
    /**
     * Grid representing premium scoring tiles such as double/triple word or letter.
     */
    private final PremiumTileType[][] premiumTiles;
    /**
     * Indicates whether the board has any tiles placed.
     */
    private boolean empty = true;

    /**
     * Constructs a board with the given size.
     *
     * @param size the size (width and height) of the square board
     */
    protected Board(int size) {
        placedTiles = new Piece[size][size];
        premiumTiles = new PremiumTileType[size][size];
    }

    /**
     * Returns the size of the board.
     *
     * @return the size of the board
     */
    public int getSize() {
        return placedTiles.length;
    }

    /**
     * Places a letter tile on the board at the specified coordinates.
     * Does nothing if the target cell is already occupied.
     *
     * @param piece the letter tile to place
     * @param x     the column index (0-based)
     * @param y     the row index (0-based)
     * @see Piece
     */
    public void placePiece(Piece piece, int x, int y) {
        if (placedTiles[x][y] != null) return;

        placedTiles[x][y] = piece;
        empty = false;
    }

    /**
     * Checks if the given coordinates represent the center tile,
     * which typically must be covered by the first word played.
     *
     * @param x the column index
     * @param y the row index
     * @return true if the cell is the center of the board
     */
    public boolean isCenter(int x, int y) {
        return x == y && x == getSize() / 2;
    }

    /**
     * Checks whether a cell at the specified coordinates is empty.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if the cell is empty and valid, false otherwise
     */
    public boolean isCellEmpty(int x, int y) {
        if (!isCellValid(x, y))
            return false;
        return placedTiles[x][y] == null;
    }

    /**
     * Returns the piece at the specified cell.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the piece at the given cell, or null if none
     */
    public Piece getCellPiece(int x, int y) {
        return placedTiles[x][y];
    }

    /**
     * Checks if a given cell is a premium tile.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if the tile is a premium tile, false otherwise
     */
    public boolean isPremiumTile(int x, int y) {
        return premiumTiles[x][y] != null;
    }

    /**
     * Returns the type of premium tile at the specified location.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the premium tile type at the given cell, or null if not premium
     * @see PremiumTileType
     */
    public PremiumTileType getPremiumTileType(int x, int y) {
        return premiumTiles[x][y];
    }

    /**
     * Sets a premium tile type at the specified location.
     *
     * @param x    the x-coordinate
     * @param y    the y-coordinate
     * @param type the premium tile type to set
     * @see PremiumTileType
     */
    protected void setPremiumTile(int x, int y, PremiumTileType type) {
        premiumTiles[x][y] = type;
    }

    /**
     * Checks whether the specified cell coordinates are within board bounds.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if the cell is valid, false otherwise
     */
    public boolean isCellValid(int x, int y) {
        return x >= 0 && x < getSize() && y >= 0 && y < getSize();
    }

    /**
     * Returns whether the board is empty.
     *
     * @return true if no pieces have been placed, false otherwise
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Returns a rotated copy of the board (90 degrees clockwise).
     *
     * @return a rotated copy of the board
     */
    public Board rotate() {
        Board copy = copy();
        copy.placedTiles = getRotatedPieces();
        return copy;
    }

    /**
     * Returns a rotated version of the placed pieces (90 degrees clockwise).
     *
     * @return the rotated pieces grid
     */
    private Piece[][] getRotatedPieces() {
        Piece[][] rotatedPieces = new Piece[getSize()][getSize()];
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                rotatedPieces[col][getSize() - row - 1] = placedTiles[row][col];
            }
        }
        return rotatedPieces;
    }

    /**
     * Creates and returns a deep copy of this board's tiles.<br>
     * <b>Important</b>: It doesn't include the placed pieces.
     *
     * @return a copy of the board
     */
    protected abstract Board copy();
}
