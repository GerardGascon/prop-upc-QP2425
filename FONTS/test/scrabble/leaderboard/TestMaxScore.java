package scrabble.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.leaderboard.Score;
import edu.upc.prop.scrabble.domain.leaderboard.MaxScoreLeaderboard;
import edu.upc.prop.scrabble.domain.leaderboard.PlayerValuePair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMaxScore {

    @Test
    public void testRunReturnsCorrectMaxScoreSorted() {

        Leaderboard leaderboard = new Leaderboard();

        // Sample data: Albert 2000 points, Biel 1500 points, Gerard 1500 points, Gina 777 points (isWinner doesn't matter)
        leaderboard.addScore(new Score(1100, false,"Albert"));
        leaderboard.addScore(new Score(750, false, "Gina"));
        leaderboard.addScore(new Score(0, false, "Gina"));
        leaderboard.addScore(new Score(200, false, "Biel"));
        leaderboard.addScore(new Score(500, false, "Gina"));
        leaderboard.addScore(new Score(1500, false, "Biel"));
        leaderboard.addScore(new Score(1800, false, "Albert"));
        leaderboard.addScore(new Score(1500, false, "Gerard"));
        leaderboard.addScore(new Score(80, false, "Gina"));
        leaderboard.addScore(new Score(200, false, "Gina"));
        leaderboard.addScore(new Score(2000, false, "Albert"));
        leaderboard.addScore(new Score(777, false, "Gina"));
        leaderboard.addScore(new Score(700, false, "Albert"));
        MaxScoreLeaderboard maxScoreLeaderboard = new MaxScoreLeaderboard();
        PlayerValuePair[] result = maxScoreLeaderboard.run(leaderboard.getScoreArray());

        // Check correct length
        assertEquals(4, result.length);

        // Check correct sorting by games played: Albert (2000), Biel (1500), Gerard (1500)
        assertEquals("Albert", result[0].playerName());
        assertEquals("Biel", result[1].playerName());
        assertEquals("Gerard", result[2].playerName());

        assertEquals(2000.0, result[0].value(), 0.0);
        assertEquals(1500.0, result[1].value(), 0.0);
        assertEquals(1500.0, result[2].value(), 0.0);

        result = PlayerValuePair.reverse(result);

        // Same length
        assertEquals(4, result.length);

        // Check correct sorting: Gina (777), Gerard (1500), Biel (1500)
        assertEquals("Gina", result[0].playerName());
        assertEquals("Gerard", result[1].playerName());
        assertEquals("Biel", result[2].playerName());

        assertEquals(777.0, result[0].value(), 0.0);
        assertEquals(1500.0, result[1].value(), 0.0);
        assertEquals(1500.0, result[2].value(), 0.0);
    }
}