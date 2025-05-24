package edu.upc.prop.scrabble.persistence.platform.gson.serializers;

import com.google.gson.*;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

import java.lang.reflect.Type;

/**
 * Serialitzador per a PersistentObject que utilitza Gson.
 * <p>
 * Aquesta classe converteix un PersistentObject en un objecte JSON amb una sola propietat,
 * on la clau és el nom de l'objecte i el valor és el seu valor serialitzat.
 * Si el valor és null, s'afegeix un JsonNull.
 * </p>
 *
 * @author Gerard Gascón
 */
class PersistentObjectSerializer implements JsonSerializer<PersistentObject> {
    /**
     * Serialitza un PersistentObject a un JsonElement.
     *
     * @param src L'objecte PersistentObject a serialitzar.
     * @param typeOfSrc El tipus de l'objecte que s'està serialitzant.
     * @param context El context de serialització Gson per serialitzar el valor intern.
     * @return Un JsonElement que representa el PersistentObject.
     */
    @Override
    public JsonElement serialize(PersistentObject src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.add(src.getName(), src.getValue() == null ? JsonNull.INSTANCE : context.serialize(src.getValue()));
        return json;
    }
}
