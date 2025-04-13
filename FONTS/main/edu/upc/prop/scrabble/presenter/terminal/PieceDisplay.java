package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.IPiecePrinter;

public class PieceDisplay implements IPiecePrinter {
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
