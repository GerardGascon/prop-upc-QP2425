package edu.upc.prop.scrabble.domain.score;

public class ScoreTotal {
    private String playerName;
    private int scoreTotal;

    public ScoreTotal(String name, int score) {
        this.playerName = name;
        this.scoreTotal = score;
    }

    public String getPlayerName() { return playerName; }

    public int getScoreTotal() { return scoreTotal; }

    public void updateScoreTotal(int score) { this.scoreTotal += score; }
}
