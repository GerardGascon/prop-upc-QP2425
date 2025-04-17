package scrabble;

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
import edu.upc.prop.scrabble.domain.AnchorUpdater;
import edu.upc.prop.scrabble.domain.ai.AI;
import edu.upc.prop.scrabble.domain.ai.CatalanAI;
import edu.upc.prop.scrabble.domain.ai.EnglishAI;
import edu.upc.prop.scrabble.domain.ai.SpanishAI;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.pieces.CatalanPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.SpanishPiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Test;
import scrabble.stubs.BoardViewStub;

import static org.junit.Assert.assertEquals;

public class TestAI {
    @Test
    public void englishAIEmptyBoard(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("HELLO");
        adder.run("SKIBIDI");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("S",1));
        bot.addPiece(new Piece("K",1));
        bot.addPiece(new Piece("I",1));
        bot.addPiece(new Piece("B",1));
        bot.addPiece(new Piece("I",1));
        bot.addPiece(new Piece("D",1));
        bot.addPiece(new Piece("I",1));
        PiecesConverter converter = new PiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new EnglishCrossChecks(board,dawg);
        AI ai = new EnglishAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("SKIBIDI",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void catalanAIEmptyBoardNY(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("SKIBIDI");
        adder.run("TANYA");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("T",1));
        bot.addPiece(new Piece("A",1));
        bot.addPiece(new Piece("NY",1));
        bot.addPiece(new Piece("N",1));
        bot.addPiece(new Piece("A",1));
        PiecesConverter converter = new CatalanPiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new CatalanCrossChecks(board,dawg);
        AI ai = new CatalanAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("TANYA",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }
    @Test
    public void catalanAIEmptyBoardLL(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("SKIBIDI");
        adder.run("TAL·LA");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("T",1));
        bot.addPiece(new Piece("A",1));
        bot.addPiece(new Piece("L·L",1));
        bot.addPiece(new Piece("N",1));
        bot.addPiece(new Piece("A",1));
        PiecesConverter converter = new CatalanPiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new CatalanCrossChecks(board,dawg);
        AI ai = new CatalanAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("TAL·LA",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }
    @Test
    public void catalanAIEmptyBoardNYendOfWord(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("SKIBIDI");
        adder.run("TANY");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("T",1));
        bot.addPiece(new Piece("A",1));
        bot.addPiece(new Piece("NY",1));
        bot.addPiece(new Piece("N",1));
        bot.addPiece(new Piece("A",1));
        PiecesConverter converter = new CatalanPiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new CatalanCrossChecks(board,dawg);
        AI ai = new CatalanAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("TANY",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void catalanAIEmptyBoardLLendOfWord(){//no se puede poner · en el nomre del test
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("SKIBIDI");
        adder.run("TAL·L");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("T",1));
        bot.addPiece(new Piece("A",1));
        bot.addPiece(new Piece("L·L",1));
        bot.addPiece(new Piece("N",1));
        bot.addPiece(new Piece("A",1));
        PiecesConverter converter = new CatalanPiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new CatalanCrossChecks(board,dawg);
        AI ai = new CatalanAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("TAL·L",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardRR(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("SKIBIDI");
        adder.run("TARRA");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("T",1));
        bot.addPiece(new Piece("A",1));
        bot.addPiece(new Piece("RR",1));
        bot.addPiece(new Piece("N",1));
        bot.addPiece(new Piece("A",1));
        PiecesConverter converter = new SpanishPiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new SpanishCrossChecks(board,dawg);
        AI ai = new SpanishAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("TARRA",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardRRendOfWord(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("SKIBIDI");
        adder.run("TARR");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("T",1));
        bot.addPiece(new Piece("A",1));
        bot.addPiece(new Piece("RR",1));
        bot.addPiece(new Piece("N",1));
        bot.addPiece(new Piece("A",1));
        PiecesConverter converter = new SpanishPiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new SpanishCrossChecks(board,dawg);
        AI ai = new SpanishAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("TARR",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardLL(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("SKIBIDI");
        adder.run("TALLA");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("T",1));
        bot.addPiece(new Piece("A",1));
        bot.addPiece(new Piece("LL",1));
        bot.addPiece(new Piece("N",1));
        bot.addPiece(new Piece("A",1));
        PiecesConverter converter = new SpanishPiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new SpanishCrossChecks(board,dawg);
        AI ai = new SpanishAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("TALLA",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardLLendOfWord(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("SKIBIDI");
        adder.run("TALL");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("T",1));
        bot.addPiece(new Piece("A",1));
        bot.addPiece(new Piece("LL",1));
        bot.addPiece(new Piece("N",1));
        bot.addPiece(new Piece("A",1));
        PiecesConverter converter = new SpanishPiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new SpanishCrossChecks(board,dawg);
        AI ai = new SpanishAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("TALL",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardCH(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("SKIBIDI");
        adder.run("TACHA");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("T",1));
        bot.addPiece(new Piece("A",1));
        bot.addPiece(new Piece("CH",1));
        bot.addPiece(new Piece("N",1));
        bot.addPiece(new Piece("A",1));
        PiecesConverter converter = new SpanishPiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new SpanishCrossChecks(board,dawg);
        AI ai = new SpanishAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("TACHA",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void spanishAIEmptyBoardCHendOfWord(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("SKIBIDI");
        adder.run("TACH");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("T",1));
        bot.addPiece(new Piece("A",1));
        bot.addPiece(new Piece("CH",1));
        bot.addPiece(new Piece("N",1));
        bot.addPiece(new Piece("A",1));
        PiecesConverter converter = new SpanishPiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new SpanishCrossChecks(board,dawg);
        AI ai = new SpanishAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        Movement expectedMove = new Movement("TACH",7,7, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }

    @Test
    public void englishAIHorizontal(){
        DAWG dawg= new DAWG();
        WordAdder adder= new WordAdder(dawg);
        adder.run("TOILET");
        adder.run("SKIBIDI");
        Board board = new StandardBoard();
        Player bot = new Player("ai",true);
        bot.addPiece(new Piece("S",1));
        bot.addPiece(new Piece("K",1));
        bot.addPiece(new Piece("I",1));
        bot.addPiece(new Piece("B",1));
        bot.addPiece(new Piece("I",1));
        bot.addPiece(new Piece("D",1));
        bot.addPiece(new Piece("I",1));
        PiecesConverter converter = new PiecesConverter();
        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors,board,converter);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        CrossChecks crossChecks = new EnglishCrossChecks(board,dawg);
        AI ai = new EnglishAI(converter, pointCalculator, dawg,board,bot,anchors,crossChecks);
        BoardViewStub mock = new BoardViewStub();
        WordPlacer wordPlacer = new WordPlacer(bot, board,mock,pointCalculator);
        Piece[] pieces = new Piece[]{
                new Piece("T", 1),
                new Piece("O", 1),
                new Piece("I", 1),
                new Piece("L", 1),
                new Piece("E", 1),
                new Piece("T", 1)
        };
        Movement previousmove = new Movement("TOILET",7,3, Direction.Vertical);
        anchorUpdater.run(previousmove);
        wordPlacer.run(pieces, 7, 3, Direction.Vertical);
        Movement expectedMove = new Movement("SKIBIDI",5,5, Direction.Horizontal);
        assertEquals(expectedMove, ai.run());
    }
}
