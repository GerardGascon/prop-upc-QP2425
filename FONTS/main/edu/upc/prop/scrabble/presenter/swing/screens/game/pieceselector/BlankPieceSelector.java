package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;

import javax.swing.*;
import java.util.function.Consumer;

public class BlankPieceSelector extends JPanel implements IBlankPieceSelector {
    private JPanel overlay;

    public BlankPieceSelector() {
        setLayout(null);
        setOpaque(false);
    }

    @Override
    public void openSelectorPopUp(Consumer<String> selectPieceCallback) {
        if (overlay != null) {
            remove(overlay);
        }

        overlay = new PieceSelector(this, selectPieceCallback);
        add(overlay);
        setComponentZOrder(overlay, 0);
        revalidate();
        repaint();
    }
}
