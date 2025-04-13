package edu.upc.prop.scrabble.presenter.terminal.movements;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovementParser {
    public static Movement parse(String input) {
        String movementWord = parseMovementWord(input);
        Vector2 position = parseMovementPosition(input);
        Direction direction = parseMovementDirection(input);
        return new Movement(movementWord, position.x, position.y, direction);
    }

    private static Direction parseMovementDirection(String movement) {
        if (isCoordinateForHorizontal(movement))
            return Direction.Horizontal;

        return Direction.Vertical;
    }

    private static boolean isCoordinateForHorizontal(String movement) {
        String direction = movement.split("\\s+")[1];
        return getCoordinate(movement).letter().charAt(0) == direction.charAt(0);
    }

    private static Vector2 parseMovementPosition(String movement) {
        MovementParser.Coordinate coordinate = getCoordinate(movement);

        int x = coordinate.letter().charAt(0) - (int) 'A';
        int y = Integer.parseInt(coordinate.number()) - 1;

        return new Vector2(x, y);
    }

    private static MovementParser.Coordinate getCoordinate(String movement) {
        String direction = movement.split("\\s+")[1];

        String letter = parseLetterCoordinate(direction);
        String number = parseNumberCoordinate(direction);

        return new MovementParser.Coordinate(letter, number);
    }

    private static String parseNumberCoordinate(String direction) {
        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher numberMatcher = numberPattern.matcher(direction);
        boolean _ = numberMatcher.find();
        return numberMatcher.group();
    }

    private static String parseLetterCoordinate(String direction) {
        Pattern letterPattern = Pattern.compile("[A-Za-z]+");
        Matcher letterMatcher = letterPattern.matcher(direction);
        boolean _ = letterMatcher.find();
        return letterMatcher.group();
    }

    private record Coordinate(String letter, String number) {
    }

    private static String parseMovementWord(String movement) {
        return movement.split("\\s+")[0];
    }
}
