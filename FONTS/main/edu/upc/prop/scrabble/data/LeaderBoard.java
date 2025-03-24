package edu.upc.prop.scrabble.data;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;

public class LeaderBoard {
    private Map<Integer, String> leaderBoard;

    public LeaderBoard() { this.leaderBoard = new TreeMap<>(); }
    public LeaderBoard(Score score) {
        this.leaderBoard = new TreeMap<>();
        leaderBoard.put(score.getScoreValue(), score.getPlayerName());
    }
    public LeaderBoard(List<Score> scoreList) {
        this.leaderBoard = new TreeMap<>();
        for (Score score : scoreList) { leaderBoard.put(score.getScoreValue(), score.getPlayerName());}
    }

    public void addPlayer(Score score) { leaderBoard.put(score.getScoreValue(), score.getPlayerName()); }

    public void addPlayers(List<Score> scoreList) {
        for (Score score : scoreList) { leaderBoard.put(score.getScoreValue(), score.getPlayerName()); }
    }

    public Map<Integer, String> getLeaderBoard() { return leaderBoard; }
}
