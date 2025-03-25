package edu.upc.prop.scrabble.data;

public class Score {
    private int scoreValue;
    private boolean isWinner;
    final private String playerName;

    public Score(int score, boolean winner, String name) {
        this.scoreValue = score;
        this.isWinner = winner;
        this.playerName = name;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public boolean getIsWinner() { return isWinner; }

    public String getPlayerName() {
        return playerName;
    }

    public void updateScore(int score) { this.scoreValue = score; }

    public void updateWinner(boolean winner) { this.isWinner = winner; }
}
