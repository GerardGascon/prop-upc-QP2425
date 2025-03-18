package scrabble;

import edu.upc.prop.scrabble.data.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPlayer {
    @Test
    public void addScoreUpdatesPlayerScore() {
        Player player = new Player("Adri", false);

        player.addScore(5);

        assertEquals(5, player.getScore());
    }

    @Test
    public void playerScoreEqualsZero() {
        Player player = new Player("Adri", false);

        assertEquals(0, player.getScore());
    }

    @Test
    public void scoreAddsProperly() {
        Player player = new Player("Adri", false);

        player.addScore(5);
        player.addScore(5);

        assertEquals(10, player.getScore());
    }
}
