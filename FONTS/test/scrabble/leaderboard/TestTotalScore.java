package scrabble.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.leaderboard.Score;
import edu.upc.prop.scrabble.domain.leaderboard.TotalScoreLeaderboard;
import edu.upc.prop.scrabble.domain.leaderboard.PlayerValuePair;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestTotalScore {

    @Test
    public void testRunReturnsCorrectTotalScoreSorted() {

        Leaderboard leaderboard = new Leaderboard();

        // Sample data: Albert 5000 points, Biel 8500 points, Gerard 3000 points, Gina 5000 points (isWinner doesn't matter)
        leaderboard.addScore(new Score(2000, false,"Albert"));
        leaderboard.addScore(new Score(750, false, "Gina"));
        leaderboard.addScore(new Score(750, false, "Gina"));
        leaderboard.addScore(new Score(4500, false, "Biel"));
        leaderboard.addScore(new Score(500, false, "Gina"));
        leaderboard.addScore(new Score(4000, false, "Biel"));
        leaderboard.addScore(new Score(500, false, "Albert"));
        leaderboard.addScore(new Score(1500, false, "Gerard"));
        leaderboard.addScore(new Score(1000, false, "Gina"));
        leaderboard.addScore(new Score(1500, false, "Gina"));
        leaderboard.addScore(new Score(1500, false, "Albert"));
        leaderboard.addScore(new Score(1500, false, "Gerard"));
        leaderboard.addScore(new Score(500, false, "Gina"));
        leaderboard.addScore(new Score(1000, false, "Albert"));
        TotalScoreLeaderboard totalScoreLeaderboard = new TotalScoreLeaderboard();
        PlayerValuePair[] result = totalScoreLeaderboard.run(leaderboard.getScoreArray());

        // Check correct length
        assertEquals(4, result.length);

        // Check correct sorting by games played: Biel (8500), Albert (5000), Gina (5000)
        assertEquals("Biel", result[0].playerName());
        assertEquals("Albert", result[1].playerName());
        assertEquals("Gina", result[2].playerName());

        assertEquals(8500.0, result[0].value(), 0.0);
        assertEquals(5000.0, result[1].value(), 0.0);
        assertEquals(5000.0, result[2].value(), 0.0);

        result = PlayerValuePair.reverse(result);

        // Same length
        assertEquals(4, result.length);

        // Check correct sorting: Gerard (3000), Gina (5000), Albert (5000)
        assertEquals("Gerard", result[0].playerName());
        assertEquals("Gina", result[1].playerName());
        assertEquals("Albert", result[2].playerName());

        assertEquals(3000.0, result[0].value(), 0.0);
        assertEquals(5000.0, result[1].value(), 0.0);
        assertEquals(5000.0, result[2].value(), 0.0);
    }
}