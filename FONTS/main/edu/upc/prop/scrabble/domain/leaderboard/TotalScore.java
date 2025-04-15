package edu.upc.prop.scrabble.domain.leaderboard;

public class TotalScore {
    private String playerName;
    private int TotalScore;

    public TotalScore(String name, int score) {
        this.playerName = name;
        this.TotalScore = score;
    }
}
