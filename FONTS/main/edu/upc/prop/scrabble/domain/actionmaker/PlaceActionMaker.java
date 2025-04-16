package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.exceptions.PlayerDoesNotHavePieceException;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PresentPiecesWordCompleter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.crosschecks.CrossCheckUpdater;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.exceptions.MovementOutsideOfBoardException;
import edu.upc.prop.scrabble.domain.exceptions.WordDoesNotExistException;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;
import edu.upc.prop.scrabble.domain.movement.MovementCleaner;
import edu.upc.prop.scrabble.domain.movement.MovementBoundsChecker;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the steps needed to perform a place move inside the game.
 * @author Gerard Gascón
 */
public class PlaceActionMaker {
    private final MovementBoundsChecker movementBoundsChecker;
    private final WordValidator wordValidator;
    private final PiecesInHandGetter piecesInHandGetter;
    private final MovementCleaner movementCleaner;
    private final WordPlacer wordPlacer;
    private final PresentPiecesWordCompleter presentPiecesWordCompleter;
    private final CrossCheckUpdater crossCheckUpdater;
    private final GameStepper stepper;

    public PlaceActionMaker(MovementBoundsChecker MovementBoundsChecker, WordValidator wordValidator, PiecesInHandGetter piecesInHandGetter, MovementCleaner movementCleaner, WordPlacer wordPlacer, PresentPiecesWordCompleter presentPiecesWordCompleter, CrossCheckUpdater crossCheckUpdater, GameStepper stepper) {
        this.movementBoundsChecker = MovementBoundsChecker;
        this.wordValidator = wordValidator;
        this.piecesInHandGetter = piecesInHandGetter;
        this.movementCleaner = movementCleaner;
        this.wordPlacer = wordPlacer;
        this.presentPiecesWordCompleter = presentPiecesWordCompleter;
        this.crossCheckUpdater = crossCheckUpdater;
        this.stepper = stepper;
    }

    /**
     * Place a word on the board
     * @param movement The movement to make
     * @throws WordDoesNotExistException If the word you are trying to place does not exist
     * @throws MovementOutsideOfBoardException If the movement you are trying to make is outside the bounds of the board
     * @throws PlayerDoesNotHavePieceException If you are placing a word that contains pieces not present in the player's hand
     * @see Movement
     */
    public void run(Movement movement) {
        assertInsideOfBounds(movement);
        Piece[] necessaryPieces = movementCleaner.run(movement);
        assertNewWordsExist(necessaryPieces, movement.x(), movement.y(), movement.direction());
        Piece[] piecesInHand = piecesInHandGetter.run(necessaryPieces);
        wordPlacer.run(piecesInHand, movement.x(), movement.y(), movement.direction());
        crossCheckUpdater.run(movement);
        stepper.run();
    }

    private void assertNewWordsExist(Piece[] pieces, int x, int y, Direction direction) {
        List<Vector2> positions = new ArrayList<>();
        for (int i = 0; i < pieces.length; i++) {
            if (direction == Direction.Horizontal)
                positions.add(new Vector2(x + i, y));
            else
                positions.add(new Vector2(x, y + i));
        }

        String[] newWords = presentPiecesWordCompleter.run(positions.toArray(Vector2[]::new), pieces);
        for (String word : newWords)
            assertWordExists(word);
    }

    private void assertWordExists(String word) {
        if (!wordValidator.run(word))
            throw new WordDoesNotExistException("The word \"" + word + "\" does not exist");
    }

    private void assertInsideOfBounds(Movement movement) {
        if (!movementBoundsChecker.run(movement))
            throw new MovementOutsideOfBoardException("The movement \"" + movement + "\" is outside of the board bounds.");
    }
}
