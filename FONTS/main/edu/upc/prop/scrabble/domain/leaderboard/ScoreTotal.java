package edu.upc.prop.scrabble.domain.leaderboard;

public class ScoreTotal {
    private String playerName;
    private int scoreTotal;

    public ScoreTotal(String name, int score) {
        this.playerName = name;
        this.scoreTotal = score;
    }
}
