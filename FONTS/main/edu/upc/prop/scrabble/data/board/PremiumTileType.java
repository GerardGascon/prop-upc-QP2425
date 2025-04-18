package edu.upc.prop.scrabble.data.board;

/**
 * Enum representing the types of premium tiles on a Scrabble board.
 * These tiles multiply the value of either the letter placed or the entire word.
 *
 * @author Gerard Gascón
 */
public enum PremiumTileType {
    /**
     * Multiplies the word score by 4.
     */
    QuadrupleWord,
    /**
     * Multiplies the word score by 3.
     */
    TripleWord,
    /**
     * Multiplies the word score by 2.
     */
    DoubleWord,
    /**
     * Multiplies the letter score by 4.
     */
    QuadrupleLetter,
    /**
     * Multiplies the letter score by 3.
     */
    TripleLetter,
    /**
     * Multiplies the letter score by 2.
     */
    DoubleLetter,
}

