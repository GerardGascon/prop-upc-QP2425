package edu.upc.prop.scrabble.persistence.platform.gson.deserializers;

import com.google.gson.*;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe abstracta que facilita la deserialització d'elements JSON en objectes de tipus PersistentObject.
 * <p>
 * Aquesta classe conté un mètode que processa recursivament diferents tipus d'elements JSON
 * (primitives, arrays, objectes) i els converteix en tipus Java equivalents o en instàncies de PersistentObject.
 * </p>
 *
 * @author Gerard Gascón
 */
abstract class Deserializer {

    /**
     * Processa un element JSON i el converteix en un objecte Java equivalent.
     * <p>
     * Aquest mètode gestiona els següents casos:
     * <ul>
     *   <li>Si l'element és null, retorna null.</li>
     *   <li>Si és una primitive, retorna el valor corresponent (boolean, string, int, long o double).</li>
     *   <li>Si és un array JSON, crea una llista d'objectes PersistentObject a partir dels elements de l'array.</li>
     *   <li>Si és un objecte JSON, el deserialitza com un PersistentObject.</li>
     * </ul>
     *
     * @param element L'element JSON a processar.
     * @param context El context de deserialització de Gson.
     * @return Un objecte Java equivalent a l'element JSON, o null si no és possible.
     */
    protected Object parseJsonElement(JsonElement element, JsonDeserializationContext context) {
        if (element.isJsonNull()) return null;
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) return primitive.getAsBoolean();
            if (primitive.isString()) return primitive.getAsString();
            if (primitive.isNumber()) {
                double num = primitive.getAsDouble();
                if (num == Math.rint(num)) {
                    if (num >= Integer.MIN_VALUE && num <= Integer.MAX_VALUE) {
                        return (int) num;
                    } else {
                        return (long) num;
                    }
                } else {
                    return num;
                }
            }
        } else if (element.isJsonArray()) {
            //TODO: Parse into a JsonArray or something
//            List<PersistentObject> elements = new ArrayList<>();
//            for (JsonElement arrayElement : element.getAsJsonArray()) {
//                elements.add((PersistentObject) parseJsonElement(arrayElement, context));
//            }
            return element.getAsJsonArray();
        } else if (element.isJsonObject()) {
            for (String key : element.getAsJsonObject().keySet()) {
                JsonElement e = element.getAsJsonObject().get(key);

                if (e.isJsonArray()) {
                    JsonArray array = e.getAsJsonArray();
                    List<Object> elements = new ArrayList<>();
                    for (JsonElement arrayElement : array) {
                        Object obj = context.deserialize(arrayElement, Object.class);
                        elements.add(obj);
                    }
                    return new PersistentArray(key, elements);
                } else {
                    return context.deserialize(element, PersistentObject.class);
                }
            }
        }

        return null;
    }
}
