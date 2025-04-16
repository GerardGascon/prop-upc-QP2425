package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PresentPiecesWordCompleter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.exceptions.MovementOutsideOfBoardException;
import edu.upc.prop.scrabble.domain.exceptions.WordDoesNotExistException;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;
import edu.upc.prop.scrabble.domain.movement.MovementCleaner;
import edu.upc.prop.scrabble.domain.movement.MovementBoundsChecker;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PlaceActionMaker {
    private final Player player;
    private final MovementBoundsChecker movementBoundsChecker;
    private final WordValidator wordValidator;
    private final PiecesInHandGetter piecesInHandGetter;
    private final MovementCleaner movementCleaner;
    private final WordPlacer wordPlacer;
    private final PresentPiecesWordCompleter presentPiecesWordCompleter;

    public PlaceActionMaker(Player player, MovementBoundsChecker MovementBoundsChecker, WordValidator wordValidator, PiecesInHandGetter piecesInHandGetter, MovementCleaner movementCleaner, WordPlacer wordPlacer, PresentPiecesWordCompleter presentPiecesWordCompleter) {
        this.player = player;
        this.movementBoundsChecker = MovementBoundsChecker;
        this.wordValidator = wordValidator;
        this.piecesInHandGetter = piecesInHandGetter;
        this.movementCleaner = movementCleaner;
        this.wordPlacer = wordPlacer;
        this.presentPiecesWordCompleter = presentPiecesWordCompleter;
    }

    public void run(Movement movement) {
        assertInsideOfBounds(movement);
        Piece[] necessaryPieces = movementCleaner.run(movement);
        assertNewWordsExist(necessaryPieces, movement.x(), movement.y(), movement.direction());

        Piece[] piecesInHand = piecesInHandGetter.run(necessaryPieces);
        wordPlacer.run(piecesInHand, movement.x(), movement.y(), movement.direction());
        //TODO: Draw new pieces to replaced the used ones
        //TODO: Add call to update views
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
