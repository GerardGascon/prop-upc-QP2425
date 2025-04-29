package scrabble.persistence;

import edu.upc.prop.scrabble.persistence.runtime.controllers.DataCollector;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import org.junit.Test;
import scrabble.stubs.PersistableObjectStub;

import static org.junit.Assert.assertEquals;

public class TestDataCollector {
    @Test
    public void collectDataGeneratesDictionaryWithClassName() {
        DataCollector sut = new DataCollector();
        sut.addPersistableObject(new PersistableObjectStub());

        PersistentDictionary result = sut.run();

        assertEquals("potato", ((PersistentDictionary)(result.get("PersistableObjectStub"))).get("stub").parse(String.class));
    }
}
