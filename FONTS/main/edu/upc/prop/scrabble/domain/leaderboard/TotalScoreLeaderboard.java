package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.Map;
import java.util.HashMap;

public class TotalScoreLeaderboard {

    public ScorePair[] run(Score[] scores) {
        // Group by player name and total score
        Map<String, Integer> scoreMap = new HashMap<>();
        for(Score score : scores) scoreMap.put(score.getPlayerName(), scoreMap.getOrDefault(score.getPlayerName(), 0) + score.getScoreValue());

        // Convert map into a sorted ScorePair[]
        return scoreMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Sort directly using map values
                .map(entry -> new ScorePair(entry.getKey(), entry.getValue())) // Create ScoreTotal objects after sorting
                .toArray(ScorePair[]::new);
    }
}