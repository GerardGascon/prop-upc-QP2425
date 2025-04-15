package edu.upc.prop.scrabble.data.leaderboard;

public class Score {
    private final int scoreValue;
    private final boolean isWinner;
    private final String playerName;

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

}
