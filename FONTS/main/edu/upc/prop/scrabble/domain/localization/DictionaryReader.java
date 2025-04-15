package edu.upc.prop.scrabble.domain.localization;

import edu.upc.prop.scrabble.data.properties.Language;

/**
 * Class used to read dictionary files
 * @author Gerard Gascón
 */
public class DictionaryReader extends LocaleReader implements IFileReader {
    /**
     * Read a dictionary file
     * @param locale The language of the dictionary desired
     * @return The contents of that dictionary converted into a string
     * @see Language
     */
    public String run(Language locale){
        return switch (locale) {
            case Catalan -> readFileToString("catalan.txt");
            case Spanish -> readFileToString("castellano.txt");
            case English -> readFileToString("english.txt");
        };
    }
}
