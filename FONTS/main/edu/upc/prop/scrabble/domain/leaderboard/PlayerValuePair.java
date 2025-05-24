package edu.upc.prop.scrabble.domain.leaderboard;

/**
 * Estructura de dades auxiliar utilitzada pels controladors de Leaderboard
 * @author Felipe Martínez Lassalle
 */
public class PlayerValuePair {
    /**
     * Emmagatzema el nom del jugador
     */
    private final String playerName;
    /**
     * Emmagatzema el valor pertinent
     */
    private final double value;

    /**
     * Creadora
     * @param name Nom del jugador
     * @param value Valor utilitzat pel controlador
     */
    public PlayerValuePair(String name, double value) {
        this.playerName = name;
        this.value = value;
    }

    /**
     * Consultora
     * @return Nom del jugador
     */
    public String getPlayerName() { return playerName; }

    /**
     * Consultora
     * @return Valor emmagatzemat
     */
    public double getValue() { return value; }

    /**
     * Funció estàtica que inverteix l'orde de l'Array donada
     * @param arr Array a invertir
     * @return Array amb l'ordre invers
     */
    public static PlayerValuePair[] reverse(PlayerValuePair[] arr) {
        PlayerValuePair[] reversed = new PlayerValuePair[arr.length];
        for (int i = 0; i < arr.length; i++) reversed[i] = arr[arr.length - 1 - i];
        return reversed;
    }
}