package edu.upc.prop.scrabble.domain.leaderboard;

/**
 * Aux class to help in win rate calculation
 * @author Felipe Martínez Lassalle
 */
public class GamesWinsPair {
    private int games;
    private int wins;

    public GamesWinsPair(boolean isWinner) {
        this.games = 1;
        if(isWinner) this.wins = 1;
    }

    /**
     * Adds a game and may add a win depending on boolean param
     * @param isWinner Marks whether the game was won or not
     * @return Object itself to ease insertion in the map
     */
    public GamesWinsPair addGame(boolean isWinner) {
        this.games++;
        if(isWinner) this.wins++;
        return this;
    }

    /**
     * Computes the win rate value
     * @return % of victories
     */
    public double getWinRate() { return (double) wins/games * 100; }

}
