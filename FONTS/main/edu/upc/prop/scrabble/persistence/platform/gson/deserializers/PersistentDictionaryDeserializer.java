package edu.upc.prop.scrabble.persistence.platform.gson.deserializers;

import com.google.gson.*;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

class PersistentDictionaryDeserializer extends Deserializer implements JsonDeserializer<PersistentDictionary> {
    @Override
    public PersistentDictionary deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.entrySet().size() != 1) {
            throw new JsonParseException("Expected exactly one entry for PersistentDictionary.");
        }

        Map.Entry<String, JsonElement> entry = jsonObject.entrySet().iterator().next();
        String name = entry.getKey();
        JsonObject dictJson = entry.getValue().getAsJsonObject();

        Map<String, PersistentObject> dictionary = new HashMap<>();
        for (Map.Entry<String, JsonElement> item : dictJson.entrySet()) {
            String key = item.getKey();
            JsonElement valueElement = item.getValue();
            PersistentObject value = (PersistentObject)parseJsonElement(valueElement, context);
            dictionary.put(key, value);
        }

        return new PersistentDictionary(name, dictionary);
    }
}
