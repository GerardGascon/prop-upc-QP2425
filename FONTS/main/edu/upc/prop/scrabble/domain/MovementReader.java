package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovementReader {
    private final IMovementReader reader;

    public MovementReader(IMovementReader reader) {
        this.reader = reader;
    }

    public Movement run() {
        String movementRaw = reader.readMove();

        String movementWord = parseMovementWord(movementRaw);
        Pair<Integer, Integer> position = parseMovementPosition(movementRaw);

        return new Movement(movementWord, 3, 2, Direction.Horizontal);
    }

    private Pair<Integer, Integer> parseMovementPosition(String movement) {
        String direction = movement.split("\\s+")[1];

        Pattern letterPattern = Pattern.compile("[A-Za-z]+");
        Matcher letterMatcher = letterPattern.matcher(direction);

        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher numberMatcher = numberPattern.matcher(direction);

        String letter = letterMatcher.group();
        String number = numberMatcher.group();

        int x = letter.charAt(0) - (int)'A';
        int y = Integer.parseInt(number);

        return new Pair<>(x, y);
    }

    private String parseMovementWord(String movement) {
        return movement.split("\\s+")[0];
    }
}
