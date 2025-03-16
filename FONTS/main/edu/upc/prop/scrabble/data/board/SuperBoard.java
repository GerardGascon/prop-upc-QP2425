package edu.upc.prop.scrabble.data.board;

public class SuperBoard extends Board {
    public SuperBoard() {
        super(21);

        setDoubleWordTiles();
        setTripleWordTiles();
        setQuadrupleWordTiles();
        setDoubleLetterTiles();
        setTripleLetterTiles();
        setQuadrupleLetterTiles();
    }

    private void setDoubleWordTiles() {
        super.setTileType(1, 1, TileType.DoubleWord);
        super.setTileType(8, 1, TileType.DoubleWord);
        super.setTileType(12, 1, TileType.DoubleWord);
        super.setTileType(19, 1, TileType.DoubleWord);

        super.setTileType(2, 2, TileType.DoubleWord);
        super.setTileType(9, 2, TileType.DoubleWord);
        super.setTileType(11, 2, TileType.DoubleWord);
        super.setTileType(18, 2, TileType.DoubleWord);

        super.setTileType(4, 4, TileType.DoubleWord);
        super.setTileType(16, 4, TileType.DoubleWord);

        super.setTileType(5, 5, TileType.DoubleWord);
        super.setTileType(15, 5, TileType.DoubleWord);

        super.setTileType(6, 6, TileType.DoubleWord);
        super.setTileType(14, 6, TileType.DoubleWord);

        super.setTileType(7, 7, TileType.DoubleWord);
        super.setTileType(13, 7, TileType.DoubleWord);

        super.setTileType(1, 8, TileType.DoubleWord);
        super.setTileType(19, 8, TileType.DoubleWord);

        super.setTileType(2, 9, TileType.DoubleWord);
        super.setTileType(18, 9, TileType.DoubleWord);

        super.setTileType(2, 11, TileType.DoubleWord);
        super.setTileType(18, 11, TileType.DoubleWord);

        super.setTileType(1, 12, TileType.DoubleWord);
        super.setTileType(19, 12, TileType.DoubleWord);

        super.setTileType(7, 13, TileType.DoubleWord);
        super.setTileType(13, 13, TileType.DoubleWord);

        super.setTileType(6, 14, TileType.DoubleWord);
        super.setTileType(14, 14, TileType.DoubleWord);

        super.setTileType(5, 15, TileType.DoubleWord);
        super.setTileType(15, 15, TileType.DoubleWord);

        super.setTileType(4, 16, TileType.DoubleWord);
        super.setTileType(16, 16, TileType.DoubleWord);

        super.setTileType(2, 18, TileType.DoubleWord);
        super.setTileType(9, 18, TileType.DoubleWord);
        super.setTileType(11, 18, TileType.DoubleWord);
        super.setTileType(18, 18, TileType.DoubleWord);

        super.setTileType(1, 19, TileType.DoubleWord);
        super.setTileType(8, 19, TileType.DoubleWord);
        super.setTileType(12, 19, TileType.DoubleWord);
        super.setTileType(19, 19, TileType.DoubleWord);
    }

    private void setTripleWordTiles() {
        super.setTileType(7, 0, TileType.TripleWord);
        super.setTileType(13, 0, TileType.TripleWord);

        super.setTileType(3, 3, TileType.TripleWord);
        super.setTileType(10, 3, TileType.TripleWord);
        super.setTileType(17, 3, TileType.TripleWord);

        super.setTileType(0, 7, TileType.TripleWord);
        super.setTileType(20, 7, TileType.TripleWord);

        super.setTileType(3, 10, TileType.TripleWord);
        super.setTileType(17, 10, TileType.TripleWord);

        super.setTileType(0, 13, TileType.TripleWord);
        super.setTileType(20, 13, TileType.TripleWord);

        super.setTileType(3, 17, TileType.TripleWord);
        super.setTileType(10, 17, TileType.TripleWord);
        super.setTileType(17, 17, TileType.TripleWord);

        super.setTileType(7, 20, TileType.TripleWord);
        super.setTileType(13, 20, TileType.TripleWord);
    }

    private void setQuadrupleWordTiles() {
        super.setTileType(0, 0, TileType.QuadrupleWord);
        super.setTileType(0, 20, TileType.QuadrupleWord);
        super.setTileType(20, 0, TileType.QuadrupleWord);
        super.setTileType(20, 20, TileType.QuadrupleWord);
    }

