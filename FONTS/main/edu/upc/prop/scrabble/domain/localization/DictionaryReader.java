package edu.upc.prop.scrabble.domain.localization;

public class DictionaryReader extends LocaleReader {
    public String run(Locale locale){
        return switch (locale) {
            case Catalan -> readFileToString("catalan.txt");
            case Spanish -> readFileToString("castellano.txt");
            case English -> readFileToString("english.txt");
        };
    }
}
