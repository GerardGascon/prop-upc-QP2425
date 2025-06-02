package edu.upc.prop.scrabble.persistence.platform.gson.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Serialitzador per a PersistentDictionary que utilitza Gson.
 * <p>
 * Aquesta classe converteix un PersistentDictionary en un objecte JSON on la clau és el
 * nom del diccionari i el valor és un objecte JSON amb les entrades del diccionari serialitzades.
 * </p>
 *
 * @author Gerard Gascón
 */
class PersistentDictionarySerializer implements JsonSerializer<PersistentDictionary> {
    /**
     * Crea una instància d'un serialitzador de diccionaris
     */
    public PersistentDictionarySerializer() {

    }

    /**
     * Serialitza un PersistentDictionary a un JsonElement.
     *
     * @param src L'objecte PersistentDictionary a serialitzar.
     * @param typeOfSrc El tipus de l'objecte que s'està serialitzant.
     * @param context El context de serialització Gson per serialitzar elements interns.
     * @return Un JsonElement que representa el PersistentDictionary.
     */
    @Override
    public JsonElement serialize(PersistentDictionary src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        JsonObject dictJson = new JsonObject();
        src.getDictionary().forEach((key, value) -> dictJson.add(key, context.serialize(value)));

        json.add(Objects.requireNonNullElse(src.getName(), ""), dictJson);
        return json;
    }
}
