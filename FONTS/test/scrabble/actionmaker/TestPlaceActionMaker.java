package scrabble.actionmaker;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.movement.MovementBoundsChecker;
import edu.upc.prop.scrabble.domain.movement.MovementCleaner;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Before;
import org.junit.Test;
import scrabble.stubs.BoardViewStub;
import scrabble.stubs.PiecePrinterStub;

import static org.junit.Assert.*;

public class TestPlaceActionMaker {
    private BoardViewStub boardViewStub;
    private Board board;
    private MovementMaker movementMaker;
    private Bag bag;
    private DAWG dawg;
    private PlaceActionMaker placeActionMaker;
    private PiecePrinterStub piecePrinterStub;

    @Before
    public void setup() {
        boardViewStub = new BoardViewStub();
        board = new StandardBoard();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        PiecesConverter piecesConverter = new PiecesConverter();
        Player player = new Player("name", false);
        WordPlacer wordPlacer = new WordPlacer(player, board, boardViewStub, pointCalculator);
        MovementBoundsChecker boundsChecker = new MovementBoundsChecker(board, piecesConverter);
        dawg = new DAWG();
        WordValidator wordValidator = new WordValidator(dawg);
        Bag bag = new Bag();
        piecePrinterStub = new PiecePrinterStub();
        PiecesInHandGetter piecesInHandGetter = new PiecesInHandGetter(bag, player, piecePrinterStub);
        MovementCleaner movementCleaner = new MovementCleaner(board, piecesConverter);
        placeActionMaker = new PlaceActionMaker(player, boundsChecker, wordValidator, piecesInHandGetter, movementCleaner, wordPlacer);
    }

    @Test
    public void placePieceSendsUpdateViewCallback() {
        Movement movement = new Movement("POTATO", 1, 1, Direction.Horizontal);
        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("POTATO");

        placeActionMaker.run(movement);

        assertTrue(boardViewStub.getUpdateCallReceived());
    }
}
