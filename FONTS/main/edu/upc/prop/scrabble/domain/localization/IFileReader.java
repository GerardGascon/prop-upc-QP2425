package edu.upc.prop.scrabble.domain.localization;

import edu.upc.prop.scrabble.data.properties.Language;

/**
 * Interface used to represent classes that convert files into strings
 * @author Gerard Gascón
 */
public interface IFileReader {
    /**
     * Read a file
     * @param locale The language of the file desired
     * @return The contents of that file converted into a string
     * @see Language
     */
    String run(Language locale);
}
