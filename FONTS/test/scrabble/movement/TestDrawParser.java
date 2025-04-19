package scrabble.movement;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.presenter.terminal.movements.DrawParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDrawParser {
    @Test
    public void testParseSingleLetter() {
        String input = "A";

        Piece[] pieces = DrawParser.parse(input);

        assertEquals("A", pieces[0].letter());
    }

    @Test
    public void testParseTwoLetters() {
        String input = "A, B";

        Piece[] pieces = DrawParser.parse(input);

        assertEquals("A", pieces[0].letter());
        assertEquals("B", pieces[1].letter());
    }

    @Test
    public void testParseSpecialDigraph() {
        String input = "A, CH";

        Piece[] pieces = DrawParser.parse(input);

        assertEquals("A", pieces[0].letter());
        assertEquals("CH", pieces[1].letter());
    }

    @Test
    public void testParseBlankTile() {
        String input = "#, CH";

        Piece[] pieces = DrawParser.parse(input);

        assertEquals("#", pieces[0].letter());
        assertTrue(pieces[0].isBlank());
        assertEquals("CH", pieces[1].letter());
    }
}
