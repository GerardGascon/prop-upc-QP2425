package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.HashMap;
import java.util.Map;

public class GamesPlayedLeaderboard   {

    public PlayerValuePair[] run(Score[] scores) {
        // Group by player name and games played
        Map<String, Integer> gamesMap = new HashMap<>();
        for (Score score : scores) gamesMap.compute(score.getPlayerName(), (k, v) -> v == null  ? 1 : v + 1);

        // Convert map into a sorted PlayerValuePair[]
        return gamesMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Sort directly using map values
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue())) // Create PlayerValuePair objects after sorting
                .toArray(PlayerValuePair[]::new);

    }
}
