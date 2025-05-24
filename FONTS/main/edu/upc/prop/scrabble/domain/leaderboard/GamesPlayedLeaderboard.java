package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.TreeMap;
import java.util.Map;

/**
 * Controlador de Leaderboard encarregat d'ordenar als jugadors segons el nombre de partides jugades
 * @author Felipe Martínez Lassalle
 * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
 */
public class GamesPlayedLeaderboard   {

    /**
     * Funció encarregada de complir la tasca del controlador
     * @param scores Array de tots els Score a ordenar
     * @return Array de PlayerValuePair que guarda de forma ordenada el nom del jugador i el nombre de partides jugades
     * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
     * @see Score
     * @see PlayerValuePair
     */
    public PlayerValuePair[] run(Score[] scores) {
        // Group by player name and games played
        Map<String, Integer> gamesMap = new TreeMap<>();
        for (Score score : scores) gamesMap.compute(score.playerName(), (k, v) -> v == null  ? 1 : v + 1);

        // Convert map into a sorted PlayerValuePair[]
        return gamesMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Sort directly using map values
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue())) // Create PlayerValuePair objects after sorting
                .toArray(PlayerValuePair[]::new);

    }
}
