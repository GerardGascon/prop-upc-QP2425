package edu.upc.prop.scrabble.persistence.platform.gson.deserializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IDeserializer;

/**
 * Implementació de l'interfície IDeserializer utilitzant la biblioteca Gson per a la deserialització JSON.
 * <p>
 * Aquesta classe configura un objecte Gson amb deserialitzadors personalitzats per a
 * PersistentObject i PersistentDictionary i proveeix un mètode per convertir cadenes JSON
 * en objectes Java de tipus PersistentObject o subtipus.
 * </p>
 *
 * @author Gerard Gascón
 */
public class GsonDeserializer implements IDeserializer {
    /**
     * Serialitzador de json de google.
     */
    private final Gson gson;

    /**
     * Crea una instància de GsonDeserializer configurant Gson amb els deserialitzadors personalitzats.
     */
    public GsonDeserializer() {
        gson = new GsonBuilder()
                .registerTypeAdapter(PersistentObject.class, new PersistentObjectDeserializer())
                .registerTypeAdapter(PersistentDictionary.class, new PersistentDictionaryDeserializer())
                .create();
    }

    /**
     * Deserialitza una cadena JSON en un objecte de la classe especificada que hereti de PersistentObject.
     *
     * @param <T>   Tipus de l'objecte a retornar, que ha de ser un subtype de PersistentObject.
     * @param data  La cadena JSON que conté la representació de l'objecte.
     * @param clazz La classe de l'objecte al qual es deserialitza.
     * @return L'objecte deserialitzat de tipus T.
     */
    @Override
    public <T extends PersistentObject> T deserialize(String data, Class<T> clazz) {
        return gson.fromJson(data, clazz);
    }
}
