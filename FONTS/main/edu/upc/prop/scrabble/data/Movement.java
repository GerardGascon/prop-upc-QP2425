package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.utils.Direction;

public record Movement(String word, int x, int y, Direction direction) {
}
