package edu.upc.prop.scrabble.domain.localization;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class used to locate the localization files
 * @author Gerard Gascón
 */
abstract class LocaleReader {
    private File getAbsolutePath(String fileName) {
        try {
            File programRootPath = new File(LocaleReader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            Path path = programRootPath.toPath()
                    .resolve("edu")
                    .resolve("upc")
                    .resolve("prop")
                    .resolve("scrabble")
                    .resolve("locales");

            return new File(path.toFile(), fileName);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read the contents of a file
     * @param filePath Absolute path of the file to read
     * @return The contents of the file
     */
    protected String readFileToString(String filePath) {
        File path = getAbsolutePath(filePath);
        try {
            return Files.readString(path.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Error reading file: " + path + " - " + e.getMessage());
            return null;
        }
    }
}
