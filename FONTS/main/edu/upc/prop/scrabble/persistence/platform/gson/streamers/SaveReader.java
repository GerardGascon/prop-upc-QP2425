package edu.upc.prop.scrabble.persistence.platform.gson.streamers;

import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class SaveReader extends SaveDataStreamer implements ISaveReader {
    @Override
    public String read(String fileName) {
        File path = getAbsolutePath(fileName);
        if (!path.exists())
            return null;

        try {
            return Files.readString(path.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Error reading file: " + path + " - " + e.getMessage());
            return null;
        }
    }
}
