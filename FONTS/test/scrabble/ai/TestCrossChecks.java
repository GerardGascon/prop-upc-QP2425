package scrabble.ai;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.EnglishCrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.domain.ai.CrossCheckUpdater;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.assertEquals;

public class TestCrossChecks {
    @Test
    public void createCrossChecks() {
        Board board = new StandardBoard();
        CrossChecks Ecrch = new EnglishCrossChecks(board.getSize());
        BitSet allavaliable = new BitSet(26);
        assertEquals(allavaliable, Ecrch.getCrossCheck(0, 0));
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

        CrossChecks Ecrch = new EnglishCrossChecks(board.getSize());
        PiecesConverter converter = new PiecesConverter();
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
        assertEquals(allavaliable, Ecrch.getCrossCheck(4, 0));
        crchU.run(moveHe);
        assertEquals(expected, Ecrch.getCrossCheck(4, 0));
        //VERTICAL END
        assertEquals(allavaliable, Ecrch.getCrossCheck(0, 4));
        crchU.run(moveVe);
        assertEquals(expected, Ecrch.getCrossCheck(0, 4));

        expected.set(18);//S
        expected.clear(2);//C
        //HORIZONTAL BEGGNINING
        assertEquals(allavaliable, Ecrch.getCrossCheck(3, 4));
        crchU.run(moveHb);
        assertEquals(expected, Ecrch.getCrossCheck(3, 4));
        //VERTICAL BEGGINING
        assertEquals(allavaliable, Ecrch.getCrossCheck(4, 3));
        crchU.run(moveVb);
        assertEquals(expected, Ecrch.getCrossCheck(4, 3));
    }

    @Test
    public void rotateCrossChecks() {
        //WOIP
        Board board = new StandardBoard();
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        adder.run("ARO");
        adder.run("CARO");

        CrossChecks Ecrch = new EnglishCrossChecks(board.getSize());
        PiecesConverter converter = new PiecesConverter();
        CrossCheckUpdater crchU = new CrossCheckUpdater(converter, Ecrch,board,dawg);

        BitSet allavaliable = new BitSet(26);
        BitSet expected = new BitSet(26);
        expected.set(0, 26, true);
        expected.clear(2);//C

        Movement moveHb = new Movement("ARO", 4,4, Direction.Horizontal);
        Movement moveVb = new Movement("ARO", 4,4, Direction.Vertical);

        //HORIZONTAL BEGGNINING
        assertEquals(allavaliable, Ecrch.getCrossCheck(3, 4));
        crchU.run(moveHb);
        assertEquals(expected, Ecrch.getCrossCheck(3, 4));
        //VERTICAL BEGGINING
        assertEquals(allavaliable, Ecrch.getCrossCheck(4, 3));
        crchU.run(moveVb);
        assertEquals(expected, Ecrch.getCrossCheck(4, 3));

        CrossChecks rotated = Ecrch.rotate();

        assertEquals(expected, rotated.getCrossCheck(10, 3));
        assertEquals(expected, rotated.getCrossCheck(11, 4));
    }
}

