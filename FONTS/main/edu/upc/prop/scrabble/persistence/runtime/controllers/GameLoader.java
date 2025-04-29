package edu.upc.prop.scrabble.persistence.runtime.controllers;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IDeserializer;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveReader;

public class GameLoader {
    private final DataRestorer dataRestorer;
    private final IDeserializer deserializer;
    private final ISaveReader saveReader;
    private final String fileName;

    public GameLoader(DataRestorer dataRestorer, IDeserializer deserializer, ISaveReader saveReader, String fileName) {
        this.dataRestorer = dataRestorer;
        this.deserializer = deserializer;
        this.saveReader = saveReader;
        this.fileName = fileName;
    }

    public void run() {
        String data = saveReader.read(fileName);
        PersistentDictionary saveData = deserializer.deserialize(data, PersistentDictionary.class);
        dataRestorer.run(saveData);
    }
}
