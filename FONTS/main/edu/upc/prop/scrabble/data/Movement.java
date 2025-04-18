package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.utils.Direction;

/**
 * Represents a movement of a word on the Scrabble board, including
 * its starting coordinates (x, y), the direction of the movement,
 * and the word itself.
 *
 * <p><b>Note:</b> The word must be provided in uppercase. Any lowercase letters
 * will be interpreted as blank pieces on the board.</p>
 *
 * @param word      The word being placed on the board. It must be in uppercase.
 * @param x         The x-coordinate (horizontal position) where the word starts.
 * @param y         The y-coordinate (vertical position) where the word starts.
 * @param direction The direction in which the word is placed (Vertical or Horizontal).
 *
 * @author Gerard Gascón
 */
public record Movement(String word, int x, int y, Direction direction) {

    /**
     * Returns the word being placed on the board in this movement.
     *
     * @return the word being placed
     */
    public String word() {
        return word;
    }

    /**
     * Returns the starting x-coordinate of the word.
     *
     * @return the x-coordinate
     */
    public int x() {
        return x;
    }

    /**
     * Returns the starting y-coordinate of the word.
     *
     * @return the y-coordinate
     */
    public int y() {
        return y;
    }

    /**
     * Returns the direction of the word placement.
     *
     * @return the direction of the word placement
     */
    public Direction direction() {
        return direction;
    }
}
