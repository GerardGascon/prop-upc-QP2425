package edu.upc.prop.scrabble.domain.localization;

public class PiecesReader extends LocaleReader implements IFileReader {
    public String run(Locale locale){
        return switch (locale) {
            case Catalan -> readFileToString("letrasCAT.txt");
            case Spanish -> readFileToString("letrasCAST.txt");
            case English -> readFileToString("letrasENG.txt");
        };
    }
}
