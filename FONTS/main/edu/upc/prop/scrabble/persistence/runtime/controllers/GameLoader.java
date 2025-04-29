package edu.upc.prop.scrabble.persistence.runtime.controllers;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IDeserializer;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveReader;

public class GameLoader {
    private final DataRestorer dataRestorer;
    private final IDeserializer deserializer;
    private final ISaveReader saveReader;

    public GameLoader(DataRestorer dataRestorer, IDeserializer deserializer, ISaveReader saveReader) {
        this.dataRestorer = dataRestorer;
        this.deserializer = deserializer;
        this.saveReader = saveReader;
    }

    public void run() {
        String data = saveReader.read();
        PersistentDictionary saveData = deserializer.deserialize(data, PersistentDictionary.class);
        dataRestorer.run(saveData);
    }
}
