package scrabble;

import edu.upc.prop.scrabble.presenter.terminal.LanguageSetter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLanguageSetter {
    @Test
    public void test() {

    }
    @Test
    public void increment_select() {
        LanguageSetter languageSetter = new LanguageSetter();

        languageSetter.next();
        languageSetter.load_language();


        assertEquals(5, languageSetter.getWords());
    }
}
