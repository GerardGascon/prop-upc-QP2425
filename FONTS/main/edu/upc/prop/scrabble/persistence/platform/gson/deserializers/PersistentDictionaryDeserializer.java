package edu.upc.prop.scrabble.persistence.platform.gson.deserializers;

import com.google.gson.*;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Deserialitzador personalitzat per a PersistentDictionary utilitzant Gson.
 * <p>
 * Aquesta classe implementa JsonDeserializer per deserialitzar objectes JSON que representen
 * un PersistentDictionary, convertint-los en una instància de PersistentDictionary amb
 * el nom i el mapa intern d'objectes PersistentObject.
 * </p>
 *
 * @author Gerard Gascón
 */
class PersistentDictionaryDeserializer extends Deserializer implements JsonDeserializer<PersistentDictionary> {
    /**
     * Deserialitza un element JSON en un PersistentDictionary.
     *
     * @param json      L'element JSON que conté la representació del diccionari.
     * @param typeOfT   El tipus de l'objecte a deserialitzar.
     * @param context   El context de deserialització de Gson.
     * @return Un PersistentDictionary amb el nom i els elements deserialitzats.
     */
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
