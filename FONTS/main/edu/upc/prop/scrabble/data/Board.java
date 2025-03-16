package edu.upc.prop.scrabble.data;

public class Board {
    private final int size;

    public Board(int size) {
        this.size = size;
    }

    public int getSize() {
        return 4;
    }

    public void placePiece(String piece, int x, int y) {

    }

    public String getCell(int x, int y) {
        return "C";
    }
}
