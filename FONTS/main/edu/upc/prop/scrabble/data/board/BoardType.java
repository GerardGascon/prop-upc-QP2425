package edu.upc.prop.scrabble.data.board;

/**
 * Defineix els tipus de tauler disponibles per al joc de Scrabble.
 * Cada tipus pot tenir una mida, disposició o conjunt de regles diferents:
 * <ul>
 *   <li>{@code Junior} - Una versió simplificada de mida 11x11 per a jugadors més joves.</li>
 *   <li>{@code Standard} - El tauler oficial de mida 15x15 utilitzat en el Scrabble clàssic.</li>
 *   <li>{@code Super} - Una variant ampliada de mida 21x21.</li>
 * </ul>
 */
public enum BoardType {
    /**
     * Una versió simplificada de mida 11x11 per a jugadors més joves.
     */
    Junior,
    /**
     * El tauler oficial de mida 15x15 utilitzat en el Scrabble clàssic.
     */
    Standard,
    /**
     * Una variant ampliada de mida 21x21.
     */
    Super
}
