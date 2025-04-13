package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

public interface IPiecePrinter {
    public default void display(Piece[] pieces) {
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
