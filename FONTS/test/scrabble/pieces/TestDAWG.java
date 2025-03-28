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
    @Test
    public void notDupeNodes(){
        DAWG dawg = new DAWG();
        dawg.Initialize();
        assertEquals(1, dawg.getuniqueNodes());
        dawg.addWord("boaaaaa");
        assertEquals(5,dawg.getuniqueNodes());
        dawg.addWord("bola");
        assertEquals(7, dawg.getuniqueNodes());
        dawg.addWord("bola");
        assertEquals(7, dawg.getuniqueNodes());
        dawg.addWord("bolo");
        assertEquals(8, dawg.getuniqueNodes());
        dawg.addWord("bolo");
        assertEquals(8, dawg.getuniqueNodes());
        dawg.addWord("bolo");
        assertEquals(8, dawg.getuniqueNodes());
        dawg.addWord("botella");
        assertEquals(10, dawg.getuniqueNodes());
       // dawg.addWord("bola");*/
        assertEquals(false, dawg.validWord("bol"));
        assertEquals(true, dawg.validWord("bolo"));
        assertEquals(true, dawg.validWord("bola"));
        assertEquals(false, dawg.validWord("botell"));
        assertEquals(false, dawg.validWord("botello"));
        assertEquals(false, dawg.validWord("botellaa"));
        assertEquals(true, dawg.validWord("botella"));
        assertEquals(true, dawg.validWord("boaaaaa"));
        assertEquals(false, dawg.validWord("boaaa"));
        assertEquals(false, dawg.validWord("baaaaaaaaa"));

    }
}
