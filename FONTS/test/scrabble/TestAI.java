package scrabble;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.crosschecks.CatalanCrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.EnglishCrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.AnchorUpdater;
import edu.upc.prop.scrabble.domain.ai.AI;
import edu.upc.prop.scrabble.domain.ai.CatalanAI;
import edu.upc.prop.scrabble.domain.ai.EnglishAI;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.pieces.CatalanPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Test;

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
    public void catalanAIEmptyBoard(){
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
}
