package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.HashMap;
import java.util.Map;

public class WinRateLeaderboard   {

    public PlayerValuePair[] run(Score[] scores) {
        // Group by player name and a pair of won and played games
        Map<String, GamesWinsPair> pairMap = new HashMap<>();
        for (Score score : scores) pairMap.compute(score.playerName(), (k, v) -> v == null  ? new GamesWinsPair(score.isWinner()) : v.addGame(score.isWinner()));

        // Convert map into a sorted PlayerValuePair[]
        return pairMap.entrySet().stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue().getWinRate(), entry1.getValue().getWinRate())) // Sort directly using map values
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue().getWinRate())) // Create PlayerValuePair objects after sorting
                .toArray(PlayerValuePair[]::new);

    }
}
