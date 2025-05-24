package edu.upc.prop.scrabble.presenter.terminal.actionmaker;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;

public class HandView implements IHandView {
    @Override
    public String[] getSelectedPiece() {
        return new String[0];
    }

    @Override
    public int getSelectedPiecePoints() {
        return 0;
    }

    @Override
    public void showPieces(Piece[] pieces) {
        System.out.println("Current hand:");
        for (Piece piece : pieces) {
            System.out.print(piece.toString() + " ");
        }
        System.out.println();
    }

    @Override
    public void piecePlaced() {

    }
}
