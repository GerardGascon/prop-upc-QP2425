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

        Piece p = sut.generatePiece(dPiece);

        assertEquals('D', p.getLetter());
    }

    @Test
    public void whenPassingCPieceStringGenerateCPiece(){
        String dPiece = "C 2 3";
        PieceGenerator sut = new PieceGenerator();

        Piece p = sut.generatePiece(dPiece);

        assertEquals('C', p.getLetter());
    }

    @Test
    public void whenPassingDPieceStringGenerateDValue(){
        String dPiece = "D 4 2";
        PieceGenerator sut = new PieceGenerator();

        Piece p = sut.generatePiece(dPiece);

        assertEquals(2, p.getValue());
    }

    @Test
    public void whenPassingEPieceStringGenerateEValue(){
        String dPiece = "E 12 1";
        PieceGenerator sut = new PieceGenerator();

        Piece p = sut.generatePiece(dPiece);

        assertEquals(1, p.getValue());
    }
}
