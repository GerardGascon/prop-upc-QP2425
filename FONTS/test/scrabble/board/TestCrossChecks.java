package scrabble.board;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.EnglishCrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.SpanishCrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.crosschecks.CrossCheckUpdater;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.pieces.CatalanPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandVerifier;
import edu.upc.prop.scrabble.domain.pieces.SpanishPiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Test;
import scrabble.stubs.BoardViewStub;

import java.util.BitSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCrossChecks {
    @Test
    public void createCrossChecks() {
        Board board = new StandardBoard();
        DAWG dawg = new DAWG();
        CrossChecks Ecrch = new EnglishCrossChecks(board,dawg);
        BitSet allavaliable = new BitSet(26);
        //expected.set(0); es crea amb tot 0's
        assertEquals(allavaliable, Ecrch.getCrossCheckHor(0, 0));
    }
    @Test
    public void updateCrossChecks() {
        Board board = new StandardBoard();
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        adder.run("ARO");
        adder.run("CARO");
        adder.run("COSA");
        adder.run("COSAS"); //ordenadas

        CrossChecks Ecrch = new EnglishCrossChecks(board,dawg);
        PiecesConverter converter = new PiecesConverter();
        //PiecesConverter converter2 = new CatalanPiecesConverter();
        //PiecesConverter converter3 = new SpanishPiecesConverter();
        CrossCheckUpdater crchU = new CrossCheckUpdater(converter, Ecrch,board,dawg);

        BitSet allavaliable = new BitSet(26);
        BitSet expected = new BitSet(26);
        expected.set(0, 26, true);
        expected.clear(18); //pos s

        Movement moveHe = new Movement("COSA", 0,0, Direction.Horizontal);
        Movement moveVe = new Movement("COSA", 0,0, Direction.Vertical);
        Movement moveHb = new Movement("ARO", 4,4, Direction.Horizontal);
        Movement moveVb = new Movement("ARO", 4,4, Direction.Vertical);
        //HORIZONTAL END
        assertEquals(allavaliable, Ecrch.getCrossCheckHor(4, 0));
        crchU.run(moveHe);
        assertEquals(expected, Ecrch.getCrossCheckHor(4, 0));
        //VERTICAL END
        assertEquals(allavaliable, Ecrch.getCrossCheckVer(0, 4));
        crchU.run(moveVe);
        assertEquals(expected, Ecrch.getCrossCheckVer(0, 4));

        expected.set(18);//S
        expected.clear(2);//C
        //HORIZONTAL END
        assertEquals(allavaliable, Ecrch.getCrossCheckHor(3, 4));
        crchU.run(moveHb);
        assertEquals(expected, Ecrch.getCrossCheckHor(3, 4));
        //VERTICAL BEGGINING
        assertEquals(allavaliable, Ecrch.getCrossCheckVer(4, 3));
        crchU.run(moveVb);
        assertEquals(expected, Ecrch.getCrossCheckVer(4, 3));
    }
}

