package scrabble.actionmaker;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.EnglishCrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.PresentPiecesWordCompleter;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.ai.CrossCheckUpdater;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.exceptions.InitialMoveNotInCenterException;
import edu.upc.prop.scrabble.domain.exceptions.MovementOutsideOfBoardException;
import edu.upc.prop.scrabble.domain.exceptions.WordDoesNotExistException;
import edu.upc.prop.scrabble.domain.exceptions.WordNotConnectedToOtherWordsException;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.game.IEndScreen;
import edu.upc.prop.scrabble.domain.movement.MovementBoundsChecker;
import edu.upc.prop.scrabble.domain.movement.MovementCleaner;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;
import edu.upc.prop.scrabble.domain.turns.Endgame;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.IRand;
import org.junit.Before;
import org.junit.Test;
import scrabble.stubs.*;

import static org.junit.Assert.*;

public class TestPlaceActionMaker {
    private BoardViewStub boardViewStub;
    private Board board;
    private Bag bag;
    private DAWG dawg;
    private PlaceActionMaker sut;
    private PiecePrinterStub piecePrinterStub;
    private Player player;

    @Before
    public void setup() {
        boardViewStub = new BoardViewStub();
        board = new StandardBoard();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        PiecesConverter piecesConverter = new PiecesConverter();
        player = new Player("name", false);
        WordPlacer wordPlacer = new WordPlacer(player, board, boardViewStub, pointCalculator);
        MovementBoundsChecker boundsChecker = new MovementBoundsChecker(board, piecesConverter);
        dawg = new DAWG();
        WordValidator wordValidator = new WordValidator(dawg);
        bag = new Bag();
        piecePrinterStub = new PiecePrinterStub();
        IRand rand = new RandStub(0);
        PiecesInHandGetter piecesInHandGetter = new PiecesInHandGetter(bag, player, rand);
        MovementCleaner movementCleaner = new MovementCleaner(board, piecesConverter);
        PresentPiecesWordCompleter presentPiecesWordCompleter = new PresentPiecesWordCompleter(wordGetter);
        CrossChecks crossChecks = new EnglishCrossChecks(board.getSize());
        CrossCheckUpdater crossCheckUpdater = new CrossCheckUpdater(piecesConverter, crossChecks, board, dawg);
        Turn turn = new Turn(new Endgame(new Player[]{player}), new IGamePlayer[]{new GamePlayerStub()});
        IEndScreen endScreen = new EndScreenStub();
        GameStepper stepper = new GameStepper(turn, new Leaderboard(), new Player[]{player}, endScreen);
        sut = new PlaceActionMaker(boundsChecker, wordValidator, piecesInHandGetter, movementCleaner, wordPlacer,
                presentPiecesWordCompleter, crossCheckUpdater, stepper, piecesConverter, board);
    }

