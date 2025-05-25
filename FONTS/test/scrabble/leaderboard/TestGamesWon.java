package scrabble.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.leaderboard.Score;
import edu.upc.prop.scrabble.domain.leaderboard.GamesWonLeaderboard;
import edu.upc.prop.scrabble.domain.leaderboard.PlayerValuePair;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGamesWon {

    @Test
    public void testRunReturnsCorrectGamesWonSorted() {

        Leaderboard leaderboard = new Leaderboard();

        // Sample data: Albert 2 wins, Biel 0 wins, Gerard 1 win, Gina 4 wins (scoreValue doesn't matter)
        leaderboard.addScore(new Score(0, false,"Albert"));
        leaderboard.addScore(new Score(0, true, "Gina"));
        leaderboard.addScore(new Score(0, false, "Gina"));
        leaderboard.addScore(new Score(0, true, "Albert"));
        leaderboard.addScore(new Score(0, true, "Gina"));
        leaderboard.addScore(new Score(0, false, "Albert"));
        leaderboard.addScore(new Score(0, false, "Biel"));
        leaderboard.addScore(new Score(0, false, "Gina"));
        leaderboard.addScore(new Score(0, false, "Biel"));
        leaderboard.addScore(new Score(0, true, "Albert"));
        leaderboard.addScore(new Score(0, true, "Gerard"));
        leaderboard.addScore(new Score(0, true, "Gina"));
        leaderboard.addScore(new Score(0, true, "Gina"));

        GamesWonLeaderboard gamesWonLeaderboard = new GamesWonLeaderboard();
        PlayerValuePair[] result = gamesWonLeaderboard.run(leaderboard.getScoreArray());

        // Check correct length
        assertEquals(4, result.length);

        // Check correct sorting by games played: Gina (4), Albert (2), Gerard (1)
        assertEquals("Gina", result[0].playerName());
        assertEquals("Albert", result[1].playerName());
        assertEquals("Gerard", result[2].playerName());

        assertEquals(4.0, result[0].value(), 0.0);
        assertEquals(2.0, result[1].value(), 0.0);
        assertEquals(1.0, result[2].value(), 0.0);

        result = PlayerValuePair.reverse(result);

        // Same length
        assertEquals(4, result.length);

        // Check correct sorting: Biel (0), Gerard (1), Albert (2)
        assertEquals("Biel", result[0].playerName());
        assertEquals("Gerard", result[1].playerName());
        assertEquals("Albert", result[2].playerName());

        assertEquals(0.0, result[0].value(), 0.0);
        assertEquals(1.0, result[1].value(), 0.0);
        assertEquals(2.0, result[2].value(), 0.0);
    }
}