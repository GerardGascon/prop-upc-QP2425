package edu.upc.prop.scrabble.domain.leaderboard;

import edu.upc.prop.scrabble.data.leaderboard.Score;

import java.util.TreeMap;
import java.util.Map;

/**
 * Controlador de Leaderboard encarregat de generar una classificació
 * dels jugadors segons el seu percentatge de victòries en les partides jugades.
 * Calcula el ratio de partides guanyades respecte al total de partides jugades per cada jugador.
 * @author Felipe Martínez Lassalle
 * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
 * @see GamesWinsPair
 */
public class WinRateLeaderboard {
    /**
     * Crea una instància del filtre pel percentatge de victòries.
     */
    public WinRateLeaderboard() {

    }

    /**
     * Executa el controlador per calcular i ordenar el percentatge de victòries dels jugadors.
     *
     * @param scores Array amb tots els objectes {@code Score} que representen les partides jugades.
     * @return Array de {@code PlayerValuePair} amb el nom del jugador i el seu percentatge de victòries,
     *         ordenat de major a menor percentatge.
     * @see edu.upc.prop.scrabble.data.leaderboard.Leaderboard
     * @see Score
     * @see PlayerValuePair
     * @see GamesWinsPair
     */
    public PlayerValuePair[] run(Score[] scores) {
        // Agrupa per nom de jugador i manté un registre de partides jugades i guanyades
        Map<String, GamesWinsPair> pairMap = new TreeMap<>();
        for (Score score : scores) {
            pairMap.compute(score.playerName(),
                    (k, v) -> v == null ? new GamesWinsPair(score.isWinner()) : v.addGame(score.isWinner()));
        }

        // Converteix el map a un array ordenat de PlayerValuePair segons el percentatge de victòries
        return pairMap.entrySet().stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue().getWinRate(), entry1.getValue().getWinRate()))
                .map(entry -> new PlayerValuePair(entry.getKey(), entry.getValue().getWinRate()))
                .toArray(PlayerValuePair[]::new);
    }
}
