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

    public static PlayerValuePair[] reverse(PlayerValuePair[] arr) {
        PlayerValuePair[] reversed = new PlayerValuePair[arr.length];
        for (int i = 0; i < arr.length; i++) reversed[i] = arr[arr.length - 1 - i];
        return reversed;
    }
}
