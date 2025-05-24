package edu.upc.prop.scrabble.persistence.runtime.controllers;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IDeserializer;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveReader;

/**
 * Classe responsable de carregar una partida desada.
 * <p>
 * Utilitza un DataRestorer per restaurar l'estat dels objectes,
 * un IDeserializer per deserialitzar les dades guardades,
 * i un ISaveReader per llegir les dades del fitxer de guardat.
 * </p>
 *
 * @author Gerard Gascón
 */
public class GameLoader {
    /** Objecte encarregat de restaurar l'estat dels objectes persistibles */
    private final DataRestorer dataRestorer;

    /** Objecte encarregat de deserialitzar les dades llegides */
    private final IDeserializer deserializer;

    /** Objecte encarregat de llegir les dades desades des del fitxer */
    private final ISaveReader saveReader;

    /** Nom del fitxer on es troben les dades guardades */
    private final String fileName;

    /**
     * Constructor de la classe GameLoader.
     *
     * @param dataRestorer Objecte encarregat de restaurar l'estat dels objectes persistibles.
     * @param deserializer Objecte encarregat de deserialitzar les dades llegides.
     * @param saveReader   Objecte encarregat de llegir les dades desades des del fitxer.
     * @param fileName     Nom del fitxer on es troben les dades guardades.
     */
    public GameLoader(DataRestorer dataRestorer, IDeserializer deserializer, ISaveReader saveReader, String fileName) {
        this.dataRestorer = dataRestorer;
        this.deserializer = deserializer;
        this.saveReader = saveReader;
        this.fileName = fileName;
    }

    /**
     * Executa el procés de càrrega de la partida.
     * <p>
     * Llegeix les dades desades, les deserialitza a un PersistentDictionary,
     * i restaura l'estat dels objectes utilitzant el DataRestorer.
     * </p>
     */
    public void run() {
        String data = saveReader.read(fileName);
        PersistentDictionary saveData = deserializer.deserialize(data, PersistentDictionary.class);
        dataRestorer.run(saveData);
    }
}
