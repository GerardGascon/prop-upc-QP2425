package edu.upc.prop.scrabble.domain.localization;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Abstract class used to locate and read localization files.
 * <p>
 * The `LocaleReader` class provides functionality to locate and read localization files
 * (e.g., dictionary files). The `readFileToString` method reads the contents of a specified
 * file from the local filesystem, using a specific encoding. This class is typically used as
 * a base class for other classes that need to load localized resources.
 * </p>
 *
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
     * Reads the contents of a file and returns it as a string.
     *
     * @param filePath The name of the file to read.
     * @return A string containing the contents of the file, or null if an error occurs.
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
