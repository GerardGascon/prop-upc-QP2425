package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.board.Board;

/**
 * Interface that facilitates communication between the domain and presentation layers for board updates.
 * It defines a method to trigger a refresh of the board in the presentation layer when the state of the game changes.
 *
 * @author Gerard Gascón
 */
public interface IBoard {
    /**
     * Triggers a board refresh on the presenter to update the game board in the presentation layer.
     * This method will be called when the game state or the board changes and needs to be reflected in the UI.
     *
     * @see Board
     */
    void updateBoard();
    void updateCell(String piece, int points, int x, int y);
}
