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

    /**
     * Puntuació total obtinguda pel jugador en una partida.
     */
    private int scoreValue;

    /**
     * Indica si el jugador ha estat el guanyador de la partida.
     */
    private boolean isWinner;

    /**
     * Nom del jugador, utilitzat com a identificador.
     */
    private String playerName;

    /**
     * Crea una nova instància de la classe {@code Score} amb la puntuació, el resultat
     * de la partida i el nom del jugador.
     *
     * @param scoreValue Puntuació total obtinguda pel jugador en la partida.
     * @param isWinner   Indica si el jugador ha guanyat la partida ({@code true}) o no ({@code false}).
     * @param playerName Nom del jugador, utilitzat com a identificador.
     */
    public Score(int scoreValue, boolean isWinner, String playerName) {
        this.scoreValue = scoreValue;
        this.isWinner = isWinner;
        this.playerName = playerName;
    }

    /**
     * Retorna la puntuació total del jugador.
     *
     * @return Valor enter amb la puntuació obtinguda.
     */
    public int scoreValue() {
        return scoreValue;
    }

    /**
     * Indica si el jugador ha estat guanyador de la partida.
     *
     * @return {@code true} si ha guanyat, {@code false} altrament.
     */
    public boolean isWinner() {
        return isWinner;
    }

    /**
     * Retorna el nom del jugador.
     *
     * @return Cadena de text amb el nom identificador del jugador.
     */
    public String playerName() {
        return playerName;
    }

    /**
     * Compara aquest objecte amb un altre per determinar-ne la igualtat.
     * Dues instàncies de {@code Score} es consideren iguals si tenen els mateixos
     * valors de puntuació, resultat de la partida i nom del jugador.
     *
     * @param obj Objecte amb el qual comparar.
     * @return {@code true} si són iguals, {@code false} altrament.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Score) obj;
        return this.scoreValue == that.scoreValue &&
                this.isWinner == that.isWinner &&
                Objects.equals(this.playerName, that.playerName);
    }

    /**
     * Calcula el codi hash per a aquesta instància de {@code Score}, basat
     * en la puntuació, el resultat i el nom del jugador.
     *
     * @return Codi hash generat.
     */
    @Override
    public int hashCode() {
        return Objects.hash(scoreValue, isWinner, playerName);
    }

    /**
     * Retorna una representació textual de l'objecte {@code Score},
     * útil per a depuració i visualització.
     *
     * @return Cadena de text amb la informació del jugador i la partida.
     */
    @Override
    public String toString() {
        return "Score[" +
                "scoreValue=" + scoreValue + ", " +
                "isWinner=" + isWinner + ", " +
                "playerName=" + playerName + ']';
    }
}
