package edu.upc.prop.scrabble.data.board;

/**
 * Representa un tauler de Scrabble de 21x21 utilitzat en modes de joc ampliats o personalitzats.
 * Aquest tauler inclou caselles premium addicionals com les de paraula i lletra quadruplicada.
 *
 * @author Gerard Gascón
 */
public class SuperBoard extends Board {
    /**
     * Construeix un SuperBoard amb un disseny de 21x21 i
     * estableix totes les posicions de caselles prèmium extenses.
     */
    public SuperBoard() {
        super(21);

        setDoubleWordTiles();
        setTripleWordTiles();
        setQuadrupleWordTiles();
        setDoubleLetterTiles();
        setTripleLetterTiles();
        setQuadrupleLetterTiles();
    }

    /**
     * Estableix les posicions de les caselles que dupliquen el valor de la paraula.
     */
    private void setDoubleWordTiles() {
        super.setPremiumTile(1, 1, PremiumTileType.DoubleWord);
        super.setPremiumTile(8, 1, PremiumTileType.DoubleWord);
        super.setPremiumTile(12, 1, PremiumTileType.DoubleWord);
        super.setPremiumTile(19, 1, PremiumTileType.DoubleWord);

        super.setPremiumTile(2, 2, PremiumTileType.DoubleWord);
        super.setPremiumTile(9, 2, PremiumTileType.DoubleWord);
        super.setPremiumTile(11, 2, PremiumTileType.DoubleWord);
        super.setPremiumTile(18, 2, PremiumTileType.DoubleWord);

        super.setPremiumTile(4, 4, PremiumTileType.DoubleWord);
        super.setPremiumTile(16, 4, PremiumTileType.DoubleWord);

        super.setPremiumTile(5, 5, PremiumTileType.DoubleWord);
        super.setPremiumTile(15, 5, PremiumTileType.DoubleWord);

        super.setPremiumTile(6, 6, PremiumTileType.DoubleWord);
        super.setPremiumTile(14, 6, PremiumTileType.DoubleWord);

        super.setPremiumTile(7, 7, PremiumTileType.DoubleWord);
        super.setPremiumTile(13, 7, PremiumTileType.DoubleWord);

        super.setPremiumTile(1, 8, PremiumTileType.DoubleWord);
        super.setPremiumTile(19, 8, PremiumTileType.DoubleWord);

        super.setPremiumTile(2, 9, PremiumTileType.DoubleWord);
        super.setPremiumTile(18, 9, PremiumTileType.DoubleWord);

        super.setPremiumTile(2, 11, PremiumTileType.DoubleWord);
        super.setPremiumTile(18, 11, PremiumTileType.DoubleWord);

        super.setPremiumTile(1, 12, PremiumTileType.DoubleWord);
        super.setPremiumTile(19, 12, PremiumTileType.DoubleWord);

        super.setPremiumTile(7, 13, PremiumTileType.DoubleWord);
        super.setPremiumTile(13, 13, PremiumTileType.DoubleWord);

        super.setPremiumTile(6, 14, PremiumTileType.DoubleWord);
        super.setPremiumTile(14, 14, PremiumTileType.DoubleWord);

        super.setPremiumTile(5, 15, PremiumTileType.DoubleWord);
        super.setPremiumTile(15, 15, PremiumTileType.DoubleWord);

        super.setPremiumTile(4, 16, PremiumTileType.DoubleWord);
        super.setPremiumTile(16, 16, PremiumTileType.DoubleWord);

        super.setPremiumTile(2, 18, PremiumTileType.DoubleWord);
        super.setPremiumTile(9, 18, PremiumTileType.DoubleWord);
        super.setPremiumTile(11, 18, PremiumTileType.DoubleWord);
        super.setPremiumTile(18, 18, PremiumTileType.DoubleWord);

        super.setPremiumTile(1, 19, PremiumTileType.DoubleWord);
        super.setPremiumTile(8, 19, PremiumTileType.DoubleWord);
        super.setPremiumTile(12, 19, PremiumTileType.DoubleWord);
        super.setPremiumTile(19, 19, PremiumTileType.DoubleWord);
    }

