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
        super.setTileType(1, 1, TileType.DoubleWord);
        super.setTileType(2, 2, TileType.DoubleWord);
        super.setTileType(3, 3, TileType.DoubleWord);
        super.setTileType(4, 4, TileType.DoubleWord);

        super.setTileType(10, 10, TileType.DoubleWord);
        super.setTileType(11, 11, TileType.DoubleWord);
        super.setTileType(12, 12, TileType.DoubleWord);
        super.setTileType(13, 13, TileType.DoubleWord);

        super.setTileType(1, 13, TileType.DoubleWord);
        super.setTileType(2, 12, TileType.DoubleWord);
        super.setTileType(3, 11, TileType.DoubleWord);
        super.setTileType(4, 10, TileType.DoubleWord);

        super.setTileType(13, 1, TileType.DoubleWord);
        super.setTileType(12, 2, TileType.DoubleWord);
        super.setTileType(11, 3, TileType.DoubleWord);
        super.setTileType(10, 4, TileType.DoubleWord);
    }

    private void setTripleWordTiles(){
        super.setTileType(0, 0, TileType.TripleWord);
        super.setTileType(0, 7, TileType.TripleWord);
        super.setTileType(0, 14, TileType.TripleWord);

        super.setTileType(7, 0, TileType.TripleWord);
        super.setTileType(7, 14, TileType.TripleWord);

        super.setTileType(14, 0, TileType.TripleWord);
        super.setTileType(14, 7, TileType.TripleWord);
        super.setTileType(14, 14, TileType.TripleWord);
    }

    private void setDoubleLetterTiles(){
        super.setTileType(3, 0, TileType.DoubleLetter);
        super.setTileType(11, 0, TileType.DoubleLetter);

        super.setTileType(6, 2, TileType.DoubleLetter);
        super.setTileType(8, 2, TileType.DoubleLetter);

        super.setTileType(0, 3, TileType.DoubleLetter);
        super.setTileType(7, 3, TileType.DoubleLetter);
        super.setTileType(14, 3, TileType.DoubleLetter);

        super.setTileType(2, 6, TileType.DoubleLetter);
        super.setTileType(6, 6, TileType.DoubleLetter);
        super.setTileType(8, 6, TileType.DoubleLetter);
        super.setTileType(12, 6, TileType.DoubleLetter);

        super.setTileType(3, 7, TileType.DoubleLetter);
        super.setTileType(11, 7, TileType.DoubleLetter);

        super.setTileType(2, 8, TileType.DoubleLetter);
        super.setTileType(6, 8, TileType.DoubleLetter);
        super.setTileType(8, 8, TileType.DoubleLetter);
        super.setTileType(12, 8, TileType.DoubleLetter);

        super.setTileType(0, 11, TileType.DoubleLetter);
        super.setTileType(7, 11, TileType.DoubleLetter);
        super.setTileType(14, 11, TileType.DoubleLetter);

        super.setTileType(6, 12, TileType.DoubleLetter);
        super.setTileType(8, 12, TileType.DoubleLetter);

        super.setTileType(3, 14, TileType.DoubleLetter);
        super.setTileType(11, 14, TileType.DoubleLetter);
    }

    private void setTripleLetterTiles(){
        super.setTileType(5, 1, TileType.TripleLetter);
        super.setTileType(9, 1, TileType.TripleLetter);

        super.setTileType(1, 5, TileType.TripleLetter);
        super.setTileType(5, 5, TileType.TripleLetter);
        super.setTileType(9, 5, TileType.TripleLetter);
        super.setTileType(13, 5, TileType.TripleLetter);

        super.setTileType(1, 9, TileType.TripleLetter);
        super.setTileType(5, 9, TileType.TripleLetter);
        super.setTileType(9, 9, TileType.TripleLetter);
        super.setTileType(13, 9, TileType.TripleLetter);

        super.setTileType(5, 13, TileType.TripleLetter);
        super.setTileType(9, 13, TileType.TripleLetter);
    }
}