    private void setDoubleLetterTiles() {
        super.setTileType(3, 0, TileType.DoubleLetter);
        super.setTileType(10, 0, TileType.DoubleLetter);
        super.setTileType(17, 0, TileType.DoubleLetter);

        super.setTileType(0, 3, TileType.DoubleLetter);
        super.setTileType(6, 3, TileType.DoubleLetter);
        super.setTileType(14, 3, TileType.DoubleLetter);
        super.setTileType(20, 3, TileType.DoubleLetter);

        super.setTileType(9, 5, TileType.DoubleLetter);
        super.setTileType(11, 5, TileType.DoubleLetter);

        super.setTileType(3, 6, TileType.DoubleLetter);
        super.setTileType(10, 6, TileType.DoubleLetter);
        super.setTileType(17, 6, TileType.DoubleLetter);

        super.setTileType(5, 9, TileType.DoubleLetter);
        super.setTileType(9, 9, TileType.DoubleLetter);
        super.setTileType(11, 9, TileType.DoubleLetter);
        super.setTileType(15, 9, TileType.DoubleLetter);

        super.setTileType(0, 10, TileType.DoubleLetter);
        super.setTileType(6, 10, TileType.DoubleLetter);
        super.setTileType(14, 10, TileType.DoubleLetter);
        super.setTileType(20, 10, TileType.DoubleLetter);

        super.setTileType(5, 11, TileType.DoubleLetter);
        super.setTileType(9, 11, TileType.DoubleLetter);
        super.setTileType(11, 11, TileType.DoubleLetter);
        super.setTileType(15, 11, TileType.DoubleLetter);

        super.setTileType(3, 14, TileType.DoubleLetter);
        super.setTileType(10, 14, TileType.DoubleLetter);
        super.setTileType(17, 14, TileType.DoubleLetter);

        super.setTileType(9, 15, TileType.DoubleLetter);
        super.setTileType(11, 15, TileType.DoubleLetter);

        super.setTileType(0, 17, TileType.DoubleLetter);
        super.setTileType(6, 17, TileType.DoubleLetter);
        super.setTileType(14, 17, TileType.DoubleLetter);
        super.setTileType(20, 17, TileType.DoubleLetter);

        super.setTileType(3, 20, TileType.DoubleLetter);
        super.setTileType(10, 20, TileType.DoubleLetter);
        super.setTileType(17, 20, TileType.DoubleLetter);
    }

    private void setTripleLetterTiles() {
        super.setTileType(4, 1, TileType.TripleLetter);
        super.setTileType(16, 1, TileType.TripleLetter);

        super.setTileType(1, 4, TileType.TripleLetter);
        super.setTileType(8, 4, TileType.TripleLetter);
        super.setTileType(12, 4, TileType.TripleLetter);
        super.setTileType(19, 4, TileType.TripleLetter);

        super.setTileType(4, 8, TileType.TripleLetter);
        super.setTileType(8, 8, TileType.TripleLetter);
        super.setTileType(12, 8, TileType.TripleLetter);
        super.setTileType(16, 8, TileType.TripleLetter);

        super.setTileType(4, 12, TileType.TripleLetter);
        super.setTileType(8, 12, TileType.TripleLetter);
        super.setTileType(12, 12, TileType.TripleLetter);
        super.setTileType(16, 12, TileType.TripleLetter);

        super.setTileType(1, 16, TileType.TripleLetter);
        super.setTileType(8, 16, TileType.TripleLetter);
        super.setTileType(12, 16, TileType.TripleLetter);
        super.setTileType(19, 16, TileType.TripleLetter);

        super.setTileType(4, 19, TileType.TripleLetter);
        super.setTileType(16, 19, TileType.TripleLetter);
    }

    private void setQuadrupleLetterTiles() {
        super.setTileType(5, 2, TileType.QuadrupleLetter);
        super.setTileType(15, 2, TileType.QuadrupleLetter);

        super.setTileType(2, 5, TileType.QuadrupleLetter);
        super.setTileType(18, 5, TileType.QuadrupleLetter);

        super.setTileType(2, 15, TileType.QuadrupleLetter);
        super.setTileType(18, 15, TileType.QuadrupleLetter);

        super.setTileType(5, 18, TileType.QuadrupleLetter);
        super.setTileType(15, 18, TileType.QuadrupleLetter);
    }
}