    /**
     * Estableix les posicions de les caselles que tripliquen el valor de la paraula.
     */
    private void setTripleWordTiles() {
        super.setPremiumTile(7, 0, PremiumTileType.TripleWord);
        super.setPremiumTile(13, 0, PremiumTileType.TripleWord);

        super.setPremiumTile(3, 3, PremiumTileType.TripleWord);
        super.setPremiumTile(10, 3, PremiumTileType.TripleWord);
        super.setPremiumTile(17, 3, PremiumTileType.TripleWord);

        super.setPremiumTile(0, 7, PremiumTileType.TripleWord);
        super.setPremiumTile(20, 7, PremiumTileType.TripleWord);

        super.setPremiumTile(3, 10, PremiumTileType.TripleWord);
        super.setPremiumTile(17, 10, PremiumTileType.TripleWord);

        super.setPremiumTile(0, 13, PremiumTileType.TripleWord);
        super.setPremiumTile(20, 13, PremiumTileType.TripleWord);

        super.setPremiumTile(3, 17, PremiumTileType.TripleWord);
        super.setPremiumTile(10, 17, PremiumTileType.TripleWord);
        super.setPremiumTile(17, 17, PremiumTileType.TripleWord);

        super.setPremiumTile(7, 20, PremiumTileType.TripleWord);
        super.setPremiumTile(13, 20, PremiumTileType.TripleWord);
    }

    /**
     * Estableix les posicions de les caselles que quadrupliquen el valor de la paraula.
     */
    private void setQuadrupleWordTiles() {
        super.setPremiumTile(0, 0, PremiumTileType.QuadrupleWord);
        super.setPremiumTile(0, 20, PremiumTileType.QuadrupleWord);
        super.setPremiumTile(20, 0, PremiumTileType.QuadrupleWord);
        super.setPremiumTile(20, 20, PremiumTileType.QuadrupleWord);
    }

    /**
     * Estableix les posicions de les caselles que dupliquen el valor de la lletra.
     */
    private void setDoubleLetterTiles() {
        super.setPremiumTile(3, 0, PremiumTileType.DoubleLetter);
        super.setPremiumTile(10, 0, PremiumTileType.DoubleLetter);
        super.setPremiumTile(17, 0, PremiumTileType.DoubleLetter);

        super.setPremiumTile(0, 3, PremiumTileType.DoubleLetter);
        super.setPremiumTile(6, 3, PremiumTileType.DoubleLetter);
        super.setPremiumTile(14, 3, PremiumTileType.DoubleLetter);
        super.setPremiumTile(20, 3, PremiumTileType.DoubleLetter);

        super.setPremiumTile(9, 5, PremiumTileType.DoubleLetter);
        super.setPremiumTile(11, 5, PremiumTileType.DoubleLetter);

        super.setPremiumTile(3, 6, PremiumTileType.DoubleLetter);
        super.setPremiumTile(10, 6, PremiumTileType.DoubleLetter);
        super.setPremiumTile(17, 6, PremiumTileType.DoubleLetter);

        super.setPremiumTile(5, 9, PremiumTileType.DoubleLetter);
        super.setPremiumTile(9, 9, PremiumTileType.DoubleLetter);
        super.setPremiumTile(11, 9, PremiumTileType.DoubleLetter);
        super.setPremiumTile(15, 9, PremiumTileType.DoubleLetter);

        super.setPremiumTile(0, 10, PremiumTileType.DoubleLetter);
        super.setPremiumTile(6, 10, PremiumTileType.DoubleLetter);
        super.setPremiumTile(14, 10, PremiumTileType.DoubleLetter);
        super.setPremiumTile(20, 10, PremiumTileType.DoubleLetter);

        super.setPremiumTile(5, 11, PremiumTileType.DoubleLetter);
        super.setPremiumTile(9, 11, PremiumTileType.DoubleLetter);
        super.setPremiumTile(11, 11, PremiumTileType.DoubleLetter);
        super.setPremiumTile(15, 11, PremiumTileType.DoubleLetter);

        super.setPremiumTile(3, 14, PremiumTileType.DoubleLetter);
        super.setPremiumTile(10, 14, PremiumTileType.DoubleLetter);
        super.setPremiumTile(17, 14, PremiumTileType.DoubleLetter);

        super.setPremiumTile(9, 15, PremiumTileType.DoubleLetter);
        super.setPremiumTile(11, 15, PremiumTileType.DoubleLetter);

        super.setPremiumTile(0, 17, PremiumTileType.DoubleLetter);
        super.setPremiumTile(6, 17, PremiumTileType.DoubleLetter);
        super.setPremiumTile(14, 17, PremiumTileType.DoubleLetter);
        super.setPremiumTile(20, 17, PremiumTileType.DoubleLetter);

        super.setPremiumTile(3, 20, PremiumTileType.DoubleLetter);
        super.setPremiumTile(10, 20, PremiumTileType.DoubleLetter);
        super.setPremiumTile(17, 20, PremiumTileType.DoubleLetter);
    }

