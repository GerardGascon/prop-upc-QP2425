package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.TreeMap;
import java.util.Map;

/**
 * Controlador de Leaderboard encarregat d'ordenar als jugadors segons la puntuació màxima obtinguda
 * @author Felipe Martínez Lassalle
 * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
 */
public class MaxScoreLeaderboard   {

    /**
     * Funció encarregada de complir la tasca del controlador
     * @param scores Array de tots els Score a ordenar
     * @return Array de PlayerValuePair que guarda de forma ordenada el nom del jugador i la puntuació màxima obtinguda
     * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
     * @see Score
     * @see PlayerValuePair
     */
    public PlayerValuePair[] run(Score[] scores) {
        // Group by player name and maxScore
        Map<String, Integer> scoreMap = new TreeMap<>();
        for (Score score : scores) scoreMap.compute(score.playerName(), (k, v) -> v == null || score.scoreValue() > v ? score.scoreValue() : v);

        // Convert map into a sorted PlayerValuePair[]
        return scoreMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Sort directly using map values
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue())) // Create PlayerValuePair objects after sorting
                .toArray(PlayerValuePair[]::new);

    }
}
