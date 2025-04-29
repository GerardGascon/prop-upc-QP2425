package edu.upc.prop.scrabble.persistence.platform.gson.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;

import java.util.ArrayList;
import java.util.List;

abstract class Deserializer {
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
            List<PersistentObject> elements = new ArrayList<>();
            for (JsonElement arrayElement : element.getAsJsonArray()) {
                elements.add((PersistentObject) parseJsonElement(arrayElement, context));
            }
            return elements;
        } else if (element.isJsonObject()) {
            return context.deserialize(element, PersistentObject.class);
        }

        return null;
    }
}
