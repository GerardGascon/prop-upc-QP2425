package edu.upc.prop.scrabble.data.properties;

import edu.upc.prop.scrabble.data.board.BoardType;

import java.util.List;

/**
 * Conte la informacio de la partida
 * @param language
 * @param boardType
 * @param players
 * @param Cpus
 */
public record GameProperties(Language language, BoardType boardType, List<String> players, List<String> Cpus) {
}
