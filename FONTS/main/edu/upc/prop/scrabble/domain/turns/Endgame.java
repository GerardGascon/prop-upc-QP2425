package edu.upc.prop.scrabble.domain.turns;

import edu.upc.prop.scrabble.data.Player;

public class Endgame {
    private final Player[] players;

    public Endgame(Player[] players) {
        this.players = players;
    }

    public boolean run(int skipCounter) {
        boolean skiplimitreached = skipCounter / 3 >= players.length;
        if (skiplimitreached) {return true;}
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
