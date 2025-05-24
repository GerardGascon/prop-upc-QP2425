package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.TreeMap;
import java.util.Map;

/**
 * Controlador de Leaderboard encarregat d'ordenar als jugadors segons la puntuació total obtinguda
 * @author Felipe Martínez Lassalle
 * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
 */
public class TotalScoreLeaderboard {

    /**
     * Funció encarregada de complir la tasca del controlador
     * @param scores Array de tots els Score a ordenar
     * @return Array de PlayerValuePair que guarda de forma ordenada el nom del jugador i la seva puntuació total
     * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
     * @see Score
     * @see PlayerValuePair
     */
    public PlayerValuePair[] run(Score[] scores) {
        // Group by player name and total score
        Map<String, Integer> scoreMap = new TreeMap<>();
        for(Score score : scores) scoreMap.put(score.playerName(), scoreMap.getOrDefault(score.playerName(), 0) + score.scoreValue());

        // Convert map into a sorted PlayerValuePair[]
        return scoreMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Sort directly using map values
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue())) // Create PlayerValuePair objects after sorting
                .toArray(PlayerValuePair[]::new);
    }
}