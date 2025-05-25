package edu.upc.prop.scrabble.data.leaderboard;


/**
 * Representa les dades rellevants d'un jugador un cop finalitzada una partida.
 * Aquest registre encapsula la puntuació total, si el jugador ha estat guanyador,
 * i el seu nom identificador.
 * @param scoreValue Puntuació total obtinguda pel jugador en la partida.
 * @param isWinner Valor booleà que indica si el jugador ha estat el guanyador de la partida.
 * @param playerName Nom del jugador, utilitzat com a identificador.
 * @author Felipe Martínez Lassalle
 */
public record Score(int scoreValue, boolean isWinner, String playerName) { }