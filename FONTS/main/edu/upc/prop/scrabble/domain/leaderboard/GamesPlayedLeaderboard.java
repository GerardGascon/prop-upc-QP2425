package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.TreeMap;
import java.util.Map;

/**
 * Controlador encarregat de generar una classificació de jugadors
 * segons el nombre total de partides que han jugat.
 * Processa un conjunt de resultats {@link Score} i agrupa les dades
 * per nom de jugador, retornant una classificació ordenada per nombre
 * de partides jugades (de més a menys).
 * @author Felipe Martínez Lassalle
 * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
 * @see Score
 */
public class GamesPlayedLeaderboard {

    /**
     * Executa el procés de classificació de jugadors segons el nombre de partides jugades.
     * @param scores Array de resultats {@link Score} corresponents a partides prèvies.
     * @return Un array de {@link PlayerValuePair}, ordenat descendentment pel nombre de partides jugades.
     *         Cada element representa un jugador i la seva quantitat total de partides.
     * @see PlayerValuePair
     * @see Score
     */
    public PlayerValuePair[] run(Score[] scores) {
        // Agrupa els resultats pel nom del jugador i compta les partides
        Map<String, Integer> gamesMap = new TreeMap<>();
        for (Score score : scores)
            gamesMap.compute(score.playerName(), (k, v) -> v == null ? 1 : v + 1);

        // Converteix el mapa a un array ordenat de PlayerValuePair (descendent per nombre de partides)
        return gamesMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue()))
                .toArray(PlayerValuePair[]::new);
    }
}
