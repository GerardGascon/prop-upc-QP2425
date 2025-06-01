
package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

/**
 * Classe que converteix una cadena de lletres en peces en anglès.
 * Estén {@link PiecesConverter} per oferir la funcionalitat de conversió específica per a l'idioma anglès.
 * Utilitza el diccionari per defecte o un personalitzat per determinar les puntuacions de les peces.
 *
 * @author Gina Escofet González
 */
public class EnglishPiecesConverter extends PiecesConverter {
    /**
     * Crea una instància del convertidor de fitxes per a l'idioma anglès amb el diccionari per defecte.
     * Aquest diccionari hauria de contenir totes les fitxes bàsiques del joc en anglès.
     */
    public EnglishPiecesConverter() {
        super();
    }
    /**
     * Crea una instància del convertidor de fitxes per a l'idioma anglès amb un diccionari personalitzat.
     * Aquest diccionari permet definir fitxes i puntuacions específiques per al joc en anglès.
     *
     * @param dictionary Array de fitxes {@link Piece} amb les puntuacions i lletres disponibles.
     */
    public EnglishPiecesConverter(Piece[] dictionary) {
        super(dictionary);
    }
}
