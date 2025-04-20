package edu.upc.prop.scrabble.data.crosschecks;

import edu.upc.prop.scrabble.data.board.Board;

import java.util.BitSet;

public abstract class CrossChecks {

    private final BitSet[][] crossChecks;


    protected int boardSize;
    public CrossChecks(int boardSize) {
        this.boardSize = boardSize;
        crossChecks = new BitSet[boardSize][boardSize];

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                crossChecks[row][col] = new BitSet(getNumberOfLetters());
            }
        }
    }

    public abstract String[] getLetters();
    public abstract int getNumberOfLetters();
    public abstract Boolean ableToPlace(int x, int y, String letter);
    public BitSet getCrossCheck(int x, int y) {
        return crossChecks[x][y];
    }
    public void setCrossCheck(int x, int y, int value) {
        crossChecks[x][y].set(value);
    }

    private void setAllBits(int x, int y, BitSet bits) { this.crossChecks[x][y] = bits;}
    protected abstract CrossChecks copy();
    public CrossChecks rotate() {
        CrossChecks copy = copy();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                copy.setAllBits(boardSize - col - 1,row, getCrossCheck(row, col));
            }
        }
        return copy;
    }
}