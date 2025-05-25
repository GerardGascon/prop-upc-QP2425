package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;

import javax.swing.*;
import java.util.function.Consumer;

/**
 * Panell per seleccionar la fitxa blanca (comodí) en el joc.
 * Implementa la interfície IBlankPieceSelector.
 *
 * @author Gerard Gascón
 */
public class BlankPieceSelector extends JPanel implements IBlankPieceSelector {

    /**
     * Panell superposat que conté el selector de fitxes.
     */
    private JPanel overlay;

    /**
     * Constructor que inicialitza el panell amb layout nul i fons transparent.
     */
    public BlankPieceSelector() {
        setLayout(null);
        setOpaque(false);
    }

    /**
     * Mostra un pop-up per seleccionar una fitxa, amb un callback que rep la fitxa escollida.
     *
     * @param selectPieceCallback Funció que s'executa quan es selecciona una fitxa.
     */
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
