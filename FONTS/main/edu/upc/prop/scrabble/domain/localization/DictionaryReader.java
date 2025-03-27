package edu.upc.prop.scrabble.domain.localization;

import edu.upc.prop.scrabble.data.localization.Locale;

public class DictionaryReader extends LocaleReader implements IFileReader {
    public String run(Locale locale){
        return switch (locale) {
            case Catalan -> readFileToString("catalan.txt");
            case Spanish -> readFileToString("castellano.txt");
            case English -> readFileToString("english.txt");
        };
    }
}
