package edu.upc.prop.scrabble.persistence.platform.gson.deserializers;

import com.google.gson.*;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Deserialitzador personalitzat per a PersistentObject utilitzant Gson.
 * <p>
 * Aquesta classe implementa JsonDeserializer per deserialitzar un element JSON
 * que representa un PersistentObject, convertint-lo en una instància de PersistentObject
 * amb un nom i un valor associat.
 * </p>
 *
 * @author Gerard Gascón
 */
class PersistentObjectDeserializer extends Deserializer implements JsonDeserializer<PersistentObject> {
    /**
     * Deserialitza un element JSON en un PersistentObject.
     *
     * @param json      L'element JSON que conté la representació de l'objecte persistent.
     * @param typeOfT   El tipus de l'objecte a deserialitzar.
     * @param context   El context de deserialització de Gson.
     * @return Un PersistentObject amb el nom i el valor deserialitzat.
     * @throws JsonParseException Si el JSON no conté exactament una propietat o si hi ha errors en la deserialització.
     */
    @Override
    public PersistentObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.entrySet().size() != 1) {
            Map<String, PersistentObject> dictionary = new HashMap<>();
            for (Map.Entry<String, JsonElement> item : jsonObject.entrySet()) {
                String key = item.getKey();
                JsonElement valueElement = item.getValue();
                PersistentObject value = (PersistentObject)parseJsonElement(valueElement, context);
                dictionary.put(key, value);
            }
            return new PersistentDictionary("", dictionary);
            //            throw new JsonParseException("S'esperava exactament una propietat per a PersistentObject");
        }

        Entry<String, JsonElement> entry = jsonObject.entrySet().iterator().next();
        String name = entry.getKey();
        JsonElement valueElement = entry.getValue();

        Object value = parseJsonElement(valueElement, context);
        return new PersistentObject(name, value);
    }
}
