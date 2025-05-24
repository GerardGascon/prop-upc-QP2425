package edu.upc.prop.scrabble.persistence.platform.gson.serializers;

import com.google.gson.*;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;

import java.lang.reflect.Type;
import java.util.stream.IntStream;

/**
 * Serialitzador per a PersistentArray que utilitza Gson.
 * <p>
 * Aquesta classe converteix un PersistentArray en un objecte JSON on la clau és el
 * nom de l'array i el valor és un array JSON amb els elements serialitzats.
 * </p>
 *
 * @author Gerard Gascón
 */
class PersistentArraySerializer implements JsonSerializer<PersistentArray> {
    /**
     * Serialitza un PersistentArray a un JsonElement.
     *
     * @param src L'objecte PersistentArray a serialitzar.
     * @param typeOfSrc El tipus de l'objecte que s'està serialitzant.
     * @param context El context de serialització Gson per serialitzar elements interns.
     * @return Un JsonElement que representa el PersistentArray.
     */
    @Override
    public JsonElement serialize(PersistentArray src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        JsonArray arrayJson = new JsonArray();
        IntStream.range(0, src.getLength()).mapToObj(i -> context.serialize(src.get(i))).forEach(arrayJson::add);
        json.add(src.getName(), arrayJson);
        return json;
    }
}
