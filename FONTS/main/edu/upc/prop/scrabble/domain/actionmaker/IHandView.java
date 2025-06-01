package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.pieces.Piece;

/**
 * Interfície que defineix els mètodes necessaris per a la visualització i interacció
 * amb les fitxes de la mà d'un jugador en un joc de scrabble.
 * @author Gina Escofet González
 */
public interface IHandView {
    /**
     * Obté les fitxes actualment seleccionades per l'usuari.
     *
     * @return Array de Strings amb la informació de la fitxa seleccionada.
     */
    String getSelectedPiece();

    /**
     * Obté els punts associats a les fitxes actualment seleccionades per l'usuari.
     * @return Puntuació de les fitxes actualment seleccionades segons les regles del joc.
     */
    int getSelectedPiecePoints();

    /**
     * Actualitza la visualització de les fitxes disponibles.
     * @param pieces Array de fitxes que s'han de mostrar a la mà de l'usuari.
     */
    void showPieces(Piece[] pieces);
    /**
     * Notifica a la vista que una fitxa ha estat col·locada al tauler.
     * Això permet a la interfície actualitzar l'estat visual de la mà del jugador,
     * per exemple, desactivant o ocultant la fitxa que s'ha fet servir.
     */
    void piecePlaced();

}
