package edu.upc.prop.scrabble.persistence.platform.gson.deserializers;

import com.google.gson.*;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

import java.lang.reflect.Type;
import java.util.Map.Entry;

class PersistentObjectDeserializer extends Deserializer implements JsonDeserializer<PersistentObject> {
    @Override
    public PersistentObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.entrySet().size() != 1) {
            throw new JsonParseException("Expected exactly one property for PersistentObject");
        }

        Entry<String, JsonElement> entry = jsonObject.entrySet().iterator().next();
        String name = entry.getKey();
        JsonElement valueElement = entry.getValue();

        Object value = parseJsonElement(valueElement, context);
        return new PersistentObject(name, value);
    }
}
