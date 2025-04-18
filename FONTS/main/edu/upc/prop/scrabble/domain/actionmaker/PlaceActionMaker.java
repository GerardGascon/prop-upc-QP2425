package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.exceptions.PlayerDoesNotHavePieceException;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PresentPiecesWordCompleter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.crosschecks.CrossCheckUpdater;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.exceptions.InitialMoveNotInCenterException;
import edu.upc.prop.scrabble.domain.exceptions.MovementOutsideOfBoardException;
import edu.upc.prop.scrabble.domain.exceptions.WordDoesNotExistException;
import edu.upc.prop.scrabble.domain.exceptions.WordNotConnectedToOtherWordsException;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;
import edu.upc.prop.scrabble.domain.movement.MovementCleaner;
import edu.upc.prop.scrabble.domain.movement.MovementBoundsChecker;
import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.Arrays;

/**
 * Manages the steps required to perform a word placement move in the Scrabble game.
 * This includes validating the movement, checking if the word exists, and placing
 * the word on the board with the necessary pieces.
 *
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
    private final PiecesConverter piecesConverter;
    private final Board board;

    /**
     * Constructs a PlaceActionMaker instance that will manage word placement actions.
     *
     * @param movementBoundsChecker A checker that ensures the movement stays within board boundaries.
     * @param wordValidator A validator to check if the word exists in the dictionary.
     * @param piecesInHandGetter A utility to get the pieces currently in the player's hand.
     * @param movementCleaner A utility to clean and validate the pieces involved in the movement.
     * @param wordPlacer A component that places the word on the board.
     * @param presentPiecesWordCompleter A utility to check the completion of words formed by the current move.
     * @param crossCheckUpdater Updates cross-checks for word validity after placement.
     * @param stepper A stepper to proceed with the game logic after the move.
     * @param piecesConverter A utility that converts a word to a set of pieces.
     * @param board The current game board.
     */
    public PlaceActionMaker(MovementBoundsChecker movementBoundsChecker, WordValidator wordValidator,
                            PiecesInHandGetter piecesInHandGetter, MovementCleaner movementCleaner,
                            WordPlacer wordPlacer, PresentPiecesWordCompleter presentPiecesWordCompleter,
                            CrossCheckUpdater crossCheckUpdater, GameStepper stepper, PiecesConverter piecesConverter,
                            Board board) {
        this.movementBoundsChecker = movementBoundsChecker;
        this.wordValidator = wordValidator;
        this.piecesInHandGetter = piecesInHandGetter;
        this.movementCleaner = movementCleaner;
        this.wordPlacer = wordPlacer;
        this.presentPiecesWordCompleter = presentPiecesWordCompleter;
        this.crossCheckUpdater = crossCheckUpdater;
        this.stepper = stepper;
        this.piecesConverter = piecesConverter;
        this.board = board;
    }

    /**
     * Executes a word placement action on the board.
     * This method performs all necessary validations and updates the board,
     * player's pieces, and game state.
     *
     * @param movement The movement containing the word and its position.
     * @throws WordDoesNotExistException If the word being placed does not exist in the dictionary.
     * @throws MovementOutsideOfBoardException If the movement goes outside the board's boundaries.
     * @throws PlayerDoesNotHavePieceException If the player does not have the necessary pieces to place the word.
     * @see Movement
     */
    public void run(Movement movement) {
        assertInsideOfBounds(movement);
        Pair<Piece, Vector2>[] necessaryPiecesPositions = movementCleaner.run(movement);
        assertInitialMoveInCenter(necessaryPiecesPositions);
        assertWordIsConnected(movement, necessaryPiecesPositions);
        Piece[] necessaryPieces = extractNecessaryPieces(necessaryPiecesPositions);
        Vector2[] necessaryPositions = extractNecessaryPositions(necessaryPiecesPositions);

        assertNewWordsExist(necessaryPieces, necessaryPositions);
        Piece[] piecesInHand = piecesInHandGetter.run(necessaryPieces);
        wordPlacer.run(piecesInHand, movement.x(), movement.y(), movement.direction());
        crossCheckUpdater.run(movement);
        stepper.run();
    }

    private void assertWordIsConnected(Movement movement, Pair<Piece, Vector2>[] necessaryPiecesPositions) {
        if (board.isEmpty())
            return;

        Piece[] necessaryPieces = piecesConverter.run(movement.word());
        if (necessaryPieces.length == necessaryPiecesPositions.length)
            throw new WordNotConnectedToOtherWordsException("The word is not connected to any other words on the board.");
    }

    private void assertInitialMoveInCenter(Pair<Piece, Vector2>[] necessaryPiecesPositions) {
        if (!board.isEmpty())
            return;
        boolean inCenter = false;
        for (Pair<Piece, Vector2> piece: necessaryPiecesPositions) {
            if (board.isCenter(piece.second().x, piece.second().y)){
                inCenter = true;
                break;
            }
        }

        if (!inCenter)
            throw new InitialMoveNotInCenterException("The first move must go through the center of the board.");
    }

    private Piece[] extractNecessaryPieces(Pair<Piece, Vector2>[] necessaryPiecesPositions) {
        return Arrays.stream(necessaryPiecesPositions).map(Pair::first).toArray(Piece[]::new);
    }

    private Vector2[] extractNecessaryPositions(Pair<Piece, Vector2>[] necessaryPiecesPositions) {
        return Arrays.stream(necessaryPiecesPositions).map(Pair::second).toArray(Vector2[]::new);
    }

    private void assertNewWordsExist(Piece[] pieces, Vector2[] positions) {
        String[] newWords = presentPiecesWordCompleter.run(positions, pieces);
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