    @Test
    public void placePieceSendsUpdateViewCallback() {
        Movement movement = new Movement("POTATO", 5, 7, Direction.Horizontal);
        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("POTATO");
        player.addPiece(new Piece("P", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("T", 1));
        player.addPiece(new Piece("A", 1));
        player.addPiece(new Piece("T", 1));
        player.addPiece(new Piece("O", 1));

        sut.run(movement);

        assertTrue(boardViewStub.getUpdateCallReceived());
    }

    @Test(expected = WordDoesNotExistException.class)
    public void placePieceThrowsExceptionIfWordDoesNotExist() {
        Movement movement = new Movement("POTATO", 5, 7, Direction.Horizontal);

        sut.run(movement);
    }

    @Test(expected = MovementOutsideOfBoardException.class)
    public void placePieceThrowsExceptionIfMovementIsOutsideOfBoard() {
        Movement movement = new Movement("POTATO", 10, 1, Direction.Horizontal);
        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("POTATO");

        sut.run(movement);
    }

    @Test
    public void placePieceThrowsExceptionIfContainsCombinedWordThatDoesNotExist() {
        Movement movement1 = new Movement("HOLA", 7, 7, Direction.Horizontal);
        Movement movement2 = new Movement("ALL", 10, 7, Direction.Vertical);
        Movement movement3 = new Movement("COLD", 8, 8, Direction.Horizontal);
        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("ALL");
        wordAdder.run("COLD");
        wordAdder.run("HOLA");

        player.addPiece(new Piece("H", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("L", 1));
        player.addPiece(new Piece("A", 1));
        sut.run(movement1);

        player.addPiece(new Piece("L", 1));
        player.addPiece(new Piece("L", 1));
        sut.run(movement2);

        player.addPiece(new Piece("C", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("D", 1));
        assertThrows(WordDoesNotExistException.class, () -> sut.run(movement3));
    }

    @Test
    public void placePiecesSubtractsPiecesFromPlayer() {
        Movement movement = new Movement("HOLA", 7, 7, Direction.Horizontal);
        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("HOLA");

        player.addPiece(new Piece("H", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("L", 1));
        player.addPiece(new Piece("A", 1));

        sut.run(movement);

        assertEquals(0, player.getHand().length);
    }

    @Test
    public void placedPiecesInHandGetReplacedByBagPieces() {
        Movement movement = new Movement("HOLA", 7, 7, Direction.Horizontal);
        bag.add(new Piece("T", 1));
        bag.add(new Piece("E", 1));
        bag.add(new Piece("S", 1));
        bag.add(new Piece("T", 1));

        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("HOLA");

        player.addPiece(new Piece("H", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("L", 1));
        player.addPiece(new Piece("A", 1));

        sut.run(movement);

        assertEquals(4, player.getHand().length);
        assertEquals("T", player.getHand()[0].letter());
        assertEquals("E", player.getHand()[1].letter());
        assertEquals("S", player.getHand()[2].letter());
        assertEquals("T", player.getHand()[3].letter());
    }

    @Test
    public void placedPiecesGetPlacedUpdatesBoard() {
        Movement movement = new Movement("HOLA", 7, 7, Direction.Horizontal);
        bag.add(new Piece("T", 1));
        bag.add(new Piece("E", 1));
        bag.add(new Piece("S", 1));
        bag.add(new Piece("T", 1));

        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("HOLA");

        player.addPiece(new Piece("H", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("L", 1));
        player.addPiece(new Piece("A", 1));

        sut.run(movement);

        assertEquals(4, player.getHand().length);
        assertEquals("T", player.getHand()[0].letter());
        assertEquals("E", player.getHand()[1].letter());
        assertEquals("S", player.getHand()[2].letter());
        assertEquals("T", player.getHand()[3].letter());
    }

    @Test
    public void continuousPlacesCorrectlyPlacedOnBoard() {
        Movement movement1 = new Movement("BOIL", 7, 7, Direction.Vertical);
        Movement movement2 = new Movement("BOILER", 7, 7, Direction.Vertical);
        Movement movement3 = new Movement("REST", 7, 12, Direction.Horizontal);
        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("BOIL");
        wordAdder.run("BOILER");
        wordAdder.run("REST");

        player.addPiece(new Piece("B", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("I", 1));
        player.addPiece(new Piece("L", 1));
        player.addPiece(new Piece("E", 1));
        player.addPiece(new Piece("R", 1));
        player.addPiece(new Piece("E", 1));
        player.addPiece(new Piece("S", 1));
        player.addPiece(new Piece("T", 1));

        sut.run(movement1);
        sut.run(movement2);
        sut.run(movement3);

        assertEquals("B", board.getCellPiece(7, 7).letter());
        assertEquals("O", board.getCellPiece(7, 8).letter());
        assertEquals("I", board.getCellPiece(7, 9).letter());
        assertEquals("L", board.getCellPiece(7, 10).letter());
        assertEquals("E", board.getCellPiece(7, 11).letter());
        assertEquals("R", board.getCellPiece(7, 12).letter());
        assertEquals("E", board.getCellPiece(8, 12).letter());
        assertEquals("S", board.getCellPiece(9, 12).letter());
        assertEquals("T", board.getCellPiece(10, 12).letter());
    }

    @Test(expected = WordNotConnectedToOtherWordsException.class)
    public void placePieceThrowsExceptionIfNotConnectedToOtherWord() {
        Movement movement1 = new Movement("HOLA", 7, 7, Direction.Horizontal);
        Movement movement2 = new Movement("HOLA", 1, 10, Direction.Horizontal);
        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("HOLA");

        player.addPiece(new Piece("H", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("L", 1));
        player.addPiece(new Piece("A", 1));

        sut.run(movement1);

        player.addPiece(new Piece("H", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("L", 1));
        player.addPiece(new Piece("A", 1));

        sut.run(movement2);
    }

    @Test
    public void placeWordWithBlankPiece() {
        Movement movement = new Movement("HOlA", 7, 7, Direction.Horizontal);

        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("HOLA");

        player.addPiece(new Piece("H", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("#", 1, true));
        player.addPiece(new Piece("A", 1));

        sut.run(movement);

        assertEquals("H", board.getCellPiece(7, 7).letter());
        assertEquals("O", board.getCellPiece(8, 7).letter());
        assertEquals("L", board.getCellPiece(9, 7).letter());
        assertEquals("A", board.getCellPiece(10, 7).letter());
    }

    @Test
    public void placeFirstOutsideOfCenter() {
        Movement movement = new Movement("HOlA", 1, 1, Direction.Horizontal);

        WordAdder wordAdder = new WordAdder(dawg);
        wordAdder.run("HOLA");

        player.addPiece(new Piece("H", 1));
        player.addPiece(new Piece("O", 1));
        player.addPiece(new Piece("#", 1, true));
        player.addPiece(new Piece("A", 1));

        assertThrows(InitialMoveNotInCenterException.class, () -> sut.run(movement));
    }
}
