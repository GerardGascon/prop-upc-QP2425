package edu.upc.prop.scrabble.data.pieces;
import java.util.*;

/***
 * Represents a bag of pieces of Scrabble game.
 * @author Gina Escofet González
 */
public class Bag {
    private final List<Piece> bag;

    /***
     * Creates an empty bag.
     */
    public Bag() {
        this.bag = new ArrayList<>();
    }

    /***
     * Checks if a bag contains any pieces.
     * @return True if the bag is empty, False otherwise.
     */
    public boolean isEmpty() {
        return bag.isEmpty();
    }

    /***
     * Gets the current number of pieces remaining of the bag.
     * @return The count of remaining pieces.
     */
    public int getSize() {
        return bag.size();
    }

    /***
     * Draws and removes a specific piece from the bag by its index position.
     * @param index The zero-based position of the piece to retrieve (must be valid).
     * @return The piece at the specified index.
     * @throws IndexOutOfBoundsException if the index is invalid.
     * @throws IllegalStateException if the bag is empty.
     */
    public Piece draw(int index) {
        if (bag.isEmpty()) {
            throw new IllegalStateException("Cannot get any piece from an empty bag");
        }
        return bag.remove(index);
    }

    /***
     * Adds a piece to the bag.
     * @param piece The piece to be added to the bag.
     * @throws IllegalArgumentException if the piece is null
     */
    public void add(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("Cannot add null piece");
        }
        bag.add(piece);
    }
}
