package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.Map;
import java.util.TreeMap;

/**
 * Controlador de Leaderboard encarregat de generar una classificació
 * dels jugadors basada en la puntuació més alta assolida en una partida.
 * A partir d’un conjunt de resultats {@link Score}, es calcula la màxima
 * puntuació per jugador i es retorna una classificació ordenada descendentment.
 * @author Felipe Martínez Lassalle
 * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
 * @see Score
 */
public class MaxScoreLeaderboard {
    /**
     * Crea una instància de l'ordenació per puntuació màxima
     */
    public MaxScoreLeaderboard() {

    }

    /**
     * Executa el procés de classificació de jugadors segons la seva puntuació més alta en una sola partida.
     *
     * @param scores Array de resultats {@link Score} corresponents a partides jugades.
     * @return Un array de {@link PlayerValuePair}, ordenat descendentment segons la puntuació màxima obtinguda
     *         per cada jugador.
     * @see Score
     * @see PlayerValuePair
     */
    public PlayerValuePair[] run(Score[] scores) {
        // Agrupa pel nom del jugador i guarda només la puntuació màxima
        Map<String, Integer> scoreMap = new TreeMap<>();
        for (Score score : scores)
            scoreMap.compute(score.playerName(),
                    (k, v) -> v == null || score.scoreValue() > v ? score.scoreValue() : v);

        // Converteix el mapa en un array ordenat de PlayerValuePair (descendent per puntuació màxima)
        return scoreMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue()))
                .toArray(PlayerValuePair[]::new);
    }
}
