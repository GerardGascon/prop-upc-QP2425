package edu.upc.prop.scrabble.domain.turns;

import edu.upc.prop.scrabble.data.Player;

/**
 * Class used to determine if a game has ended
 * @author Biel Pérez
 */
public class Endgame {
    private final Player[] players;

    public Endgame(Player[] players) {
        this.players = players;
    }

    /**
     * Checks if the game has ended
     * @param skipCounter Counter used to determine if everyone has skipped three turns in a row
     * @return A boolean which determines if the game has ended or not
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
