package edu.upc.prop.scrabble.data.board;

public class StandardBoard extends Board {
    public StandardBoard() {
        super(15);

        setDoubleWordTiles();
        setTripleWordTiles();
        setDoubleLetterTiles();
        setTripleLetterTiles();
    }

    private void setDoubleWordTiles(){
        super.setPremiumTile(1, 1, PremiumTileType.DoubleWord);
        super.setPremiumTile(2, 2, PremiumTileType.DoubleWord);
        super.setPremiumTile(3, 3, PremiumTileType.DoubleWord);
        super.setPremiumTile(4, 4, PremiumTileType.DoubleWord);

        super.setPremiumTile(10, 10, PremiumTileType.DoubleWord);
        super.setPremiumTile(11, 11, PremiumTileType.DoubleWord);
        super.setPremiumTile(12, 12, PremiumTileType.DoubleWord);
        super.setPremiumTile(13, 13, PremiumTileType.DoubleWord);

        super.setPremiumTile(1, 13, PremiumTileType.DoubleWord);
        super.setPremiumTile(2, 12, PremiumTileType.DoubleWord);
        super.setPremiumTile(3, 11, PremiumTileType.DoubleWord);
        super.setPremiumTile(4, 10, PremiumTileType.DoubleWord);

        super.setPremiumTile(13, 1, PremiumTileType.DoubleWord);
        super.setPremiumTile(12, 2, PremiumTileType.DoubleWord);
        super.setPremiumTile(11, 3, PremiumTileType.DoubleWord);
        super.setPremiumTile(10, 4, PremiumTileType.DoubleWord);
    }

    private void setTripleWordTiles(){
        super.setPremiumTile(0, 0, PremiumTileType.TripleWord);
        super.setPremiumTile(0, 7, PremiumTileType.TripleWord);
        super.setPremiumTile(0, 14, PremiumTileType.TripleWord);

        super.setPremiumTile(7, 0, PremiumTileType.TripleWord);
        super.setPremiumTile(7, 14, PremiumTileType.TripleWord);

        super.setPremiumTile(14, 0, PremiumTileType.TripleWord);
        super.setPremiumTile(14, 7, PremiumTileType.TripleWord);
        super.setPremiumTile(14, 14, PremiumTileType.TripleWord);
    }

    private void setDoubleLetterTiles(){
        super.setPremiumTile(3, 0, PremiumTileType.DoubleLetter);
        super.setPremiumTile(11, 0, PremiumTileType.DoubleLetter);

        super.setPremiumTile(6, 2, PremiumTileType.DoubleLetter);
        super.setPremiumTile(8, 2, PremiumTileType.DoubleLetter);

        super.setPremiumTile(0, 3, PremiumTileType.DoubleLetter);
        super.setPremiumTile(7, 3, PremiumTileType.DoubleLetter);
        super.setPremiumTile(14, 3, PremiumTileType.DoubleLetter);

        super.setPremiumTile(2, 6, PremiumTileType.DoubleLetter);
        super.setPremiumTile(6, 6, PremiumTileType.DoubleLetter);
        super.setPremiumTile(8, 6, PremiumTileType.DoubleLetter);
        super.setPremiumTile(12, 6, PremiumTileType.DoubleLetter);

        super.setPremiumTile(3, 7, PremiumTileType.DoubleLetter);
        super.setPremiumTile(11, 7, PremiumTileType.DoubleLetter);

        super.setPremiumTile(2, 8, PremiumTileType.DoubleLetter);
        super.setPremiumTile(6, 8, PremiumTileType.DoubleLetter);
        super.setPremiumTile(8, 8, PremiumTileType.DoubleLetter);
        super.setPremiumTile(12, 8, PremiumTileType.DoubleLetter);

        super.setPremiumTile(0, 11, PremiumTileType.DoubleLetter);
        super.setPremiumTile(7, 11, PremiumTileType.DoubleLetter);
        super.setPremiumTile(14, 11, PremiumTileType.DoubleLetter);

        super.setPremiumTile(6, 12, PremiumTileType.DoubleLetter);
        super.setPremiumTile(8, 12, PremiumTileType.DoubleLetter);

        super.setPremiumTile(3, 14, PremiumTileType.DoubleLetter);
        super.setPremiumTile(11, 14, PremiumTileType.DoubleLetter);
    }

    private void setTripleLetterTiles(){
        super.setPremiumTile(5, 1, PremiumTileType.TripleLetter);
        super.setPremiumTile(9, 1, PremiumTileType.TripleLetter);

        super.setPremiumTile(1, 5, PremiumTileType.TripleLetter);
        super.setPremiumTile(5, 5, PremiumTileType.TripleLetter);
        super.setPremiumTile(9, 5, PremiumTileType.TripleLetter);
        super.setPremiumTile(13, 5, PremiumTileType.TripleLetter);

        super.setPremiumTile(1, 9, PremiumTileType.TripleLetter);
        super.setPremiumTile(5, 9, PremiumTileType.TripleLetter);
        super.setPremiumTile(9, 9, PremiumTileType.TripleLetter);
        super.setPremiumTile(13, 9, PremiumTileType.TripleLetter);

        super.setPremiumTile(5, 13, PremiumTileType.TripleLetter);
        super.setPremiumTile(9, 13, PremiumTileType.TripleLetter);
    }
}
