package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
/**
 * Used to ease communication between domain and presentation
 * @author Gina Escofet González
 */
public interface IPiecePrinter {
    /**
     *  Displays a collection of pieces on the game board or player interface.
     * @param pieces  Array of game pieces to display
     */
    void display(Piece[] pieces);
}
