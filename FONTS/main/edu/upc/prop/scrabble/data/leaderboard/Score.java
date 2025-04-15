package edu.upc.prop.scrabble.data.leaderboard;

/**
 * Record of relevant values of a game
 * @param scoreValue Total score achieved by the player in the game
 * @param isWinner True if the player won the game
 * @param playerName Name used by the player
 * @author Felipe Martínez Lassalle
 */
public record Score(int scoreValue, boolean isWinner, String playerName) {

}
