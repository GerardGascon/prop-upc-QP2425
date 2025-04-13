package edu.upc.prop.scrabble.data.properties;

import edu.upc.prop.scrabble.data.board.BoardType;

//Conte la informacio de la configuracio de la partida :D
public record GameProperties(Language language, BoardType boardType, int players, int cpus) {

}
