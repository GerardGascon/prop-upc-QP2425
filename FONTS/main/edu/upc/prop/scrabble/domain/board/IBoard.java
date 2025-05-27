package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.board.PremiumTileType;

/**
 * Interfície que facilita la comunicació entre la capa de domini i la capa de presentació per a les actualitzacions del tauler.
 * Defineix els mètodes per actualitzar el tauler quan l'estat del joc canvia.
 *
 * @author Gerard Gascón
 */
public interface IBoard {
    /**
     * Actualitza una cel·la específica del tauler amb la peça i els punts corresponents.
     *
     * @param piece  La lletra o peça que s'ha col·locat.
     * @param points Els punts assignats a aquesta peça.
     * @param x      La coordenada horitzontal de la cel·la.
     * @param y      La coordenada vertical de la cel·la.
     */
    void updateCell(String piece, int points, int x, int y);

    /**
     * Estableix el tipus de casella especial (premium) en una posició determinada del tauler.
     *
     * @param type El tipus de casella premium (doble lletra, triple paraula, etc.).
     * @param x    La coordenada horitzontal de la cel·la.
     * @param y    La coordenada vertical de la cel·la.
     */
    void setPremiumTile(PremiumTileType type, int x, int y);
}
