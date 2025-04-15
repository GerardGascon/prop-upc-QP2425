package edu.upc.prop.scrabble.data.leaderboard;

/**
 * Record of relevant values of a game
 * @param scoreValue
 * @param isWinner
 * @param playerName
 * @author Felipe Martínez Lassalle
 */
public record Score(int scoreValue, boolean isWinner, String playerName) {

}
