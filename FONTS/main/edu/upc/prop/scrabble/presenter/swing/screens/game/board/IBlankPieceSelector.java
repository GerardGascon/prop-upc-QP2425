package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import java.util.function.Consumer;

/**
 * Interfície per gestionar la selecció d'una fitxa en blanc.
 * Quan s'obre el selector, es passa un callback que rebrà la fitxa seleccionada.
 *
 * @author Gerard Gascón
 */
public interface IBlankPieceSelector {
    /**
     * Obre el popup per seleccionar la fitxa en blanc.
     * Un cop seleccionada la fitxa, es crida el callback amb la lletra escollida.
     *
     * @param selectPieceCallback funció que rep la fitxa seleccionada (ex: "A", "E", etc.)
     */
    void openSelectorPopUp(Consumer<String> selectPieceCallback);
}
