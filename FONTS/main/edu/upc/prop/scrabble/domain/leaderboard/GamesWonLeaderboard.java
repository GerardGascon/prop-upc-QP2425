package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.HashMap;
import java.util.Map;

public class GamesWonLeaderboard   {

    public PlayerValuePair[] run(Score[] scores) {
        // Group by player name and won games
        Map<String, Integer> winsMap = new HashMap<>();
        for (Score score : scores) winsMap.compute(score.playerName(), (k, v) -> v == null  ? (score.isWinner() ? 1 : 0) : (score.isWinner() ? v + 1 : v));

        // Convert map into a sorted PlayerValuePair[]
        return winsMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Sort directly using map values
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue())) // Create PlayerValuePair objects after sorting
                .toArray(PlayerValuePair[]::new);

    }
}
