package scrabble.pieces;

import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDAWG {
    @Test
    public void createDAWGpropperly() {
        DAWG dawg = new DAWG();
        WordValidator sut = new WordValidator(dawg);
        assertFalse(sut.run(""));
        assertFalse(sut.run("HOLA"));
    }

    @Test
    public void addWord(){
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        WordValidator sut = new WordValidator(dawg);

        adder.run("HOLA");

        assertTrue(sut.run("HOLA"));
    }
    @Test
    public void addMultipleWords(){
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        WordValidator sut = new WordValidator(dawg);

        adder.run("LLAVE");
        adder.run("LLAVERO");

        assertTrue(sut.run("LLAVE"));
        assertTrue(sut.run("LLAVERO"));
        assertFalse(sut.run("LLAVER"));
    }

    @Test
    public void addRareWords(){
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        WordValidator sut = new WordValidator(dawg);

        adder.run("CL·LAVE");
        adder.run("CL·LAVERO");

        assertTrue(sut.run("CL·LAVE"));
        assertTrue(sut.run("CL·LAVERO"));
        assertFalse(sut.run("CL·LAVER"));

        adder.run("DCHAVE");
        adder.run("DCHAVERO");

        assertTrue(sut.run("DCHAVE"));
        assertTrue(sut.run("DCHAVERO"));
        assertFalse(sut.run("DCHAVER"));

        adder.run("ÑAVE");
        adder.run("ÑAVERO");

        assertTrue(sut.run("ÑAVE"));
        assertTrue(sut.run("ÑAVERO"));
        assertFalse(sut.run("ÑAVER"));

        adder.run("XÇAVE");
        adder.run("XÇAVERO");

        assertTrue(sut.run("XÇAVE"));
        assertTrue(sut.run("XÇAVERO"));
        assertFalse(sut.run("XÇAVER"));
    }

}