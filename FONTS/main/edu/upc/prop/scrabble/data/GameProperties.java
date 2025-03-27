package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.BoardType;
import edu.upc.prop.scrabble.data.localization.Locale;

public record GameProperties(String[] playerNames, int playerNumber, Locale locale, BoardType boardType) {
}
