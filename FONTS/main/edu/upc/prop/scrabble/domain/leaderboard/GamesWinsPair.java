package edu.upc.prop.scrabble.domain.leaderboard;

public class GamesWinsPair {
    private int games;
    private int wins;

    public GamesWinsPair(boolean isWinner) {
        this.games = 1;
        if(isWinner) this.wins = 1;
    }

    public void addGame(boolean isWinner) {
        this.games++;
        if(isWinner) this.wins++;
    }

    public double getWinRate() { return (double) wins/games * 100; }

}
