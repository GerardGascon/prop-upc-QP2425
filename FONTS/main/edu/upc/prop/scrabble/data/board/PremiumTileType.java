package edu.upc.prop.scrabble.data.board;

/**
 * Enum que representa els tipus de caselles especials en un tauler de Scrabble.
 * Aquestes caselles multipliquen el valor de la lletra col·locada o de tota la paraula.
 *
 * @author Gerard Gascón
 */
public enum PremiumTileType {
    /**
     * Multiplica la puntuació de la paraula per 4.
     */
    QuadrupleWord,
    /**
     * Multiplica la puntuació de la paraula per 3.
     */
    TripleWord,
    /**
     * Multiplica la puntuació de la paraula per 2.
     */
    DoubleWord,
    /**
     * Multiplica la puntuació de la lletra per 4.
     */
    QuadrupleLetter,
    /**
     * Multiplica la puntuació de la lletra per 3.
     */
    TripleLetter,
    /**
     * Multiplica la puntuació de la lletra per 2.
     */
    DoubleLetter,
}
