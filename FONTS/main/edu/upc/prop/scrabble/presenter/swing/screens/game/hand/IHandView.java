package edu.upc.prop.scrabble.presenter.swing.screens.game.hand;

import edu.upc.prop.scrabble.data.Player;

public interface IHandView {
    String getSelectedPiece();
    int getSelectedPiecePoints();

    void showPieces(Player player);
}
