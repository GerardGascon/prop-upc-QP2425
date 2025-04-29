package scrabble.stubs;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

public class PersistableObjectStub implements IPersistableObject {
    private String decodedValue;

    public String getDecodedValue() {
        return decodedValue;
    }

    @Override
    public PersistentDictionary encode() {
        PersistentDictionary data = new PersistentDictionary(null);
        data.add(new PersistentObject("stub", "potato"));
        return data;
    }

    @Override
    public void decode(PersistentDictionary data) {
        decodedValue = data.get("stub").parse(String.class);
    }
}
