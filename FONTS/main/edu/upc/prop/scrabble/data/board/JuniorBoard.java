package edu.upc.prop.scrabble.data.board;

/**
 * Representa un tauler de Scrabble configurat per a la variant Junior.
 * Inicialitza un tauler d'11x11 amb les posicions específiques de les caselles especials.
 *
 * @author Gerard Gascón
 */
public class JuniorBoard extends Board {

    /**
     * Crea una instància de {@code JuniorBoard} amb una disposició d'11x11
     * i configura totes les caselles especials (doble/multiplicador de paraula o lletra).
     */
    public JuniorBoard() {
        super(11);

        setDoubleWordTiles();
        setTripleWordTiles();
        setDoubleLetterTiles();
        setTripleLetterTiles();
    }

    /**
     * Estableix les caselles de doble paraula (Double Word) al tauler Junior.
     */
    private void setDoubleWordTiles() {
        super.setPremiumTile(1, 1, PremiumTileType.DoubleWord);
        super.setPremiumTile(2, 2, PremiumTileType.DoubleWord);
        super.setPremiumTile(8, 8, PremiumTileType.DoubleWord);
        super.setPremiumTile(9, 9, PremiumTileType.DoubleWord);

        super.setPremiumTile(1, 9, PremiumTileType.DoubleWord);
        super.setPremiumTile(2, 8, PremiumTileType.DoubleWord);
        super.setPremiumTile(8, 2, PremiumTileType.DoubleWord);
        super.setPremiumTile(9, 1, PremiumTileType.DoubleWord);
    }

    /**
     * Estableix les caselles de triple paraula (Triple Word) al tauler Junior.
     */
    private void setTripleWordTiles() {
        super.setPremiumTile(0, 0, PremiumTileType.TripleWord);
        super.setPremiumTile(0, 5, PremiumTileType.TripleWord);
        super.setPremiumTile(0, 10, PremiumTileType.TripleWord);

        super.setPremiumTile(5, 0, PremiumTileType.TripleWord);
        super.setPremiumTile(5, 10, PremiumTileType.TripleWord);

        super.setPremiumTile(10, 0, PremiumTileType.TripleWord);
        super.setPremiumTile(10, 5, PremiumTileType.TripleWord);
        super.setPremiumTile(10, 10, PremiumTileType.TripleWord);
    }

    /**
     * Estableix les caselles de doble lletra (Double Letter) al tauler Junior.
     */
    private void setDoubleLetterTiles() {
        super.setPremiumTile(4, 1, PremiumTileType.DoubleLetter);
        super.setPremiumTile(6, 1, PremiumTileType.DoubleLetter);

        super.setPremiumTile(5, 2, PremiumTileType.DoubleLetter);

        super.setPremiumTile(1, 4, PremiumTileType.DoubleLetter);
        super.setPremiumTile(4, 4, PremiumTileType.DoubleLetter);
        super.setPremiumTile(6, 4, PremiumTileType.DoubleLetter);
        super.setPremiumTile(9, 4, PremiumTileType.DoubleLetter);

        super.setPremiumTile(2, 5, PremiumTileType.DoubleLetter);
        super.setPremiumTile(8, 5, PremiumTileType.DoubleLetter);

        super.setPremiumTile(1, 6, PremiumTileType.DoubleLetter);
        super.setPremiumTile(4, 6, PremiumTileType.DoubleLetter);
        super.setPremiumTile(6, 6, PremiumTileType.DoubleLetter);
        super.setPremiumTile(9, 6, PremiumTileType.DoubleLetter);

        super.setPremiumTile(5, 8, PremiumTileType.DoubleLetter);

        super.setPremiumTile(4, 9, PremiumTileType.DoubleLetter);
        super.setPremiumTile(6, 9, PremiumTileType.DoubleLetter);
    }

    /**
     * Estableix les caselles de triple lletra (Triple Letter) al tauler Junior.
     */
    private void setTripleLetterTiles() {
        super.setPremiumTile(3, 0, PremiumTileType.TripleLetter);
        super.setPremiumTile(7, 0, PremiumTileType.TripleLetter);

        super.setPremiumTile(0, 3, PremiumTileType.TripleLetter);
        super.setPremiumTile(3, 3, PremiumTileType.TripleLetter);
        super.setPremiumTile(7, 3, PremiumTileType.TripleLetter);
        super.setPremiumTile(10, 3, PremiumTileType.TripleLetter);

        super.setPremiumTile(0, 7, PremiumTileType.TripleLetter);
        super.setPremiumTile(3, 7, PremiumTileType.TripleLetter);
        super.setPremiumTile(7, 7, PremiumTileType.TripleLetter);
        super.setPremiumTile(10, 7, PremiumTileType.TripleLetter);

        super.setPremiumTile(3, 10, PremiumTileType.TripleLetter);
        super.setPremiumTile(7, 10, PremiumTileType.TripleLetter);
    }

    /**
     * Crea i retorna una còpia del tauler {@code JuniorBoard}.
     * Aquesta còpia manté la mateixa configuració però no les fitxes col·locades.
     *
     * @return una nova instància de {@code JuniorBoard}
     */
    @Override
    protected Board copy() {
        return new JuniorBoard();
    }
}
