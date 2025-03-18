package scrabble;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.domain.PieceGenerator;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPieceGenerator {
    @Test
    public void whenPassingDPieceStringGenerateDPiece(){
        String dPiece = "D 4 2";
        PieceGenerator sut = new PieceGenerator();

        Piece[] p = sut.run(dPiece);

        assertEquals('D', p[0].getLetter());
    }

    @Test
    public void whenPassingCPieceStringGenerateCPiece(){
        String dPiece = "C 2 3";
        PieceGenerator sut = new PieceGenerator();

        Piece[] p = sut.run(dPiece);

        assertEquals('C', p[0].getLetter());
    }

    @Test
    public void whenPassingDPieceStringGenerateDValue(){
        String dPiece = "D 4 2";
        PieceGenerator sut = new PieceGenerator();

        Piece[] p = sut.run(dPiece);

        assertEquals(2, p[0].getValue());
    }

    @Test
    public void whenPassingEPieceStringGenerateEValue(){
        String dPiece = "E 12 1";
        PieceGenerator sut = new PieceGenerator();

        Piece[] p = sut.run(dPiece);

        assertEquals(1, p[0].getValue());
    }

    @Test
    public void parseMultiplePieces(){
        String pieces = "E 12 1\nD 4 2";
        PieceGenerator sut = new PieceGenerator();

        Piece[] p = sut.run(pieces);

        assertEquals(2, p.length);
        assertEquals('E', p[0].getLetter());
        assertEquals('D', p[1].getLetter());
    }
}
