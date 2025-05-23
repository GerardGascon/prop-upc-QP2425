package edu.upc.prop.scrabble.data.leaderboard;

/**
 * Record dels valors rellevants d'un jugador resultants d'una partida finalitzada
 * @param scoreValue Puntuació total obtinguda
 * @param isWinner Vertader en cas de ser el guanyador/a
 * @param playerName Nom que identifica al jugador
 * @author Felipe Martínez Lassalle
 */
public record Score(int scoreValue, boolean isWinner, String playerName) { }