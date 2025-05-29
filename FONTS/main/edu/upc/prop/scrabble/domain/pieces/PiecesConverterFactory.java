package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.utils.Pair;

import java.util.Arrays;

/**
 * Fàbrica encarregada de crear un convertidor de fitxes segons l'idioma seleccionat.
 * <p>
 * Aquesta classe utilitza un lector de fitxers per obtenir el contingut de les fitxes,
 * genera les fitxes amb {@code PieceGenerator} i crea el convertidor adequat en funció de l'idioma.
 * </p>
 *
 * @author Gerard Gascón
 */
public class PiecesConverterFactory {
    /**
     * Lector de fitxers de peces.
     */
    private final IFileReader piecesReader;
    /**
     * Generador de peces en funció d'un fitxer de peces.
     */
    private final PieceGenerator pieceGenerator = new PieceGenerator();

    /**
     * Constructor que rep un lector de fitxers de fitxes.
     *
     * @param piecesReader Lector encarregat de llegir el fitxer de fitxes segons l'idioma.
     */
    public PiecesConverterFactory(IFileReader piecesReader) {
        this.piecesReader = piecesReader;
    }

    /**
     * Genera un convertidor de fitxes per l'idioma especificat.
     * <p>
     * Llegeix el fitxer de fitxes corresponent, genera les fitxes i retorna l'objecte
     * convertidor específic per a l'idioma (Català, Castellà o Anglès).
     * </p>
     *
     * @param language L'idioma pel qual es vol generar el convertidor de fitxes.
     * @return Un objecte `PiecesConverter` per l'idioma indicat.
     */
    public PiecesConverter run(Language language) {
        String piecesFile = piecesReader.run(language);
        Piece[] pieces = Arrays.stream(pieceGenerator.run(piecesFile)).map(Pair::first).toArray(Piece[]::new);

        return switch (language) {
            case Language.Catalan -> new CatalanPiecesConverter(pieces);
            case Language.Spanish -> new SpanishPiecesConverter(pieces);
            case Language.English -> new EnglishPiecesConverter(pieces);
        };
    }
}
