package edu.upc.prop.scrabble.persistence.platform.gson.streamers;

import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SaveWriter extends SaveDataStreamer implements ISaveWriter {
    @Override
    public void write(String data) {
        File path = getAbsolutePath();

        try {
            Files.writeString(path.toPath(), data);
        } catch (IOException e) {
            System.err.println("Error writing file: " + path + " - " + e.getMessage());
        }
    }
}
