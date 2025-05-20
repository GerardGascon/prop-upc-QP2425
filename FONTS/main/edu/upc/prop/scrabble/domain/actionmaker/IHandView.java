package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.pieces.Piece;

public interface IHandView {
    String[] getSelectedPiece();
    int getSelectedPiecePoints();

    void showPieces(Piece[] pieces);
}
