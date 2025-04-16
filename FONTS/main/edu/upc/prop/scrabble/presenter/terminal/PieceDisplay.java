package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.IPiecePrinter;

/***
 * Terminal-based implementation of IPiecePrinter for displaying Scrabble pieces.
 * @author Gina Escofet González
 */
public class PieceDisplay implements IPiecePrinter {
    /***
     * Displays an array of pieces in the terminal.
     * @param pieces  Array of game pieces to display.
     */
    public void display(Piece[] pieces) {
        if (pieces == null || pieces.length == 0) {
            System.out.println("No pieces have to be displayed");
            return;
        }
        for (Piece piece : pieces) {
            System.out.println(piece + " ");
        }
        System.out.println();
    }
}
