package scrabble.ai;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.crosschecks.CatalanCrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.EnglishCrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.SpanishCrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.ai.AnchorUpdater;
import edu.upc.prop.scrabble.domain.ai.AI;
import edu.upc.prop.scrabble.domain.ai.CatalanAI;
import edu.upc.prop.scrabble.domain.ai.EnglishAI;
import edu.upc.prop.scrabble.domain.ai.SpanishAI;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.ai.CrossCheckUpdater;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.pieces.CatalanPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.SpanishPiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Before;
import org.junit.Test;
import scrabble.stubs.BoardViewStub;

import static org.junit.Assert.assertEquals;

public class TestAI {
    private DAWG dawg;
    private Board board;
    private WordAdder adder;
    private Player bot;
    private PointCalculator pointCalculator;
    private Anchors anchors;
    private WordPlacer wordPlacer;
    Piece[] valCat = {
            new Piece("A", 1),
            new Piece("B", 3),
            new Piece("C", 3),
            new Piece("Ç", 10),
            new Piece("D", 2),
            new Piece("E", 1),
            new Piece("F", 4),
            new Piece("G", 2),
            new Piece("H", 8),
            new Piece("I", 1),
            new Piece("J", 8),
            new Piece("L", 1),
            new Piece("L·L", 10),
            new Piece("M", 3),
            new Piece("N", 1),
            new Piece("NY", 10),
            new Piece("O", 1),
            new Piece("P", 3),
            new Piece("Q", 5),
            new Piece("R", 1),
            new Piece("S", 1),
            new Piece("T", 1),
            new Piece("U", 1),
            new Piece("V", 4),
            new Piece("X", 8),
            new Piece("Y", 4),
            new Piece("Z", 10)
    };
    @Before
    public void setUp() {
        dawg = new DAWG();
        bot = new Player("ai", true);

        adder = new WordAdder(dawg);
        board = new StandardBoard();
        anchors = new Anchors();
        WordGetter wordGetter = new WordGetter(board);
        pointCalculator = new PointCalculator(board, wordGetter);

        BoardViewStub boardViewStub = new BoardViewStub();
        wordPlacer = new WordPlacer(bot, board, boardViewStub, pointCalculator);
    }

