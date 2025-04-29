package edu.upc.prop.scrabble.persistence.platform.gson.serializers;

import com.google.gson.*;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;

import java.lang.reflect.Type;

class PersistentDictionarySerializer implements JsonSerializer<PersistentDictionary> {
    @Override
    public JsonElement serialize(PersistentDictionary src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        JsonObject dictJson = new JsonObject();
        src.getDictionary().forEach((key, value) -> dictJson.add(key, context.serialize(value)));

        json.add(src.getName(), dictJson);
        return json;
    }
}
