package edu.upc.prop.scrabble.domain.leaderboard;

/**
 * Classe auxiliar utilitzada pels controladors de Leaderboard per representar
 * les estadístiques d’un jugador en termes de partides jugades i partides guanyades.
 * Aquesta estructura permet acumular resultats i calcular el percentatge de victòries.
 * @author Felipe Martínez Lassalle
 */
public class GamesWinsPair {
    /**
     * Nombre total de partides jugades.
     */
    private int games;

    /**
     * Nombre total de partides guanyades.
     */
    private int wins;

    /**
     * Creador de l’estructura a partir del resultat d'una partida.
     * @param isWinner {@code true} si la partida va ser guanyada, {@code false} altrament.
     */
    public GamesWinsPair(boolean isWinner) {
        this.games = 1;
        if (isWinner) this.wins = 1;
    }

    /**
     * Afegeix una nova partida a l’estadística, i incrementa el nombre de victòries si escau.
     * @param isWinner {@code true} si la nova partida va ser guanyada, {@code false} altrament.
     * @return El mateix objecte {@code GamesWinsPair}, per permetre concatenació de crides.
     */
    public GamesWinsPair addGame(boolean isWinner) {
        this.games++;
        if (isWinner) this.wins++;
        return this;
    }

    /**
     * Calcula el percentatge de victòries respecte al total de partides jugades.
     * @return Percentatge de victòries (de 0 a 100), com a valor {@code double}.
     */
    public double getWinRate() {
        return (double) wins / games * 100;
    }
}
