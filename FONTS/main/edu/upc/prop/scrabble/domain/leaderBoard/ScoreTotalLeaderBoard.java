package edu.upc.prop.scrabble.domain.leaderBoard;

import edu.upc.prop.scrabble.data.Score;
import edu.upc.prop.scrabble.domain.score.ScoreTotal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

public class ScoreTotalLeaderBoard {

    public ArrayList<ScoreTotal> getScoreTotalLeaderBoard(Score[] scores) {
        // Group by player name and total score
        Map<String, Integer> scoreMap = new HashMap<>();
        for(Score score : scores) scoreMap.put(score.getPlayerName(), scoreMap.getOrDefault(score.getPlayerName(), 0) + score.getScoreValue());

        // Convert map into a sorted ArrayList<ScoreTotal>
        return scoreMap.entrySet().stream()
                .map(entry -> new ScoreTotal(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(ScoreTotal::getScoreTotal).reversed())
                .collect(Collectors.toCollection(ArrayList::new));

    }

}