package edu.upc.prop.scrabble.data.properties;

import edu.upc.prop.scrabble.data.board.BoardType;

public record GameProperties(Language language, BoardType boardType, int players, int cpus) {

}
