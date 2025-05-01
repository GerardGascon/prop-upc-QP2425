package scrabble.swing;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.JuniorBoard;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.board.SuperBoard;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.PremiumTileTypeFiller;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardCell;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardEmptyTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardPieceTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium.*;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.After;
import org.junit.Test;
import scrabble.swing.stubs.HandViewStub;
import scrabble.swing.utils.SwingTest;
import scrabble.swing.utils.TestWindow;

import static org.junit.Assert.*;

public class TestBoardView extends SwingTest {
    private TestWindow<BoardView> view;

    private static int getTileIndex(int x, int y, int boardSize) {
        return y * boardSize + x;
    }

    private static BoardTile getTile(TestWindow<BoardView> view, int x, int y, int boardSize) {
        return view.getComponentOfType(BoardCell.class, getTileIndex(x, y, boardSize)).getTile();
    }

    @After
    public void tearDown() {
        view.close();
    }

    @Test
    public void cellClickAsksHandForSelectedPiece() {
        HandViewStub handViewStub = new HandViewStub();
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub));
        BoardTile c = getTile(view, 10, 10, 21);

        c.doClick();

        assertTrue("Hand view getPiece() was not requested", handViewStub.getGetPieceRequestCalled());
    }

    @Test
    public void cellTileChangeReplacesTile() {
        HandViewStub handViewStub = new HandViewStub();
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub));

        view.getPanel().changeTile(new BoardPieceTile("A", 1, 0, 0, handViewStub), 0, 0);

        assertTrue(getTile(view, 0, 0, 21) instanceof BoardPieceTile);
    }

    @Test
    public void cellTileIsEmptyByDefault() {
        HandViewStub handViewStub = new HandViewStub();
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub));

        assertTrue(getTile(view, 0, 0, 21) instanceof BoardEmptyTile);
    }

    @Test
    public void boardGetsUpdatedWhenPieceIsPlaced() {
        HandViewStub handViewStub = new HandViewStub();
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub));
        Board board = new SuperBoard();
        Player player = new Player("test", false);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer placer = new WordPlacer(player, board, view.getPanel(), pointCalculator);

        placer.run(new Piece[]{new Piece("A", 3), new Piece("B", 2)}, 10, 10, Direction.Horizontal);

        assertTrue(getTile(view, 10, 10, 21) instanceof BoardPieceTile);
        assertEquals("A", ((BoardPieceTile) getTile(view, 10, 10, 21)).getLetter());
        assertEquals(3, ((BoardPieceTile) getTile(view, 10, 10, 21)).getPoints());

        assertTrue(getTile(view, 11, 10, 21) instanceof BoardPieceTile);
        assertEquals("B", ((BoardPieceTile) getTile(view, 11, 10, 21)).getLetter());
        assertEquals(2, ((BoardPieceTile) getTile(view, 11, 10, 21)).getPoints());
    }

    @Test
    public void superBoardGetsGeneratedWithProperPremiumTiles() {
        HandViewStub handViewStub = new HandViewStub();
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub));

        Board board = new SuperBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 0, 0, 21) instanceof BoardQuadrupleWordTile);
        assertTrue(getTile(view, 0, 20, 21) instanceof BoardQuadrupleWordTile);
        assertTrue(getTile(view, 20, 0, 21) instanceof BoardQuadrupleWordTile);
        assertTrue(getTile(view, 20, 20, 21) instanceof BoardQuadrupleWordTile);
    }

    @Test
    public void standardBoardGetsGeneratedWithProperPremiumTiles() {
        HandViewStub handViewStub = new HandViewStub();
        view = new TestWindow<>(500, 500, new BoardView(15, handViewStub));

        Board board = new StandardBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 1, 5, 15) instanceof BoardTripleLetterTile);
        assertTrue(getTile(view, 5, 1, 15) instanceof BoardTripleLetterTile);
    }

    @Test
    public void juniorBoardGetsGeneratedWithProperPremiumTiles() {
        HandViewStub handViewStub = new HandViewStub();
        view = new TestWindow<>(500, 500, new BoardView(11, handViewStub));

        Board board = new JuniorBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 1, 4, 11) instanceof BoardDoubleLetterTile);
        assertTrue(getTile(view, 4, 1, 11) instanceof BoardDoubleLetterTile);
    }

    @Test
    public void superBoardCenter() {
        HandViewStub handViewStub = new HandViewStub();
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub));

        Board board = new SuperBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 10, 10, 21) instanceof BoardCenterTile);
    }

    @Test
    public void standardBoardCenter() {
        HandViewStub handViewStub = new HandViewStub();
        view = new TestWindow<>(500, 500, new BoardView(15, handViewStub));

        Board board = new StandardBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 7, 7, 15) instanceof BoardCenterTile);
    }

    @Test
    public void juniorBoardCenter() {
        HandViewStub handViewStub = new HandViewStub();
        view = new TestWindow<>(500, 500, new BoardView(11, handViewStub));

        Board board = new JuniorBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 5, 5, 11) instanceof BoardCenterTile);
    }
}
