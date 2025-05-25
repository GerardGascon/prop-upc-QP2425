package scrabble.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.leaderboard.Score;
import edu.upc.prop.scrabble.domain.leaderboard.GamesPlayedLeaderboard;
import edu.upc.prop.scrabble.domain.leaderboard.PlayerValuePair;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGamesPlayed {

    @Test
    public void testRunReturnsCorrectGamesPlayedSorted() {

        Leaderboard leaderboard = new Leaderboard();

        // Sample data: Albert 4 games, Biel 2 games, Gerard 1 game, Gina 4 games (scoreValue and isWinner doesn't matter)
        leaderboard.addScore(new Score(0, false,"Albert"));
        leaderboard.addScore(new Score(0, false, "Gina"));
        leaderboard.addScore(new Score(0, false, "Albert"));
        leaderboard.addScore(new Score(0, false, "Albert"));
        leaderboard.addScore(new Score(0, false, "Biel"));
        leaderboard.addScore(new Score(0, false, "Gina"));
        leaderboard.addScore(new Score(0, false, "Biel"));
        leaderboard.addScore(new Score(0, false, "Albert"));
        leaderboard.addScore(new Score(0, false, "Gerard"));
        leaderboard.addScore(new Score(0, false, "Gina"));
        leaderboard.addScore(new Score(0, false, "Gina"));

        GamesPlayedLeaderboard gamesPlayedLeaderboard = new GamesPlayedLeaderboard();
        PlayerValuePair[] result = gamesPlayedLeaderboard.run(leaderboard.getScoreArray());

        // Check correct length
        assertEquals(4, result.length);

        // Check correct sorting by games played (Alphabetical order in tie case): Albert (4), Gina (4), Biel (2)
        assertEquals("Albert", result[0].playerName());
        assertEquals("Gina", result[1].playerName());
        assertEquals("Biel", result[2].playerName());

        assertEquals(4.0, result[0].value(), 0.0);
        assertEquals(4.0, result[1].value(), 0.0);
        assertEquals(2.0, result[2].value(), 0.0);

        result = PlayerValuePair.reverse(result);

        // Same length
        assertEquals(4, result.length);

        // Check correct sorting: Gerard (1), Biel (2), Gina (4)
        assertEquals("Gerard", result[0].playerName());
        assertEquals("Biel", result[1].playerName());
        assertEquals("Gina", result[2].playerName());

        assertEquals(1.0, result[0].value(), 0.0);
        assertEquals(2.0, result[1].value(), 0.0);
        assertEquals(4.0, result[2].value(), 0.0);
    }
}