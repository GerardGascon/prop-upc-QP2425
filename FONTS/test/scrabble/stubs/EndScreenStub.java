package scrabble.stubs;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.domain.game.IEndScreen;

public class EndScreenStub implements IEndScreen {
    @Override
    public void show(Player[] sortedPlayers) {
        System.out.println("End Screen called.");
    }
}
