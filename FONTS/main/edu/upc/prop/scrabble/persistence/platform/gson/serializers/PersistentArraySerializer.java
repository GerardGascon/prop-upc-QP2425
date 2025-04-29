package edu.upc.prop.scrabble.persistence.platform.gson.serializers;

import com.google.gson.*;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;

import java.lang.reflect.Type;
import java.util.stream.IntStream;

class PersistentArraySerializer implements JsonSerializer<PersistentArray> {
    @Override
    public JsonElement serialize(PersistentArray src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        JsonArray arrayJson = new JsonArray();
        IntStream.range(0, src.getLength()).mapToObj(i -> context.serialize(src.get(i))).forEach(arrayJson::add);
        json.add(src.getName(), arrayJson);
        return json;
    }
}

