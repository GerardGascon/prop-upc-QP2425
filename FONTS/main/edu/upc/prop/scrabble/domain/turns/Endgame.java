package edu.upc.prop.scrabble.domain.turns;

import edu.upc.prop.scrabble.data.Player;

/**
 * Classe encarregada de determinar si la partida ha finalitzat.
 * La partida acaba si tots els jugadors passen el torn diverses vegades consecutives
 * o si algun jugador es queda sense fitxes.
 *
 * @author Biel Pérez
 */
public class Endgame {
    /**
     * Array de jugadors, utilitzat per consultar els seus atributs.
     * @see Player
     */
    private final Player[] players;

    /**
     * Constructor de la classe Endgame.
     * @param players Jugadors que participen a la partida.
     * @see Player
     */
    public Endgame(Player[] players) {
        this.players = players;
    }

    /**
     * Determina si la partida ha acabat.
     * @param skipCounter Comptador per determinar si tots els jugadors han passat el torn tres vegades consecutives,
     * cas en què la partida finalitza.
     * @return true si la partida ha acabat, false en cas contrari.
     */
    public boolean run(int skipCounter) {

        boolean skiplimitreached = skipCounter / 3 >= players.length;

        if (skiplimitreached)
            return true;
        for (Player player : players)
            if (player.getHand().length == 0)
                return true;

        return false;
    }
}
