package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.pieces.Piece;

/**
 * Interfície que representa la vista de la mà del jugador en el joc de Scrabble.
 * Permet consultar la peça actualment seleccionada i mostrar el conjunt de peces disponibles.
 *
 * @author Gina Escofet
 */
public interface IHandView {
    /**
     * Retorna la peça actualment seleccionada com un array de cadenes.
     * El format de les cadenes depèn de la implementació (per exemple, lletra i marques especials).
     *
     * @return Un array de cadenes que representen la peça seleccionada.
     */
    String[] getSelectedPiece();

    /**
     * Retorna el valor en punts de la peça actualment seleccionada.
     *
     * @return Els punts de la peça seleccionada.
     */
    int getSelectedPiecePoints();

    /**
     * Mostra el conjunt de peces proporcionat al jugador.
     *
     * @param pieces L'array de peces que s'han de mostrar.
     */
    void showPieces(Piece[] pieces);
}
