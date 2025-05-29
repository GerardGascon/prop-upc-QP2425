package edu.upc.prop.scrabble.data.leaderboard;


import java.util.Objects;

/**
 * Representa les dades rellevants d'un jugador un cop finalitzada una partida.
 * Aquest registre encapsula la puntuació total, si el jugador ha estat guanyador,
 * i el seu nom identificador.
 *
 * @author Felipe Martínez Lassalle
 */
public final class Score {
    private int scoreValue;
    private boolean isWinner;
    private String playerName;

    /**
     * @param scoreValue Puntuació total obtinguda pel jugador en la partida.
     * @param isWinner   Valor booleà que indica si el jugador ha estat el guanyador de la partida.
     * @param playerName Nom del jugador, utilitzat com a identificador.
     */
    public Score(int scoreValue, boolean isWinner, String playerName) {
        this.scoreValue = scoreValue;
        this.isWinner = isWinner;
        this.playerName = playerName;
    }

    public int scoreValue() {
        return scoreValue;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public String playerName() {
        return playerName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Score) obj;
        return this.scoreValue == that.scoreValue &&
                this.isWinner == that.isWinner &&
                Objects.equals(this.playerName, that.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scoreValue, isWinner, playerName);
    }

    @Override
    public String toString() {
        return "Score[" +
                "scoreValue=" + scoreValue + ", " +
                "isWinner=" + isWinner + ", " +
                "playerName=" + playerName + ']';
    }
}