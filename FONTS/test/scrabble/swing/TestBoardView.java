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
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.*;
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
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));
        BoardTile c = getTile(view, 10, 10, 21);

        queueClick(c);
        doClick(c);

        assertTrue("Hand view getPiece() was not requested", handViewStub.getGetPieceRequestCalled());
        finish();
    }

    @Test
    public void cellTileChangeReplacesTile() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));

        view.getPanel().changeTile(new BoardPieceTile("A", 1, 0, 0, handViewStub, null, null), 0, 0);

        assertTrue(getTile(view, 0, 0, 21) instanceof BoardPieceTile);
        finish();
    }

    @Test
    public void cellTileIsEmptyByDefault() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));

        assertTrue(getTile(view, 0, 0, 21) instanceof BoardEmptyTile);
        finish();
    }

    @Test
    public void boardGetsUpdatedWhenPieceIsPlaced() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));
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
        finish();
    }

    @Test
    public void superBoardGetsGeneratedWithProperPremiumTiles() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));

        Board board = new SuperBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 0, 0, 21) instanceof BoardQuadrupleWordTile);
        assertTrue(getTile(view, 0, 20, 21) instanceof BoardQuadrupleWordTile);
        assertTrue(getTile(view, 20, 0, 21) instanceof BoardQuadrupleWordTile);
        assertTrue(getTile(view, 20, 20, 21) instanceof BoardQuadrupleWordTile);
        finish();
    }

    @Test
    public void standardBoardGetsGeneratedWithProperPremiumTiles() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(15, handViewStub, null));

        Board board = new StandardBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 1, 5, 15) instanceof BoardTripleLetterTile);
        assertTrue(getTile(view, 5, 1, 15) instanceof BoardTripleLetterTile);
        finish();
    }

    @Test
    public void juniorBoardGetsGeneratedWithProperPremiumTiles() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(11, handViewStub, null));

        Board board = new JuniorBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 1, 4, 11) instanceof BoardDoubleLetterTile);
        assertTrue(getTile(view, 4, 1, 11) instanceof BoardDoubleLetterTile);
        finish();
    }

    @Test
    public void superBoardCenter() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));

        Board board = new SuperBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 10, 10, 21) instanceof BoardCenterTile);
        finish();
    }

    @Test
    public void standardBoardCenter() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(15, handViewStub, null));

        Board board = new StandardBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 7, 7, 15) instanceof BoardCenterTile);
        finish();
    }

    @Test
    public void juniorBoardCenter() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(11, handViewStub, null));

        Board board = new JuniorBoard();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, view.getPanel());

        filler.run();

        assertTrue(getTile(view, 5, 5, 11) instanceof BoardCenterTile);
        finish();
    }

    @Test
    public void placeTemporalPieceGetsPlaced() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));

        view.getPanel().placeTemporalPiece("A", 3, 10, 10);
        view.getPanel().placeTemporalPiece("B", 2, 11, 10);

        assertTrue(getTile(view, 10, 10, 21) instanceof BoardTemporalPieceTile);
        assertEquals("A", ((BoardTemporalPieceTile) getTile(view, 10, 10, 21)).getLetter());
        assertEquals(3, ((BoardTemporalPieceTile) getTile(view, 10, 10, 21)).getPoints());

        assertTrue(getTile(view, 11, 10, 21) instanceof BoardTemporalPieceTile);
        assertEquals("B", ((BoardTemporalPieceTile) getTile(view, 11, 10, 21)).getLetter());
        assertEquals(2, ((BoardTemporalPieceTile) getTile(view, 11, 10, 21)).getPoints());
        finish();
    }

    @Test
    public void getTemporalPieceTilesHorizontal() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));

        view.getPanel().placeTemporalPiece("B", 2, 11, 10);
        view.getPanel().placeTemporalPiece("A", 3, 10, 10);

        String temporalWord = view.getPanel().getTemporalWord().word();

        assertEquals("AB", temporalWord);
        finish();
    }

    @Test
    public void getTemporalPieceTilesVertical() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));

        view.getPanel().placeTemporalPiece("B", 2, 10, 11);
        view.getPanel().placeTemporalPiece("A", 3, 10, 10);

        String temporalWord = view.getPanel().getTemporalWord().word();

        assertEquals("AB", temporalWord);
        finish();
    }

    @Test
    public void placeTemporalPiecesWithPieceInBetween() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));
        Board board = new SuperBoard();
        Player player = new Player("test", false);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer placer = new WordPlacer(player, board, view.getPanel(), pointCalculator);

        placer.run(new Piece[]{new Piece("A", 3)}, 10, 10, Direction.Horizontal);

        view.getPanel().placeTemporalPiece("P", 2, 9, 10);
        view.getPanel().placeTemporalPiece("T", 3, 11, 10);

        assertTrue(getTile(view, 9, 10, 21) instanceof BoardTemporalPieceTile);
        assertEquals("P", ((BoardTemporalPieceTile) getTile(view, 9, 10, 21)).getLetter());
        assertEquals(2, ((BoardTemporalPieceTile) getTile(view, 9, 10, 21)).getPoints());

        assertTrue(getTile(view, 11, 10, 21) instanceof BoardTemporalPieceTile);
        assertEquals("T", ((BoardTemporalPieceTile) getTile(view, 11, 10, 21)).getLetter());
        assertEquals(3, ((BoardTemporalPieceTile) getTile(view, 11, 10, 21)).getPoints());

        finish();
    }

    @Test
    public void placeTemporalPiecesWithPieceInBetweenVertical() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));
        Board board = new SuperBoard();
        Player player = new Player("test", false);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer placer = new WordPlacer(player, board, view.getPanel(), pointCalculator);

        placer.run(new Piece[]{new Piece("A", 3)}, 10, 10, Direction.Horizontal);

        view.getPanel().placeTemporalPiece("P", 2, 10, 9);
        view.getPanel().placeTemporalPiece("T", 3, 10, 11);

        assertTrue(getTile(view, 10, 9, 21) instanceof BoardTemporalPieceTile);
        assertEquals("P", ((BoardTemporalPieceTile) getTile(view, 10, 9, 21)).getLetter());
        assertEquals(2, ((BoardTemporalPieceTile) getTile(view, 10, 9, 21)).getPoints());

        assertTrue(getTile(view, 10, 11, 21) instanceof BoardTemporalPieceTile);
        assertEquals("T", ((BoardTemporalPieceTile) getTile(view, 10, 11, 21)).getLetter());
        assertEquals(3, ((BoardTemporalPieceTile) getTile(view, 10, 11, 21)).getPoints());

        finish();
    }

    @Test
    public void getTemporalPiecesWordWithPieceInBetweenVertical() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));
        Board board = new SuperBoard();
        Player player = new Player("test", false);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer placer = new WordPlacer(player, board, view.getPanel(), pointCalculator);

        placer.run(new Piece[]{new Piece("A", 3)}, 10, 10, Direction.Horizontal);

        view.getPanel().placeTemporalPiece("P", 2, 9, 10);
        view.getPanel().placeTemporalPiece("T", 3, 11, 10);

        String temporalWord = view.getPanel().getTemporalWord().word();

        assertEquals("PAT", temporalWord);

        finish();
    }

    @Test
    public void getTemporalPiecesWordWithPieceInBetweenHorizontal() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));
        Board board = new SuperBoard();
        Player player = new Player("test", false);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer placer = new WordPlacer(player, board, view.getPanel(), pointCalculator);

        placer.run(new Piece[]{new Piece("A", 3)}, 10, 10, Direction.Horizontal);

        view.getPanel().placeTemporalPiece("P", 2, 10, 9);
        view.getPanel().placeTemporalPiece("T", 3, 10, 11);

        String temporalWord = view.getPanel().getTemporalWord().word();

        assertEquals("PAT", temporalWord);

        finish();
    }

    @Test
    public void getTemporalPiecesTakesSpacesIntoAccount() {
        HandViewStub handViewStub = new HandViewStub("A", 1);
        view = new TestWindow<>(500, 500, new BoardView(21, handViewStub, null));
        Board board = new SuperBoard();
        Player player = new Player("test", false);
        PointCalculator pointCalculator = new PointCalculator(board);
        WordPlacer placer = new WordPlacer(player, board, view.getPanel(), pointCalculator);

        placer.run(new Piece[]{new Piece("A", 3)}, 10, 10, Direction.Horizontal);
        placer.run(new Piece[]{new Piece("A", 3)}, 10, 7, Direction.Horizontal);

        view.getPanel().placeTemporalPiece("P", 2, 10, 9);
        view.getPanel().placeTemporalPiece("T", 3, 10, 11);

        String temporalWord = view.getPanel().getTemporalWord().word();

        assertEquals("PAT", temporalWord);

        finish();
    }
}