    /**
     * Estableix les posicions de les caselles que tripliquen el valor de la lletra.
     */
    private void setTripleLetterTiles() {
        super.setPremiumTile(4, 1, PremiumTileType.TripleLetter);
        super.setPremiumTile(16, 1, PremiumTileType.TripleLetter);

        super.setPremiumTile(1, 4, PremiumTileType.TripleLetter);
        super.setPremiumTile(8, 4, PremiumTileType.TripleLetter);
        super.setPremiumTile(12, 4, PremiumTileType.TripleLetter);
        super.setPremiumTile(19, 4, PremiumTileType.TripleLetter);

        super.setPremiumTile(4, 8, PremiumTileType.TripleLetter);
        super.setPremiumTile(8, 8, PremiumTileType.TripleLetter);
        super.setPremiumTile(12, 8, PremiumTileType.TripleLetter);
        super.setPremiumTile(16, 8, PremiumTileType.TripleLetter);

        super.setPremiumTile(4, 12, PremiumTileType.TripleLetter);
        super.setPremiumTile(8, 12, PremiumTileType.TripleLetter);
        super.setPremiumTile(12, 12, PremiumTileType.TripleLetter);
        super.setPremiumTile(16, 12, PremiumTileType.TripleLetter);

        super.setPremiumTile(1, 16, PremiumTileType.TripleLetter);
        super.setPremiumTile(8, 16, PremiumTileType.TripleLetter);
        super.setPremiumTile(12, 16, PremiumTileType.TripleLetter);
        super.setPremiumTile(19, 16, PremiumTileType.TripleLetter);

        super.setPremiumTile(4, 19, PremiumTileType.TripleLetter);
        super.setPremiumTile(16, 19, PremiumTileType.TripleLetter);
    }

    /**
     * Estableix les posicions de les caselles que quadrupliquen el valor de la lletra.
     */
    private void setQuadrupleLetterTiles() {
        super.setPremiumTile(5, 2, PremiumTileType.QuadrupleLetter);
        super.setPremiumTile(15, 2, PremiumTileType.QuadrupleLetter);

        super.setPremiumTile(2, 5, PremiumTileType.QuadrupleLetter);
        super.setPremiumTile(18, 5, PremiumTileType.QuadrupleLetter);

        super.setPremiumTile(2, 15, PremiumTileType.QuadrupleLetter);
        super.setPremiumTile(18, 15, PremiumTileType.QuadrupleLetter);

        super.setPremiumTile(5, 18, PremiumTileType.QuadrupleLetter);
        super.setPremiumTile(15, 18, PremiumTileType.QuadrupleLetter);
    }

    /**
     * Crea una còpia d'aquest tauler.
     *
     * @return una nova instància de SuperBoard amb la mateixa configuració.
     */
    @Override
    protected Board copy() {
        return new SuperBoard();
    }
}
