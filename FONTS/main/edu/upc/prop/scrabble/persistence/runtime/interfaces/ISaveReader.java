package edu.upc.prop.scrabble.persistence.runtime.interfaces;

public interface ISaveReader {
    /**
     * Reads the save file and converts it into a string
     *
     * @return null if file does not exist, String otherwise
     */
    String read();
}
