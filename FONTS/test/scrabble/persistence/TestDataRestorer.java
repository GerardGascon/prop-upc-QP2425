package scrabble.persistence;

import edu.upc.prop.scrabble.persistence.runtime.controllers.DataCollector;
import edu.upc.prop.scrabble.persistence.runtime.controllers.DataRestorer;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import org.junit.Test;
import scrabble.stubs.PersistableObjectStub;

import static org.junit.Assert.assertEquals;

public class TestDataRestorer {
    @Test
    public void collectDataGeneratesDictionaryWithClassName() {
        PersistableObjectStub stub = new PersistableObjectStub();
        DataCollector collector = new DataCollector();
        collector.addPersistableObject(stub);
        PersistentDictionary saveData = collector.run();

        DataRestorer sut = new DataRestorer();
        sut.addPersistableObject(stub);

        sut.run(saveData);

        assertEquals("potato", stub.getDecodedValue());
    }
}
