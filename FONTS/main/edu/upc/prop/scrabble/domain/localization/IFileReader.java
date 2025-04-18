package edu.upc.prop.scrabble.domain.localization;

import edu.upc.prop.scrabble.data.properties.Language;

/**
 * Interface used to represent classes that convert files into strings.
 * <p>
 * The IFileReader interface defines a method for reading a file based on the language
 * and converting its contents into a string. This interface can be implemented by
 * classes that handle reading various types of files (e.g., dictionaries).
 * </p>
 *
 * @author Gerard Gascón
 */
public interface IFileReader {
    /**
     * Reads a file based on the desired language and converts its contents into a string.
     *
     * @param locale The language of the file desired.
     * @return The contents of the file, converted into a string.
     * @see Language
     */
    String run(Language locale);
}
