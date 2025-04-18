package edu.upc.prop.scrabble.data.board;

/**
 * Represents a Scrabble board configured for the Junior variant.
 * Initializes an 11x11 board with specific premium tile positions.
 *
 * @author Gerard Gascón
 */
public class JuniorBoard extends Board {
    /**
     * Constructs a JuniorBoard with an 11x11 layout and
     * sets all premium tile positions.
     */
    public JuniorBoard() {
        super(11);

        setDoubleWordTiles();
        setTripleWordTiles();
        setDoubleLetterTiles();
        setTripleLetterTiles();
    }

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

    @Override
    protected Board copy() {
        return new JuniorBoard();
    }
}
