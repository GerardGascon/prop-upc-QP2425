package edu.upc.prop.scrabble.data.board;

public class JuniorBoard extends Board {
    public JuniorBoard() {
        super(11);

        setDoubleWordTiles();
        setTripleWordTiles();
        setDoubleLetterTiles();
        setTripleLetterTiles();
    }

    private void setDoubleWordTiles(){
        super.setTileType(1, 1, TileType.DoubleWord);
        super.setTileType(2, 2, TileType.DoubleWord);
        super.setTileType(8, 8, TileType.DoubleWord);
        super.setTileType(9, 9, TileType.DoubleWord);

        super.setTileType(1, 9, TileType.DoubleWord);
        super.setTileType(2, 8, TileType.DoubleWord);
        super.setTileType(8, 2, TileType.DoubleWord);
        super.setTileType(9, 1, TileType.DoubleWord);
    }

    private void setTripleWordTiles(){
        super.setTileType(0, 0, TileType.TripleWord);
        super.setTileType(0, 5, TileType.TripleWord);
        super.setTileType(0, 10, TileType.TripleWord);

        super.setTileType(5, 0, TileType.TripleWord);
        super.setTileType(5, 10, TileType.TripleWord);

        super.setTileType(10, 0, TileType.TripleWord);
        super.setTileType(10, 5, TileType.TripleWord);
        super.setTileType(10, 10, TileType.TripleWord);
    }

    private void setDoubleLetterTiles(){
        super.setTileType(4, 1, TileType.DoubleLetter);
        super.setTileType(6, 1, TileType.DoubleLetter);

        super.setTileType(5, 2, TileType.DoubleLetter);

        super.setTileType(1, 4, TileType.DoubleLetter);
        super.setTileType(4, 4, TileType.DoubleLetter);
        super.setTileType(6, 4, TileType.DoubleLetter);
        super.setTileType(9, 4, TileType.DoubleLetter);

        super.setTileType(2, 5, TileType.DoubleLetter);
        super.setTileType(8, 5, TileType.DoubleLetter);

        super.setTileType(1, 6, TileType.DoubleLetter);
        super.setTileType(4, 6, TileType.DoubleLetter);
        super.setTileType(6, 6, TileType.DoubleLetter);
        super.setTileType(9, 6, TileType.DoubleLetter);

        super.setTileType(5, 8, TileType.DoubleLetter);

        super.setTileType(4, 9, TileType.DoubleLetter);
        super.setTileType(6, 9, TileType.DoubleLetter);
    }

    private void setTripleLetterTiles(){
        super.setTileType(3, 0, TileType.TripleLetter);
        super.setTileType(7, 0, TileType.TripleLetter);

        super.setTileType(0, 3, TileType.TripleLetter);
        super.setTileType(3, 3, TileType.TripleLetter);
        super.setTileType(7, 3, TileType.TripleLetter);
        super.setTileType(10, 3, TileType.TripleLetter);

        super.setTileType(0, 7, TileType.TripleLetter);
        super.setTileType(3, 7, TileType.TripleLetter);
        super.setTileType(7, 7, TileType.TripleLetter);
        super.setTileType(10, 7, TileType.TripleLetter);

        super.setTileType(3, 10, TileType.TripleLetter);
        super.setTileType(7, 10, TileType.TripleLetter);
    }
}
