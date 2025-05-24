package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.IRand;

/**
 * Classe responsable d'omplir la mà de cada jugador amb fitxes de la bossa al començament de la partida.
 * <p>
 * La classe {@code HandFiller} assegura que cada jugador rep 7 fitxes a l’inici del joc,
 * extretes de manera aleatòria de la {@code Bag} de fitxes disponibles. Utilitza una implementació de {@code IRand}
 * per a la selecció aleatòria.
 * </p>
 *
 * @author Gerard Gascón
 */
public class HandFiller {
    /** Bossa de fitxes d’on s’extreuen les fitxes per omplir les mans. */
    private final Bag bag;

    /** Llista dels jugadors als quals cal omplir la mà. */
    private final Player[] players;

    /** Generador de nombres aleatoris per seleccionar fitxes a l’atzar de la bossa. */
    private final IRand random;

    /**
     * Crea una nova instància de `HandFiller`.
     *
     * @param bag     La bossa d’on s’extreuen les fitxes.
     * @param players Els jugadors als quals omplir la mà.
     * @param random  El generador de nombres aleatoris usat per seleccionar les fitxes.
     */
    public HandFiller(Bag bag, Player[] players, IRand random) {
        this.bag = bag;
        this.players = players;
        this.random = random;
    }

    /**
     * Omple la mà de cada jugador amb 7 fitxes extretes de la bossa.
     * <p>
     * Aquest mètode assegura que cada jugador rebi 7 fitxes aleatòries de la bossa.
     * Fa una comprovació que hi hagi prou fitxes a la bossa per a tots els jugadors.
     * </p>
     */
    public void run() {
        assert bag.getSize() >= players.length * 7;

        for (Player player : players)
            fillPlayerHand(player);
    }

    /**
     * Omple la mà d’un jugador amb 7 fitxes extretes de la bossa de forma aleatòria.
     *
     * @param player El jugador a qui s’omplirà la mà.
     */
    private void fillPlayerHand(Player player) {
        for (int i = 0; i < 7; i++) {
            Piece piece = bag.draw(random.nextInt(bag.getSize()));
            player.addPiece(piece);
        }
    }
}
