package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.TreeMap;
import java.util.Map;

/**
 * Controlador de Leaderboard encarregat de generar una classificació
 * dels jugadors segons la puntuació total acumulada en totes les partides.
 * Cada jugador és identificat pel seu nom, i la seva puntuació total
 * es calcula sumant totes les puntuacions obtingudes a les diferents partides.
 * @author Felipe Martínez Lassalle
 * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
 */
public class TotalScoreLeaderboard {
    /**
     * Crea una instància del filtre per la suma de puntuació en totes les partides.
     */
    public TotalScoreLeaderboard() {

    }

    /**
     * Executa el controlador per generar la classificació de puntuació total.
     *
     * @param scores Array amb tots els objectes {@code Score} de les partides jugades.
     * @return Un array de {@code PlayerValuePair} que conté el nom dels jugadors
     *         i la seva puntuació total, ordenats de major a menor valor.
     * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
     * @see Score
     * @see PlayerValuePair
     */
    public PlayerValuePair[] run(Score[] scores) {
        // Agrupa per nom de jugador i suma la seva puntuació total
        Map<String, Integer> scoreMap = new TreeMap<>();
        for (Score score : scores)
            scoreMap.put(score.playerName(), scoreMap.getOrDefault(score.playerName(), 0) + score.scoreValue());

        // Converteix el map a un array ordenat de PlayerValuePair
        return scoreMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue()))
                .toArray(PlayerValuePair[]::new);
    }
}
