package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.IRand;

public class HandFiller {
    private final Bag bag;
    private final Player[] players;
    private final IRand random;

    public HandFiller(Bag bag, Player[] players, IRand random) {
        this.bag = bag;
        this.players = players;
        this.random = random;
    }

    public void run(){
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
