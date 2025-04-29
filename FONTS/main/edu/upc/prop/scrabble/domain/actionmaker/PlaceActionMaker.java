package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.exceptions.PlayerDoesNotHavePieceException;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.ai.AnchorUpdater;
import edu.upc.prop.scrabble.domain.board.PresentPiecesWordCompleter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.ai.CrossCheckUpdater;
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
import edu.upc.prop.scrabble.domain.turns.TurnResult;
import edu.upc.prop.scrabble.utils.IRand;
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
    private final AnchorUpdater anchorUpdater;

    /**
     * Constructs a PlaceActionMaker instance that will manage word placement actions.
     *
     * @param wordPlacer         A component that places the word on the board.
     * @param stepper            A stepper to proceed with the game logic after the move.
     * @param piecesConverter    A utility that converts a word to a set of pieces.
     * @param board              The current game board.
     * @param crossChecks
     * @param dawg
     */
    public PlaceActionMaker(Player player, Bag bag,
                            WordPlacer wordPlacer,
                            GameStepper stepper, PiecesConverter piecesConverter,
                            Board board, AnchorUpdater anchorUpdater, CrossChecks crossChecks, DAWG dawg, IRand rand) {
        this.movementBoundsChecker = new MovementBoundsChecker(board, piecesConverter);
        this.movementCleaner = new MovementCleaner(board, piecesConverter);
        this.wordValidator = new WordValidator(dawg);
        this.piecesInHandGetter = new PiecesInHandGetter(bag, player, rand);
        this.wordPlacer = wordPlacer;
        this.presentPiecesWordCompleter = new PresentPiecesWordCompleter(board);
        this.crossCheckUpdater = new CrossCheckUpdater(piecesConverter, crossChecks, board, dawg);
        this.stepper = stepper;
        this.piecesConverter = piecesConverter;
        this.board = board;
        this.anchorUpdater = anchorUpdater;
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
        anchorUpdater.run(movement);
        stepper.run(TurnResult.Place);
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
