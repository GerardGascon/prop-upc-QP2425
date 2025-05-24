package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.properties.Language;

/**
 * Interfície que defineix la lectura de fitxers amb dades relacionades amb el joc,
 * com poden ser diccionaris o recursos segons l’idioma.
 * <p>
 * Aquesta interfície permet implementar diferents maneres de llegir fitxers segons
 * el llenguatge o configuració local.
 * </p>
 *
 * @author Gerard Gascón
 */
public interface IFileReader {
    /**
     * Llegeix dades del fitxer associat a l’idioma especificat.
     *
     * @param locale L’idioma o configuració regional per seleccionar el fitxer correcte.
     * @return El contingut del fitxer com a cadena de text.
     */
    String run(Language locale);
}
