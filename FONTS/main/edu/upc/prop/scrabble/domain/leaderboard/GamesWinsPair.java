package edu.upc.prop.scrabble.domain.leaderboard;

/**
 * Estructura de dades auxiliar utilitzada pels controladors de Leaderboard
 * @author Felipe Martínez Lassalle
 */
public class GamesWinsPair {
    /**
     * Variable que emmagatzema el nombre de partides
     */
    private int games;
    /**
     * Variable que emmagatzema el nombre de victòries
     */
    private int wins;

    /**
     * Constructora
     * @param isWinner Cert si la partida va ser guanyada
     */
    public GamesWinsPair(boolean isWinner) {
        this.games = 1;
        if(isWinner) this.wins = 1;
    }

    /**
     * Afegeix una partida i victòria depenent del valor booleà
     * @param isWinner Cert si la partida va ser guanyada
     * @return El mateix objecte
     */
    public GamesWinsPair addGame(boolean isWinner) {
        this.games++;
        if(isWinner) this.wins++;
        return this;
    }

    /**
     * Calcula el ratio de victòries
     * @return % de victòries
     */
    public double getWinRate() { return (double) wins/games * 100; }

}
