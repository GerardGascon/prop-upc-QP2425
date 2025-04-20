package edu.upc.prop.scrabble.presenter.localization;

import edu.upc.prop.scrabble.data.properties.Language;

/**
 * Class used to read piece files for different languages.
 * <p>
 * The `PiecesReader` class extends the `LocaleReader` class and implements the `IFileReader` interface.
 * It is responsible for reading piece files (e.g., letters or tiles) based on the provided language.
 * The class offers a method to fetch the corresponding piece file based on the selected language and returns
 * the file contents as a string.
 * </p>
 *
 * @author Gerard Gascón
 */
public class PiecesReader extends LocaleReader {
    /**
     * Reads the piece file corresponding to the specified language.
     * <p>
     * The method will return the contents of the piece file for the selected language. It will check the
     * provided language (`locale`) and return the appropriate piece file's contents.
     * </p>
     *
     * @param locale The language of the piece file to read.
     * @return The contents of the selected piece file as a string.
     * @see Language
     */
    public String run(Language locale) {
        return switch (locale) {
            case Catalan -> readFileToString("letrasCAT.txt");
            case Spanish -> readFileToString("letrasCAST.txt");
            case English -> readFileToString("letrasENG.txt");
        };
    }
}
