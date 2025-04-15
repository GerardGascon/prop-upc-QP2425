package edu.upc.prop.scrabble.domain.localization;

import edu.upc.prop.scrabble.data.properties.Language;

/**
 * Class used to read piece files
 * @author Gerard Gascón
 */
public class PiecesReader extends LocaleReader implements IFileReader {
    /**
     * Read a piece file
     * @param locale The language of the piece desired
     * @return The contents of that piece converted into a string
     * @see Language
     */
    public String run(Language locale){
        return switch (locale) {
            case Catalan -> readFileToString("letrasCAT.txt");
            case Spanish -> readFileToString("letrasCAST.txt");
            case English -> readFileToString("letrasENG.txt");
        };
    }
}
