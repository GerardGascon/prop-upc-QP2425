package edu.upc.prop.scrabble.persistence.runtime.controllers;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveWriter;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISerializer;

/**
 * Classe responsable de desar l'estat actual de la partida.
 * <p>
 * Utilitza un DataCollector per recollir l'estat dels objectes persistibles,
 * un ISerializer per serialitzar aquestes dades,
 * i un ISaveWriter per escriure les dades en un fitxer.
 * </p>
 *
 * @author Gerard Gascón
 */
public class GameSaver {
    /** Objecte encarregat de recollir l'estat dels objectes persistibles */
    private final DataCollector dataCollector;

    /** Objecte encarregat de serialitzar les dades abans de desar-les */
    private final ISerializer serializer;

    /** Objecte encarregat d'escriure les dades serialitzades al fitxer */
    private final ISaveWriter saveWriter;

    /** Nom del fitxer on es guardaran les dades de la partida */
    private final String fileName;

    /**
     * Constructor de la classe GameSaver.
     *
     * @param dataCollector Objecte encarregat de recollir l'estat dels objectes persistibles.
     * @param serializer    Objecte encarregat de serialitzar les dades.
     * @param saveWriter    Objecte encarregat d'escriure les dades en el fitxer.
     * @param fileName      Nom del fitxer on es guardaran les dades.
     */
    public GameSaver(DataCollector dataCollector, ISerializer serializer, ISaveWriter saveWriter, String fileName) {
        this.dataCollector = dataCollector;
        this.serializer = serializer;
        this.saveWriter = saveWriter;
        this.fileName = fileName;
    }

    /**
     * Executa el procés de desament de la partida.
     * <p>
     * Recull l'estat dels objectes, els serialitza i escriu les dades al fitxer especificat.
     * </p>
     */
    public void run() {
        PersistentDictionary saveData = dataCollector.run();
        String data = serializer.serialize(saveData);
        saveWriter.write(data, fileName);
    }
}
