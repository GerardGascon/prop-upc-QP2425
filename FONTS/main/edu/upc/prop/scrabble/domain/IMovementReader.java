package edu.upc.prop.scrabble.domain;

public interface IMovementReader {
    /*
     * Format:
     *   WORD XY
     *   If X is a letter, it means horizontal word
     *   If Y is a letter, it means vertical word
     */
    String readMove();
}
