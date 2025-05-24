package edu.upc.prop.scrabble.data.properties;

import edu.upc.prop.scrabble.data.board.BoardType;

import java.util.List;

/**
 * Representa la configuració i informació bàsica d'una partida de Scrabble.
 * Conté dades com l'idioma escollit, el tipus de tauler, els jugadors humans i les CPU participants.
 * @param language Idioma de la partida (català, castellà, anglès).
 * @param boardType Tipus de tauler utilitzat (clàssic, duplicat, personalitzat).
 * @param players Llista amb els noms dels jugadors humans.
 * @param Cpus Llista amb els noms o identificadors dels jugadors controlats per la màquina.
 * @author Biel Pérez Silvestre
 */
public record GameProperties(Language language, BoardType boardType, List<String> players, List<String> Cpus) {
}
