package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.TreeMap;
import java.util.Map;

/**
 * Leaderboard controller to sort players based on the number of games played
 * @author Felipe Martínez Lassalle
 */
public class GamesPlayedLeaderboard   {

    public PlayerValuePair[] run(Score[] scores) {
        // Group by player name and games played
        Map<String, Integer> gamesMap = new TreeMap<>();
        for (Score score : scores) gamesMap.compute(score.playerName(), (k, v) -> v == null  ? 1 : v + 1);

        // Convert map into a sorted PlayerValuePair[]
        return gamesMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Sort directly using map values
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue())) // Create PlayerValuePair objects after sorting
                .toArray(PlayerValuePair[]::new);

    }
}
