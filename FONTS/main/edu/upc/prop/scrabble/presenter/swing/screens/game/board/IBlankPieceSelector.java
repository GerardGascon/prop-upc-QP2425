package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import java.util.function.Consumer;

public interface IBlankPieceSelector {
    void openSelectorPopUp(Consumer<String> selectPieceCallback);
}
