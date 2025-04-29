package edu.upc.prop.scrabble.persistence.platform.gson.streamers;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

abstract class SaveDataStreamer {
    protected File getAbsolutePath(String fileName) {
        try {
            File programRootPath = new File(SaveWriter.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            Path path = programRootPath.toPath()
                    .resolve("edu")
                    .resolve("upc")
                    .resolve("prop")
                    .resolve("scrabble")
                    .resolve("save");

            return new File(path.toFile(), fileName);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
