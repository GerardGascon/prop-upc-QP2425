package edu.upc.prop.scrabble.persistence.platform.gson.serializers;

import com.google.gson.*;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

import java.lang.reflect.Type;

class PersistentObjectSerializer implements JsonSerializer<PersistentObject> {
    @Override
    public JsonElement serialize(PersistentObject src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.add(src.getName(), src.getValue() == null ? JsonNull.INSTANCE : context.serialize(src.getValue()));
        return json;
    }
}
