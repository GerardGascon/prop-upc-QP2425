package edu.upc.prop.scrabble.persistence.runtime.controllers;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveWriter;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISerializer;

public class GameSaver {
    private final DataCollector dataCollector;
    private final ISerializer serializer;
    private final ISaveWriter saveWriter;
    private final String fileName;

    public GameSaver(DataCollector dataCollector, ISerializer serializer, ISaveWriter saveWriter, String fileName) {
        this.dataCollector = dataCollector;
        this.serializer = serializer;
        this.saveWriter = saveWriter;
        this.fileName = fileName;
    }

    public void run() {
        PersistentDictionary saveData = dataCollector.run();
        String data = serializer.serialize(saveData);
        saveWriter.write(data, fileName);
    }
}
