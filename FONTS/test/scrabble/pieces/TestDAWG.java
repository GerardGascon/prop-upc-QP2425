package scrabble.pieces;

import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDAWG {
    @Test
    public void createDAWGpropperly() {
        Piece[] hola = new Piece[]{
                new Piece("h", 1),
                new Piece("o", 1),
                new Piece("l", 1),
                new Piece("a", 1),
        };
        Piece[] empty = new Piece[]{
                new Piece("", 1),
        };
        DAWG dawg = new DAWG();
        WordValidator sut = new WordValidator(dawg);
        assertFalse(sut.run(empty));
        assertFalse(sut.run(hola));
    }

    @Test
    public void addWord(){
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        WordValidator sut = new WordValidator(dawg);

        adder.run("hola");

        Piece[] hola = new Piece[]{
                new Piece("h", 1),
                new Piece("o", 1),
                new Piece("l", 1),
                new Piece("a", 1),
        };

        assertTrue(sut.run(hola));
    }
    @Test
    public void addMultipleWords(){
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        WordValidator sut = new WordValidator(dawg);

        adder.run("llave");
        adder.run("llavero");

        Piece[] llave = new Piece[]{
                new Piece("l", 1),
                new Piece("l", 1),
                new Piece("a", 1),
                new Piece("v", 1),
                new Piece("e", 1),
        };
        Piece[] llaver = new Piece[]{
                new Piece("l", 1),
                new Piece("l", 1),
                new Piece("a", 1),
                new Piece("v", 1),
                new Piece("e", 1),
                new Piece("r", 1),
        };
        Piece[] llavero = new Piece[]{
                new Piece("l", 1),
                new Piece("l", 1),
                new Piece("a", 1),
                new Piece("v", 1),
                new Piece("e", 1),
                new Piece("r", 1),
                new Piece("o", 1),
        };

        assertTrue(sut.run(llave));
        assertTrue(sut.run(llavero));
        assertFalse(sut.run(llaver));
    }
}