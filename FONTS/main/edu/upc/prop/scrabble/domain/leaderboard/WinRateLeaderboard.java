package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.HashMap;
import java.util.Map;

public class WinRateLeaderboard   {

    public ScorePair[] run(Score[] scores) {
        // Group by player name and maxScore
        Map<String, GamesWinsPair> pairMap = new HashMap<>();
        for (Score score : scores) pairMap.compute(score.getPlayerName(), (k, v) -> v == null  ? new GamesWinsPair(score.getIsWinner()) : v.addGame(score.getIsWinner()));

        // Convert map into a sorted ScorePair[]
        return pairMap.entrySet().stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue().getWinRate(), entry1.getValue().getWinRate())) // Sort directly using map values
                .map(entry -> new ScorePair(entry.getKey(), entry.getValue().getWinRate())) // Create ScorePair objects after sorting
                .toArray(ScorePair[]::new);

    }
}
