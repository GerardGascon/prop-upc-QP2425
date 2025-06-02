package scrabble.pieces;

import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    public void checkIfThereIsAnErrorOnGeneration1(){
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        WordValidator sut = new WordValidator(dawg);

        adder.run("TRAINT");
        adder.run("TRAMAT");
        adder.run("TRAMATS");

        assertFalse(sut.run("TRAINTS"));
    }
    @Test
    public void checkIfThereIsAnErrorOnGeneration2(){
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        WordValidator sut = new WordValidator(dawg);

        adder.run("PANAXES");
        adder.run("PAPPIES");
        adder.run("PAPPIEST");

        assertFalse(sut.run("PANAXEST"));
    }

    @Test
    public void testCatalanDictionary() throws IOException, URISyntaxException {
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        WordValidator sut = new WordValidator(dawg);

        String content = new String(Files.readAllBytes(
                Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("catalan.txt")).toURI())
        ));
        content.lines().forEach(adder::run);

        assertFalse("TRAINTS should not exist in catalan dictionary", sut.run("TRAINTS"));
        assertFalse("DARIENE should not exist in catalan dictionary", sut.run("DARIENE"));
        assertFalse("URSINAM should not exist in catalan dictionary", sut.run("URSINAM"));
        assertFalse("ERTALDE should not exist in catalan dictionary", sut.run("ERTALDE"));
        assertFalse("CREADAS should not exist in catalan dictionary", sut.run("CREADAS"));
    }

    @Test
    public void testSpanishDictionary() throws IOException, URISyntaxException {
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        WordValidator sut = new WordValidator(dawg);

        String content = new String(Files.readAllBytes(
                Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("castellano.txt")).toURI())
        ));
        content.lines().forEach(adder::run);

        assertFalse("RIFEÑOSE should not exist in spanish dictionary", sut.run("RIFEÑOSE"));
        assertFalse("ALENGUOS should not exist in spanish dictionary", sut.run("ALENGUOS"));
        assertFalse("HEZAR should not exist in spanish dictionary", sut.run("HEZAR"));
        assertFalse("JINGLANDOSE should not exist in spanish dictionary", sut.run("JINGLANDOSE"));
    }

    @Test
    public void testEnglishDictionary() throws IOException, URISyntaxException {
        DAWG dawg = new DAWG();
        WordAdder adder = new WordAdder(dawg);
        WordValidator sut = new WordValidator(dawg);

        String content = new String(Files.readAllBytes(
                Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("english.txt")).toURI())
        ));
        content.lines().forEach(adder::run);

        assertFalse("PANAXEST should not exist in english dictionary", sut.run("PANAXEST"));
        assertFalse("TELDENZ should not exist in english dictionary", sut.run("TELDENZ"));
        assertFalse("TACKEYS should not exist in english dictionary", sut.run("TACKEYS"));
        assertFalse("CRIBSONS should not exist in english dictionary", sut.run("CRIBSONS"));
    }
}