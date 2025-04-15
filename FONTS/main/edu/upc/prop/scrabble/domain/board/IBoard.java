package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.board.Board;

/**
 * Used to ease communication between domain and presentation
 * @author Gerard Gascón
 */
public interface IBoard {
    /**
     * Trigger a board refresh on a presenter
     * @param board A reference to the board of the current game
     * @see Board
     */
    void updateBoard(Board board);
}
