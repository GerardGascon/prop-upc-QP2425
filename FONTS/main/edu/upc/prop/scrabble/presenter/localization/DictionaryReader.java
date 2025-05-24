package edu.upc.prop.scrabble.presenter.localization;

import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.domain.pieces.IFileReader;

/**
 * Classe que s'encarrega de llegir fitxers de diccionaris per diferents idiomes.
 * <p>
 * La classe {@code DictionaryReader} és responsable de llegir els fitxers de diccionaris
 * segons l'idioma (locale) seleccionat. Suporta la lectura de diccionaris en català, castellà i anglès.
 * </p>
 *
 * @author Gerard Gascón
 */
public class DictionaryReader extends LocaleReader implements IFileReader {

    /**
     * Llegeix el fitxer de diccionari segons l'idioma especificat.
     * <p>
     * Aquest mètode comprova l'idioma donat i retorna el contingut del fitxer de diccionari
     * corresponent com una cadena de text. Els idiomes suportats són català, castellà i anglès.
     * </p>
     *
     * @param locale L'idioma del diccionari que es vol llegir.
     * @return Una cadena amb el contingut del diccionari en l'idioma seleccionat.
     * @see Language
     */
    @Override
    public String run(Language locale) {
        return switch (locale) {
            case Catalan -> readFileToString("catalan.txt");
            case Spanish -> readFileToString("castellano.txt");
            case English -> readFileToString("english.txt");
        };
    }
}
