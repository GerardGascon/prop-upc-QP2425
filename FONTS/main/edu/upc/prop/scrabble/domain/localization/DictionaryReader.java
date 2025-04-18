package edu.upc.prop.scrabble.domain.localization;

import edu.upc.prop.scrabble.data.properties.Language;

/**
 * Class used to read dictionary files for different languages.
 * <p>
 * The `DictionaryReader` class is responsible for reading dictionary files based on the
 * selected language (locale). It supports reading dictionaries in Catalan, Spanish, and English.
 * </p>
 *
 * @author Gerard Gascón
 */
public class DictionaryReader extends LocaleReader implements IFileReader {
    /**
     * Reads a dictionary file based on the specified language.
     * <p>
     * The method checks the provided `locale` (language) and returns the corresponding dictionary
     * file's contents as a string. The supported languages are Catalan, Spanish, and English.
     * </p>
     *
     * @param locale The language of the dictionary desired.
     * @return A string containing the contents of the dictionary in the selected language.
     * @see Language
     */
    public String run(Language locale) {
        return switch (locale) {
            case Catalan -> readFileToString("catalan.txt");
            case Spanish -> readFileToString("castellano.txt");
            case English -> readFileToString("english.txt");
        };
    }
}
