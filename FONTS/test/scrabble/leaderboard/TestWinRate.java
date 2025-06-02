package scrabble.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.leaderboard.Score;
import edu.upc.prop.scrabble.domain.leaderboard.PlayerValuePair;
import edu.upc.prop.scrabble.domain.leaderboard.WinRateLeaderboard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestWinRate {

    @Test
    public void testRunReturnsCorrectWinRatesSorted() {

        Leaderboard leaderboard = new Leaderboard();

        // Sample data: Albert wins 2 of 3, Biel wins 1 of 2, Gerard wins 1 of 1, Gina wins 0 of 1 (scoreValue doesn't matter)
        leaderboard.addScore(new Score(0, true,"Albert"));
        leaderboard.addScore(new Score(0, true, "Albert"));
        leaderboard.addScore(new Score(0, false, "Albert"));
        leaderboard.addScore(new Score(0, true, "Biel"));
        leaderboard.addScore(new Score(0, false, "Biel"));
        leaderboard.addScore(new Score(0, true, "Gerard"));
        leaderboard.addScore(new Score(0, false, "Gina"));

        WinRateLeaderboard winRateLeaderboard = new WinRateLeaderboard();
        PlayerValuePair[] result = winRateLeaderboard.run(leaderboard.getScoreArray());

        // Check correct length
        assertEquals(4, result.length);

        // Check correct sorting by win rate: Gerard (1.0), Albert (0.66), Biel (0.5)
        assertEquals("Gerard", result[0].playerName());
        assertEquals("Albert", result[1].playerName());
        assertEquals("Biel", result[2].playerName());

        assertEquals(1.0 * 100, result[0].value(), 0.01);
        assertEquals((2.0 / 3)* 100, result[1].value(), 0.01);
        assertEquals(0.5 * 100, result[2].value(), 0.01);

        result = PlayerValuePair.reverse(result);

        // Same length
        assertEquals(4, result.length);

        // Check correct sorting: Gina (0.0), Biel (0.5), Albert (0.66)
        assertEquals("Gina", result[0].playerName());
        assertEquals("Biel", result[1].playerName());
        assertEquals("Albert", result[2].playerName());

        assertEquals(0.0 * 100, result[0].value(), 0.01);
        assertEquals(0.5 * 100, result[1].value(), 0.01);
        assertEquals((2.0 / 3) * 100, result[2].value(), 0.01);
    }
}