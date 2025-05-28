package scrabble.persistence;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPersistenceBaseDataTypes {
    @Test
    public void savedIntegerCanBeRead() {
        PersistentObject sut = new PersistentObject("name", 1);

        int result = sut.parse(Integer.class);

        assertEquals(1, result);
    }

    @Test
    public void savedStringCanBeRead() {
        PersistentObject sut = new PersistentObject("name", "potato");

        String result = sut.parse(String.class);

        assertEquals("potato", result);
    }

    @Test
    public void savedBooleanCanBeRead() {
        PersistentObject sut = new PersistentObject("name", true);

        boolean result = sut.parse(Boolean.class);

        assertTrue(result);
    }

    @Test
    public void savedValueThrowsExceptionIfValueIsWrongType() {
        PersistentObject sut = new PersistentObject("name", true);

        assertThrows(ClassCastException.class, () -> sut.parse(String.class));
    }

    @Test
    public void tryParseArrayThrowsException() {
        PersistentArray sut = new PersistentArray("name");

        assertThrows(RuntimeException.class, () -> sut.parse(PersistentArray.class));
    }

    @Test
    public void addToArrayUpdatesLength() {
        PersistentObject obj = new PersistentObject("name", "potato");
        PersistentArray array = new PersistentArray("name");

        array.add(obj);

        assertEquals(1, array.getLength());
    }

    @Test
    public void getValueFromArrayReturnsObject() {
        PersistentObject obj = new PersistentObject("name", "potato");
        PersistentArray array = new PersistentArray("name");

        array.add(obj);

        assertEquals("potato", array.get(0, String.class));
    }

    @Test
    public void tryParseDictionaryThrowsException() {
        PersistentDictionary sut = new PersistentDictionary("name");

        assertThrows(RuntimeException.class, () -> sut.parse(PersistentDictionary.class));
    }

    @Test
    public void getValueFromDictionaryReturnsObject() {
        PersistentObject obj = new PersistentObject("name", "potato");
        PersistentDictionary array = new PersistentDictionary("name");

        array.add(obj);

        assertEquals("potato", array.get("name").parse(String.class));
    }
}
