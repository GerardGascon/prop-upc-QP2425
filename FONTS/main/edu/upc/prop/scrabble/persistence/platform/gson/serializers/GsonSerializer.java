package edu.upc.prop.scrabble.persistence.platform.gson.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISerializer;

/**
 * Serialitzador que utilitza Gson per convertir PersistentObjects en JSON.
 * <p>
 * Aquesta classe configura un Gson personalitzat amb serialitzadors específics
 * per a PersistentObject, PersistentDictionary i PersistentArray per gestionar
 * la serialització d'aquests tipus de dades.
 * </p>
 *
 * @author Gerard Gascón
 */
public class GsonSerializer implements ISerializer {
    private final Gson gson;

    /**
     * Constructor que crea un objecte Gson configurat amb els serialitzadors personalitzats.
     */
    public GsonSerializer() {
        gson = new GsonBuilder()
                .registerTypeAdapter(PersistentObject.class, new PersistentObjectSerializer())
                .registerTypeAdapter(PersistentDictionary.class, new PersistentDictionarySerializer())
                .registerTypeAdapter(PersistentArray.class, new PersistentArraySerializer())
                .create();
    }

    /**
     * Serialitza un PersistentObject a una cadena JSON.
     *
     * @param object L'objecte persistent a serialitzar.
     * @return La representació JSON de l'objecte.
     */
    @Override
    public String serialize(PersistentObject object) {
        return gson.toJson(object);
    }
}
