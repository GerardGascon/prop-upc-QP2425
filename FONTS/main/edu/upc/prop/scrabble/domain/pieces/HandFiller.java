package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.IRand;

/**
 * Class responsible for filling each player's hand with pieces from the bag at the start of the game.
 * <p>
 * The `HandFiller` class ensures that each player is given 7 pieces at the beginning of the game,
 * drawing randomly from the `Bag` of available pieces. It uses an `IRand` implementation for random selection.
 * </p>
 *
 * @author Gerard Gascón
 */
public class HandFiller {
    private final Bag bag;
    private final Player[] players;
    private final IRand random;

    /**
     * Constructs a new `HandFiller` instance.
     *
     * @param bag     The bag from which pieces are drawn.
     * @param players The players to fill hands for.
     * @param random  The random number generator used for selecting pieces.
     */
    public HandFiller(Bag bag, Player[] players, IRand random) {
        this.bag = bag;
        this.players = players;
        this.random = random;
    }

    /**
     * Fills each player's hand with 7 pieces from the bag.
     * <p>
     * This method ensures that each player receives 7 random pieces from the bag.
     * It asserts that there are enough pieces in the bag to accommodate all players.
     * </p>
     */
    public void run() {
        assert bag.getSize() >= players.length * 7;

        for (Player player : players)
            fillPlayerHand(player);
    }

    private void fillPlayerHand(Player player) {
        for (int i = 0; i < 7; i++) {
            Piece piece = bag.draw(random.nextInt(bag.getSize()));
            player.addPiece(piece);
        }
    }
}
