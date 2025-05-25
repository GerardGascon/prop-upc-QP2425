package edu.upc.prop.scrabble.domain.leaderboard;

/**
 * Estructura de dades auxiliar utilitzada pels controladors de Leaderboard.
 * Aquesta classe representa una parella formada pel nom d’un jugador
 * i un valor numèric associat (com ara puntuació, partides guanyades o win rate),
 * i és utilitzada per representar els resultats en les classificacions.
 * @param playerName Nom del jugador.
 * @param value      Valor associat al jugador (puntuació, nombre de partides, etc.).
 * @author Felipe Martínez Lassalle
 */
public record PlayerValuePair(String playerName, double value) {
    /**
     * Constructora de la classe.
     * @param playerName Nom identificador del jugador.
     * @param value      Valor associat al jugador.
     */
    public PlayerValuePair { }

    /**
     * Obté el nom del jugador.
     * @return El nom del jugador.
     */
    @Override
    public String playerName() { return playerName; }

    /**
     * Obté el valor associat al jugador.
     * @return El valor numèric corresponent.
     */
    @Override
    public double value() { return value; }

    /**
     * Inverteix l’ordre dels elements d’un array de {@code PlayerValuePair}.
     * @param arr Array d’objectes {@code PlayerValuePair} a invertir.
     * @return Un nou array amb els elements en ordre invers.
     */
    public static PlayerValuePair[] reverse(PlayerValuePair[] arr) {
        PlayerValuePair[] reversed = new PlayerValuePair[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reversed[i] = arr[arr.length - 1 - i];
        }
        return reversed;
    }
}
