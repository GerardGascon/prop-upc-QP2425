package scrabble.pieces;

import edu.upc.prop.scrabble.data.DAWG;
import edu.upc.prop.scrabble.data.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDAWG {
    @Test
    public void createDAWGpropperly() {
        DAWG dawg = new DAWG();
        dawg.Initialize();
        assertEquals(false, dawg.validWord(""));
        assertEquals(false, dawg.validWord("hola"));
    }

    @Test
    public void addWord(){
        DAWG dawg = new DAWG();
        dawg.Initialize();
        dawg.addWord("hola");
        assertEquals(true, dawg.validWord("hola"));
    }
    @Test
    public void addMultipleWords(){
        DAWG dawg = new DAWG();
        dawg.Initialize();
        dawg.addWord("llave");
        dawg.addWord("llavero");
        assertEquals(true, dawg.validWord("llave"));
        assertEquals(true, dawg.validWord("llavero"));
        assertEquals(false, dawg.validWord("llaver"));
    }
}
