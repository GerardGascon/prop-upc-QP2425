package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.HashMap;
import java.util.Map;

public class GamesPlayedLeaderboard   {

    public ScorePair[] run(Score[] scores) {
        // Group by player name and maxScore
        Map<String, Integer> gamesMap = new HashMap<>();
        for (Score score : scores) gamesMap.compute(score.getPlayerName(), (k, v) -> v == null  ? 1 : v + 1);

        // Convert map into a sorted ScorePair[]
        return gamesMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Sort directly using map values
                .map(entry -> new ScorePair(entry.getKey(), entry.getValue())) // Create ScorePair objects after sorting
                .toArray(ScorePair[]::new);

    }
}
