package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.domain.movement.IMovementMaker;
import edu.upc.prop.scrabble.presenter.terminal.utils.IReader;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovementMaker implements IMovementMaker {
    private final IReader reader;

    public MovementMaker(IReader reader) {
        this.reader = reader;
    }

    @Override
    public Movement makeMove() {
        /*
         * Format:
         *   WORD XY
         *   If X is a letter, it means horizontal word
         *   If Y is a letter, it means vertical word
         */
        String movementRaw = reader.readLine();
        
        String movementWord = parseMovementWord(movementRaw);
        Vector2 position = parseMovementPosition(movementRaw);
        Direction direction = parseMovementDirection(movementRaw);

        return new Movement(movementWord, position.x, position.y, direction);
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

    private Vector2 parseMovementPosition(String movement) {
        Coordinate coordinate = getCoordinate(movement);

        int x = coordinate.letter().charAt(0) - (int) 'A';
        int y = Integer.parseInt(coordinate.number());

        return new Vector2(x, y);
    }

    private static Coordinate getCoordinate(String movement) {
        String direction = movement.split("\\s+")[1];

        String letter = parseLetterCoordinate(direction);
        String number = parseNumberCoordinate(direction);

        return new Coordinate(letter, number);
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

    private String parseMovementWord(String movement) {
        return movement.split("\\s+")[0];
    }
}
