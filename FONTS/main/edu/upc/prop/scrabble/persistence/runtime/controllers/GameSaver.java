package edu.upc.prop.scrabble.persistence.runtime.controllers;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveWriter;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISerializer;

public class GameSaver {
    private final DataCollector dataCollector;
    private final ISerializer serializer;
    private final ISaveWriter saveWriter;

    public GameSaver(DataCollector dataCollector, ISerializer serializer, ISaveWriter saveWriter) {
        this.dataCollector = dataCollector;
        this.serializer = serializer;
        this.saveWriter = saveWriter;
    }

    public void run() {
        PersistentDictionary saveData = dataCollector.run();
        String data = serializer.serialize(saveData);
        saveWriter.write(data);
    }
}
