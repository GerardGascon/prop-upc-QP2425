package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.TreeMap;
import java.util.Map;

/**
 * Controlador de Leaderboard encarregat de generar una classificació
 * dels jugadors segons el nombre total de partides guanyades.
 * Processa un conjunt de resultats {@link Score} i compta les victòries
 * per jugador, retornant una classificació ordenada descendentment.
 * @author Felipe Martínez Lassalle
 * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
 * @see Score
 */
public class GamesWonLeaderboard {
    /**
     * Crea una instància del filtre per nombre de partides guanyades.
     */
    public GamesWonLeaderboard() {

    }

    /**
     * Executa el procés de classificació de jugadors segons el nombre de partides guanyades.
     *
     * @param scores Array de resultats {@link Score} corresponents a partides prèvies.
     * @return Un array de {@link PlayerValuePair}, ordenat descendentment pel nombre de partides guanyades.
     *         Cada element representa un jugador i la seva quantitat total de victòries.
     * @see Score
     * @see PlayerValuePair
     */
    public PlayerValuePair[] run(Score[] scores) {
        // Agrupa els resultats pel nom del jugador i compta només les partides guanyades
        Map<String, Integer> winsMap = new TreeMap<>();
        for (Score score : scores) {
            winsMap.compute(score.playerName(), (k, v) ->
                    v == null
                            ? (score.isWinner() ? 1 : 0)
                            : (score.isWinner() ? v + 1 : v));
        }

        // Converteix el mapa a un array ordenat de PlayerValuePair (descendent per nombre de victòries)
        return winsMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue()))
                .toArray(PlayerValuePair[]::new);
    }
}
