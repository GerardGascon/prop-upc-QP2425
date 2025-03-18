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
        Direction direction = parseMovementDirection(movementRaw);

        return new Movement(movementWord, position.first(), position.second(), direction);
    }

    private Direction parseMovementDirection(String movement) {
        if (isCoordinateForHorizontal(movement))
            return Direction.Horizontal;

        return Direction.Vertical;
    }

    private static boolean isCoordinateForHorizontal(String movement) {
        String direction = movement.split("\\s+")[1];
        return getCoordinate(movement).letter().charAt(0) == direction.charAt(0);
    }

    private Pair<Integer, Integer> parseMovementPosition(String movement) {
        Coordinate coordinate = getCoordinate(movement);

        int x = coordinate.letter().charAt(0) - (int) 'A';
        int y = Integer.parseInt(coordinate.number());

        return new Pair<>(x, y);
    }

    private static Coordinate getCoordinate(String movement) {
        String direction = movement.split("\\s+")[1];

        Pattern letterPattern = Pattern.compile("[A-Za-z]+");
        Matcher letterMatcher = letterPattern.matcher(direction);
        boolean _ = letterMatcher.find();

        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher numberMatcher = numberPattern.matcher(direction);
        boolean _ = numberMatcher.find();
        return new Coordinate(letterMatcher.group(), numberMatcher.group());
    }

    private record Coordinate(String letter, String number) {
    }

    private String parseMovementWord(String movement) {
        return movement.split("\\s+")[0];
    }
}
