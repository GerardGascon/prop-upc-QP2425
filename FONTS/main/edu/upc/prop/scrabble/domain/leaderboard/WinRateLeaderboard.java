package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.TreeMap;
import java.util.Map;

/**
 * Controlador de Leaderboard encarregat d'ordenar als jugadors segons el percentatge de victòries
 * @author Felipe Martínez Lassalle
 * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
 */
public class WinRateLeaderboard   {

    /**
     * Funció encarregada de complir la tasca del controlador
     * @param scores Array de tots els Score a ordenar
     * @return Array de PlayerValuePair que guarda de forma ordenada el nom del jugador i el seu percentatge de partides guanyades
     * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
     * @see Score
     * @see PlayerValuePair
     * @see GamesWinsPair
     */
    public PlayerValuePair[] run(Score[] scores) {
        // Group by player name and a pair of won and played games
        Map<String, GamesWinsPair> pairMap = new TreeMap<>();
        for (Score score : scores) pairMap.compute(score.playerName(), (k, v) -> v == null  ? new GamesWinsPair(score.isWinner()) : v.addGame(score.isWinner()));

        // Convert map into a sorted PlayerValuePair[]
        return pairMap.entrySet().stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue().getWinRate(), entry1.getValue().getWinRate())) // Sort directly using map values
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue().getWinRate())) // Create PlayerValuePair objects after sorting
                .toArray(PlayerValuePair[]::new);

    }
}