    @Test
    public void englishAIEmptyBoard() {
        adder.run("HELLO");
        adder.run("SKIBIDI");

        bot.addPiece(new Piece("S", 1));
        bot.addPiece(new Piece("K", 1));
        bot.addPiece(new Piece("I", 1));
        bot.addPiece(new Piece("B", 1));
        bot.addPiece(new Piece("I", 1));
        bot.addPiece(new Piece("D", 1));
        bot.addPiece(new Piece("I", 1));

        PiecesConverter converter = new PiecesConverter();
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new EnglishCrossChecks(board.getSize());
        AI ai = new EnglishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("SKIBIDI", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void catalanAIEmptyBoardNY() {
        adder.run("SKIBIDI");
        adder.run("TANYA");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("NY", 1));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new CatalanPiecesConverter();
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new CatalanCrossChecks(board.getSize());
        AI ai = new CatalanAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TANYA", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void catalanAIEmptyBoardLL() {
        adder.run("SKIBIDI");
        adder.run("TAL·LA");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("L·L", 1));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new CatalanPiecesConverter();
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new CatalanCrossChecks(board.getSize());
        AI ai = new CatalanAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TAL·LA", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void catalanAIEmptyBoardNYendOfWord() {
        adder.run("SKIBIDI");
        adder.run("TANY");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("NY", 1));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new CatalanPiecesConverter();
        CrossChecks crossChecks = new CatalanCrossChecks(board.getSize());
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        AI ai = new CatalanAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TANY", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void catalanAIEmptyBoardLGeminadaEndOfWord() {
        adder.run("SKIBIDI");
        adder.run("TAL·L");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("L·L", 1));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new CatalanPiecesConverter();
        CrossChecks crossChecks = new CatalanCrossChecks(board.getSize());
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        AI ai = new CatalanAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TAL·L", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardRR() {
        adder.run("SKIBIDI");
        adder.run("TARRA");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("RR", 1));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new SpanishPiecesConverter();
        CrossChecks crossChecks = new SpanishCrossChecks(board.getSize());
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        AI ai = new SpanishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TARRA", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardRRendOfWord() {
        adder.run("SKIBIDI");
        adder.run("TARR");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("RR", 1));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new SpanishPiecesConverter();
        CrossChecks crossChecks = new SpanishCrossChecks(board.getSize());
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        AI ai = new SpanishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TARR", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardLL() {
        adder.run("SKIBIDI");
        adder.run("TALLA");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("LL", 1));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new SpanishPiecesConverter();
        CrossChecks crossChecks = new SpanishCrossChecks(board.getSize());
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        AI ai = new SpanishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TALLA", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardLLendOfWord() {
        adder.run("SKIBIDI");
        adder.run("TALL");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("LL", 1));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new SpanishPiecesConverter();
        CrossChecks crossChecks = new SpanishCrossChecks(board.getSize());
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        AI ai = new SpanishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TALL", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardCH() {
        adder.run("SKIBIDI");
        adder.run("TACHA");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("CH", 1));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new SpanishPiecesConverter();
        CrossChecks crossChecks = new SpanishCrossChecks(board.getSize());
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        AI ai = new SpanishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TACHA", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardCHendOfWord() {
        adder.run("SKIBIDI");
        adder.run("TACH");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("CH", 1));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new SpanishPiecesConverter();
        CrossChecks crossChecks = new SpanishCrossChecks(board.getSize());
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        AI ai = new SpanishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TACH", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void englishAIHorizontal() {
        adder.run("TOILET");
        adder.run("SKIBIDI");

        bot.addPiece(new Piece("S", 1));
        bot.addPiece(new Piece("K", 1));
        bot.addPiece(new Piece("I", 1));
        bot.addPiece(new Piece("B", 1));
        bot.addPiece(new Piece("I", 1));
        bot.addPiece(new Piece("D", 1));
        bot.addPiece(new Piece("I", 1));

        PiecesConverter converter = new PiecesConverter();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new EnglishCrossChecks(board.getSize());
        AI ai = new EnglishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);
        CrossCheckUpdater updater = new CrossCheckUpdater(converter, crossChecks, board, dawg);

        Piece[] pieces = new Piece[]{
                new Piece("T", 1),
                new Piece("O", 1),
                new Piece("I", 1),
                new Piece("L", 1),
                new Piece("E", 1),
                new Piece("T", 1)
        };
        Movement previousMove = new Movement("TOILET", 7, 3, Direction.Vertical);
        anchorUpdater.run(previousMove);
        updater.run(previousMove);

        wordPlacer.run(pieces, 7, 3, Direction.Vertical);

        Movement expectedMove = new Movement("SKIBIDI", 5, 5, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void catalanAIHorizontalNYRETRUNSMAXMOVEPOINTS() {
        adder.run("ANY");
        adder.run("PAZNYP");

        bot.addPiece(new Piece("P", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("Z", 1));
        bot.addPiece(new Piece("P", 1));

        PiecesConverter converter = new CatalanPiecesConverter(valCat);
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new CatalanCrossChecks(board.getSize());
        AI ai = new CatalanAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);
        CrossCheckUpdater updater = new CrossCheckUpdater(converter, crossChecks, board, dawg);

        Piece[] pieces = new Piece[]{
                new Piece("NY", 1),
        };
        Movement previousMove = new Movement("NY", 7, 7, Direction.Vertical);
        anchorUpdater.run(previousMove);
        updater.run(previousMove);

        wordPlacer.run(pieces, 7, 7, Direction.Vertical);

        Movement expectedMove = new Movement("PAZNYP", 4, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void catalanAIHorizontalLGeminada() {
        adder.run("PAZL·LP");

        bot.addPiece(new Piece("P", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("Z", 1));
        bot.addPiece(new Piece("P", 1));

        PiecesConverter converter = new CatalanPiecesConverter();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new CatalanCrossChecks(board.getSize());
        AI ai = new CatalanAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);
        CrossCheckUpdater updater = new CrossCheckUpdater(converter, crossChecks, board, dawg);

        Piece[] pieces = new Piece[]{
                new Piece("L·L", 1),
        };
        Movement previousMove = new Movement("L·L", 7, 7, Direction.Vertical);
        anchorUpdater.run(previousMove);
        updater.run(previousMove);

        wordPlacer.run(pieces, 7, 7, Direction.Vertical);

        Movement expectedMove = new Movement("PAZL·LP", 4, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIHorizontalRR() {
        adder.run("PAZRRP");

        bot.addPiece(new Piece("P", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("Z", 1));
        bot.addPiece(new Piece("P", 1));

        PiecesConverter converter = new SpanishPiecesConverter();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new SpanishCrossChecks(board.getSize());
        AI ai = new SpanishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);
        CrossCheckUpdater updater = new CrossCheckUpdater(converter, crossChecks, board, dawg);

        Piece[] pieces = new Piece[]{
                new Piece("RR", 1),
        };
        Movement previousMove = new Movement("RR", 7, 7, Direction.Vertical);
        anchorUpdater.run(previousMove);
        updater.run(previousMove);

        wordPlacer.run(pieces, 7, 7, Direction.Vertical);

        Movement expectedMove = new Movement("PAZRRP", 4, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIHorizontalLL() {
        adder.run("PAZLLP");

        bot.addPiece(new Piece("P", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("Z", 1));
        bot.addPiece(new Piece("P", 1));

        PiecesConverter converter = new SpanishPiecesConverter();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new SpanishCrossChecks(board.getSize());
        AI ai = new SpanishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);
        CrossCheckUpdater updater = new CrossCheckUpdater(converter, crossChecks, board, dawg);

        Piece[] pieces = new Piece[]{
                new Piece("LL", 1),
        };
        Movement previousMove = new Movement("LL", 7, 7, Direction.Vertical);
        anchorUpdater.run(previousMove);
        updater.run(previousMove);

        wordPlacer.run(pieces, 7, 7, Direction.Vertical);

        Movement expectedMove = new Movement("PAZLLP", 4, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIHorizontalCH() {
        adder.run("PAZCHP");

        bot.addPiece(new Piece("P", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("Z", 1));
        bot.addPiece(new Piece("P", 1));

        PiecesConverter converter = new SpanishPiecesConverter();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new SpanishCrossChecks(board.getSize());
        AI ai = new SpanishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);
        CrossCheckUpdater updater = new CrossCheckUpdater(converter, crossChecks, board, dawg);

        Piece[] pieces = new Piece[]{
                new Piece("CH", 1),
        };
        Movement previousMove = new Movement("CH", 7, 7, Direction.Vertical);
        anchorUpdater.run(previousMove);
        updater.run(previousMove);

        wordPlacer.run(pieces, 7, 7, Direction.Vertical);

        Movement expectedMove = new Movement("PAZCHP", 4, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void englishAIHorizontalwithBlank() {
        adder.run("TOILET");
        adder.run("SKIBIDI");

        bot.addPiece(new Piece("#", 0, true));
        bot.addPiece(new Piece("K", 1));
        bot.addPiece(new Piece("I", 1));
        bot.addPiece(new Piece("B", 1));
        bot.addPiece(new Piece("I", 1));
        bot.addPiece(new Piece("D", 1));
        bot.addPiece(new Piece("I", 1));

        PiecesConverter converter = new PiecesConverter();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new EnglishCrossChecks(board.getSize());
        AI ai = new EnglishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);
        CrossCheckUpdater updater = new CrossCheckUpdater(converter, crossChecks, board, dawg);

        Piece[] pieces = new Piece[]{
                new Piece("T", 1, true),
                new Piece("O", 1),
                new Piece("I", 1),
                new Piece("L", 1),
                new Piece("E", 1),
                new Piece("T", 1)
        };
        Movement previousMove = new Movement("TOILET", 7, 3, Direction.Vertical);
        anchorUpdater.run(previousMove);
        updater.run(previousMove);

        wordPlacer.run(pieces, 7, 3, Direction.Vertical);

        Movement expectedMove = new Movement("sKIBIDI", 5, 5, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void catalanAIEmptyBoardNYwithBlank() {
        adder.run("SKIBIDI");
        adder.run("TAYA");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("#", 0, true));
        bot.addPiece(new Piece("Y", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new CatalanPiecesConverter();
        CrossChecks crossChecks = new CatalanCrossChecks(board.getSize());
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        AI ai = new CatalanAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TAYA", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardRRwithBlank() {
        adder.run("SKIBIDI");
        adder.run("TARRA");

        bot.addPiece(new Piece("T", 1));
        bot.addPiece(new Piece("A", 1));
        bot.addPiece(new Piece("#", 0, true));
        bot.addPiece(new Piece("N", 1));
        bot.addPiece(new Piece("A", 1));

        PiecesConverter converter = new SpanishPiecesConverter();
        CrossChecks crossChecks = new SpanishCrossChecks(board.getSize());
        AnchorUpdater _ = new AnchorUpdater(anchors, board, converter);
        AI ai = new SpanishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);

        Movement expectedMove = new Movement("TArrA", 7, 7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void englishAIEmptyBoardV() {
        adder.run("HELLO");
        adder.run("SKIBIDI");
        adder.run("KIBIDI");
        adder.run("SKIBI");

        bot.addPiece(new Piece("S", 1));
        bot.addPiece(new Piece("K", 1));
        bot.addPiece(new Piece("I", 1));
        bot.addPiece(new Piece("B", 1));

        PiecesConverter converter = new PiecesConverter();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors, board, converter);
        CrossChecks crossChecks = new EnglishCrossChecks(board.getSize());
        AI ai = new EnglishAI(converter, pointCalculator, dawg, board, bot, anchors, crossChecks);
        CrossCheckUpdater updater = new CrossCheckUpdater(converter, crossChecks, board, dawg);

        Piece[] pieces = new Piece[]{
                new Piece("I", 1),
                new Piece("D", 1),
                new Piece("I", 1)
        };
        Movement previousMove = new Movement("IDI", 7, 7, Direction.Vertical);
        anchorUpdater.run(previousMove);
        updater.run(previousMove);

        wordPlacer.run(pieces, 7, 7, Direction.Vertical);

        Movement expectedMove = new Movement("SKIBIDI", 7, 3, Direction.Vertical);
        assertEquals(expectedMove, ai.run());
    }

}
