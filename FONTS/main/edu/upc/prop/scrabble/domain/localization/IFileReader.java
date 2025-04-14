package edu.upc.prop.scrabble.domain.localization;

import edu.upc.prop.scrabble.data.properties.Language;

public interface IFileReader {
    String run(Language locale);
}
