package edu.upc.prop.scrabble.domain.leaderboard;

/**
 * Aux class to store playerName and value used at the controller
 * @author Felipe Martínez Lassalle
 */
public class PlayerValuePair {
    private final String playerName;
    private final double value;

    public PlayerValuePair(String name, double value) {
        this.playerName = name;
        this.value = value;
    }

    public String getPlayerName() { return playerName; }
    public double getValue() { return value; }

    /**
     * Static function that reverses the order of the array
     * @param arr Array to reverse
     * @return Reversed array
     */
    public static PlayerValuePair[] reverse(PlayerValuePair[] arr) {
        PlayerValuePair[] reversed = new PlayerValuePair[arr.length];
        for (int i = 0; i < arr.length; i++) reversed[i] = arr[arr.length - 1 - i];
        return reversed;
    }
}