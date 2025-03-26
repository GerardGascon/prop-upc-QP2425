package edu.upc.prop.scrabble.domain.leaderBoard;

import edu.upc.prop.scrabble.data.Score;
import edu.upc.prop.scrabble.domain.score.ScoreTotal;

import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

public class ScoreTotalLeaderBoard {

    public ScoreTotal[] run(Score[] scores) {
        // Group by player name and total score
        Map<String, Integer> scoreMap = new HashMap<>();
        for(Score score : scores) scoreMap.put(score.getPlayerName(), scoreMap.getOrDefault(score.getPlayerName(), 0) + score.getScoreValue());

        // Convert map into a sorted ArrayList<ScoreTotal>
        return scoreMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Sort directly using map values
                .map(entry -> new ScoreTotal(entry.getKey(), entry.getValue())) // Create ScoreTotal objects after sorting
                .toArray(ScoreTotal[]::new);
    }
}