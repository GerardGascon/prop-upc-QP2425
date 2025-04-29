package edu.upc.prop.scrabble.persistence.platform.gson.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISerializer;

public class GsonSerializer implements ISerializer {
    private final Gson gson;

    public GsonSerializer() {
        gson = new GsonBuilder()
                .registerTypeAdapter(PersistentObject.class, new PersistentObjectSerializer())
                .registerTypeAdapter(PersistentDictionary.class, new PersistentDictionarySerializer())
                .registerTypeAdapter(PersistentArray.class, new PersistentArraySerializer())
                .create();
    }

    @Override
    public String serialize(PersistentObject object) {
        return gson.toJson(object);
    }
}
