package edu.upc.prop.scrabble.domain.leaderboard;

public class PlayerValuePair {
    private final String playerName;
    private final double value;

    public PlayerValuePair(String name, double value) {
        this.playerName = name;
        this.value = value;
    }

    public String getPlayerName() { return playerName; }

    public double getValue() { return value; }
}
