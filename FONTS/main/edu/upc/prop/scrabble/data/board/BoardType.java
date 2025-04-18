package edu.upc.prop.scrabble.data.board;

/**
 * Defines the available board types for a Scrabble game.
 * Each type may have a different size, layout, or set of rules:
 * <ul>
 *   <li>{@code Junior} - A simplified version of size 11x11 for younger players.</li>
 *   <li>{@code Standard} - The official board of size 15x15 used in classic Scrabble.</li>
 *   <li>{@code Super} - An extended variant of size 21x21.</li>
 * </ul>
 */
public enum BoardType {
    /**
     * A simplified version of size 11x11 for younger players.
     */
    Junior,
    /**
     * The official board of size 15x15 used in classic Scrabble.
     */
    Standard,
    /**
     * An extended variant of size 21x21.
     */
    Super
}
