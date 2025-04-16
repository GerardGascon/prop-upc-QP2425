package edu.upc.prop.scrabble.presenter.terminal.actionmaker;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.IHandDisplay;

public class HandView implements IHandDisplay {
    @Override
    public void updateHand(Piece[] hand) {
        System.out.println("Current hand:");
        for (Piece piece : hand) {
            System.out.print(piece.toString() + " ");
        }
        System.out.println();
    }
}
