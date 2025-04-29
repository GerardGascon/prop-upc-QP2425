package edu.upc.prop.scrabble.persistence.platform.gson.deserializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IDeserializer;

public class GsonDeserializer implements IDeserializer {
    private final Gson gson;

    public GsonDeserializer() {
        gson = new GsonBuilder()
                .registerTypeAdapter(PersistentObject.class, new PersistentObjectDeserializer())
                .registerTypeAdapter(PersistentDictionary.class, new PersistentDictionaryDeserializer())
                .create();
    }

    @Override
    public <T extends PersistentObject> T deserialize(String data, Class<T> clazz) {
        return gson.fromJson(data, clazz);
    }
}
