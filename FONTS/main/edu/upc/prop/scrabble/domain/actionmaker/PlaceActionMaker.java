package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.exceptions.MovementOutsideOfBoardException;
import edu.upc.prop.scrabble.domain.exceptions.WordDoesNotExistException;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;
import edu.upc.prop.scrabble.domain.movement.MovementCleaner;
import edu.upc.prop.scrabble.domain.movement.MovementBoundsChecker;

public class PlaceActionMaker {
    private final Player player;
    private final MovementBoundsChecker MovementBoundsChecker;
    private final WordValidator wordValidator;
    private final PiecesInHandGetter piecesInHandGetter;
    private final MovementCleaner movementCleaner;
    private final WordPlacer wordPlacer;

    public PlaceActionMaker(Player player, MovementBoundsChecker MovementBoundsChecker, WordValidator wordValidator, PiecesInHandGetter piecesInHandGetter, MovementCleaner movementCleaner, WordPlacer wordPlacer) {
        this.player = player;
        this.MovementBoundsChecker = MovementBoundsChecker;
        this.wordValidator = wordValidator;
        this.piecesInHandGetter = piecesInHandGetter;
        this.movementCleaner = movementCleaner;
        this.wordPlacer = wordPlacer;
    }

    public void run(Movement movement) {
        assertInsideOfBounds(movement);
        assertWordExists(movement);

        Piece[] necessaryPieces = movementCleaner.run(movement);
        Piece[] piecesInHand = piecesInHandGetter.run(necessaryPieces);
        int score = wordPlacer.run(piecesInHand, movement.x(), movement.y(), movement.direction());
        player.addScore(score);
        //TODO: Draw new pieces to replaced the used ones
        //TODO: Add call to update views
    }

    private void assertWordExists(Movement movement) {
        if (!wordValidator.runString(movement.word()))
            throw new WordDoesNotExistException("The word \"" + movement.word() + "\" does not exist");
    }

    private void assertInsideOfBounds(Movement movement) {
        if (!MovementBoundsChecker.run(movement))
            throw new MovementOutsideOfBoardException("The movement \"" + movement + "\" is outside of the board bounds.");
    }
}
