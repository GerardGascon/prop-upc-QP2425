package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.HashMap;
import java.util.Map;

public class MaxScoreLeaderboard   {

    public PlayerValuePair[] run(Score[] scores) {
        // Group by player name and maxScore
        Map<String, Integer> scoreMap = new HashMap<>();
        for (Score score : scores) scoreMap.compute(score.playerName(), (k, v) -> v == null || score.scoreValue() > v ? score.scoreValue() : v);

        // Convert map into a sorted PlayerValuePair[]
        return scoreMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Sort directly using map values
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue())) // Create PlayerValuePair objects after sorting
                .toArray(PlayerValuePair[]::new);

    }
}
