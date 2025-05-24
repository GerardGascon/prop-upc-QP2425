package edu.upc.prop.scrabble.domain.turns;

/**
 * Enumeració que representa els possibles resultats d’un torn en el joc.
 *
 * <ul>
 *   <li><b>Skip</b>: El jugador decideix passar el torn sense fer cap acció.</li>
 *   <li><b>Place</b>: El jugador col·loca una o més fitxes al tauler.</li>
 *   <li><b>Draw</b>: El jugador roba fitxes del sac (bag).</li>
 * </ul>
 *
 * @author Gerard Gascón
 */
public enum TurnResult {
    /** El jugador decideix passar el torn sense fer cap acció. */
    Skip,

    /** El jugador col·loca una o més fitxes al tauler. */
    Place,

    /** El jugador roba fitxes del sac (bag). */
    Draw
}
