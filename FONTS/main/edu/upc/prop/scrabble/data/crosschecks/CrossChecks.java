package edu.upc.prop.scrabble.data.crosschecks;

import edu.upc.prop.scrabble.data.board.Board;

import java.util.BitSet;

public abstract class CrossChecks {
    //0 se puede poner, 1 no se puede poner
    private final BitSet[][] crossChecksVer;
    //almacena que piezas no podemos poner en la casilla [][]
    //por culpa de las palabras en Vertical
    private final BitSet[][] crossChecksHor;
    //lo mimsmo pero por culpa de las palabras en horizontal

    public CrossChecks(Board board) {
        int boardSize = board.getSize();
        crossChecksVer = new BitSet[boardSize][boardSize];
        crossChecksHor = new BitSet[boardSize][boardSize];

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                crossChecksVer[row][col] = new BitSet(getNumberOfLetters());
                crossChecksHor[row][col] = new BitSet(getNumberOfLetters());
            }
        }
    }

    public abstract String[] getLetters();
    public abstract int getNumberOfLetters();

    public BitSet getCrossCheckVer(int x, int y) {
        return crossChecksVer[x][y];
    }
    public void setCrossCheckVer(int x, int y, int value) {
        crossChecksVer[x][y].set(value);
    }

    public BitSet getCrossCheckHor(int x, int y) {
        return crossChecksHor[x][y];
    }
    public void setCrossCheckHor(int x, int y, int value) {
        crossChecksHor[x][y].set(value);
    }
}