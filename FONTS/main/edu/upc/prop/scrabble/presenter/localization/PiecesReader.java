package edu.upc.prop.scrabble.presenter.localization;

import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.domain.pieces.IFileReader;

/**
 * Classe per llegir fitxers de peces per diferents idiomes.
 * <p>
 * La classe {@code PiecesReader} estén {@code LocaleReader} i implementa la
 * interfície {@code IFileReader}. S'encarrega de llegir els fitxers de peces
 * (per exemple, lletres o fitxes) segons l'idioma proporcionat.
 * Proporciona un mètode per obtenir el contingut del fitxer de peces corresponent
 * a l'idioma seleccionat i retorna el seu contingut en forma de cadena.
 * </p>
 *
 * @author Gerard Gascón
 */
public class PiecesReader extends LocaleReader implements IFileReader {
    /**
     * Llegeix el fitxer de peces corresponent a l'idioma especificat.
     * <p>
     * Aquest mètode comprova l'idioma passat com a paràmetre i retorna el contingut
     * del fitxer de peces associat a aquest idioma.
     * </p>
     *
     * @param locale L'idioma del fitxer de peces que es vol llegir.
     * @return El contingut del fitxer de peces seleccionat, en format cadena.
     * @see Language
     */
    @Override
    public String run(Language locale) {
        return switch (locale) {
            case Catalan -> readFileToString("letrasCAT.txt");
            case Spanish -> readFileToString("letrasCAST.txt");
            case English -> readFileToString("letrasENG.txt");
        };
    }
}
