package edu.upc.prop.scrabble.domain.leaderboard;

public class ScorePair {
    private String playerName;
    private double value;

    public ScorePair(String name, double value) {
        this.playerName = name;
        this.value = value;
    }
}
