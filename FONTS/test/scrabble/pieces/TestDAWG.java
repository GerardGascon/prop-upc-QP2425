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

}