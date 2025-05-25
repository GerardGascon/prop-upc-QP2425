package edu.upc.prop.scrabble.domain.game;

import edu.upc.prop.scrabble.data.Player;

/**
 * Interfície que defineix la pantalla de final de partida.
 * Permet mostrar els resultats finals amb els jugadors ordenats segons
 * la puntuació o un altre criteri.
 * @author Biel Pérez Silvestre
 * @see Player
 */
public interface IEndScreen {
    /**
     * Mostra la pantalla final amb els jugadors ordenats.
     * @param sortedPlayers Array de jugadors ordenats segons la seva puntuació
     */
    void show(Player[] sortedPlayers);
}
