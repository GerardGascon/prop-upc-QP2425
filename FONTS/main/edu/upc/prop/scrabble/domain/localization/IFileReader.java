package edu.upc.prop.scrabble.domain.localization;

import edu.upc.prop.scrabble.data.localization.Locale;

public interface IFileReader {
    String run(Locale locale);
}
