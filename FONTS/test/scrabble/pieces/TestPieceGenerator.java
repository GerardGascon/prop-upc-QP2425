package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PieceGenerator;
import edu.upc.prop.scrabble.utils.Pair;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPieceGenerator {
    @Test
    public void whenPassingDPieceStringGenerateDPiece(){
        String dPiece = "D 4 2";
        PieceGenerator sut = new PieceGenerator();

        Pair<Piece, Integer>[] p = sut.run(dPiece);

        assertEquals("D", p[0].first().letter());
    }
    @Test
    public void whenPassingBlankPieceStringGenerateBlankPiece(){
        String dPiece = "# 2 0";
        PieceGenerator sut = new PieceGenerator();

        Pair<Piece, Integer>[] p = sut.run(dPiece);

        assertEquals("#", p[0].first().letter());
        assertTrue(p[0].first().isBlank());
    }

    @Test
    public void whenPassingCPieceStringGenerateCPiece(){
        String dPiece = "C 2 3";
        PieceGenerator sut = new PieceGenerator();

        Pair<Piece, Integer>[] p = sut.run(dPiece);

        assertEquals("C", p[0].first().letter());
    }

    @Test
    public void whenPassingDPieceStringGenerateDValue(){
        String dPiece = "D 4 2";
        PieceGenerator sut = new PieceGenerator();

        Pair<Piece, Integer>[] p = sut.run(dPiece);

        assertEquals(2, p[0].first().value());
    }

    @Test
    public void whenPassingEPieceStringGenerateEValue(){
        String dPiece = "E 12 1";
        PieceGenerator sut = new PieceGenerator();

        Pair<Piece, Integer>[] p = sut.run(dPiece);

        assertEquals(1, p[0].first().value());
    }

    @Test
    public void whenPassingDPieceStringGenerateDCount(){
        String dPiece = "D 4 2";
        PieceGenerator sut = new PieceGenerator();

        Pair<Piece, Integer>[] p = sut.run(dPiece);

        assertEquals(4, p[0].second().intValue());
    }

    @Test
    public void whenPassingEPieceStringGenerateECount(){
        String dPiece = "E 12 1";
        PieceGenerator sut = new PieceGenerator();

        Pair<Piece, Integer>[] p = sut.run(dPiece);

        assertEquals(12, p[0].second().intValue());
    }

    @Test
    public void parseMultiplePieces(){
        String pieces = "E 12 1\nD 4 2";
        PieceGenerator sut = new PieceGenerator();

        Pair<Piece, Integer>[] p = sut.run(pieces);

        assertEquals(2, p.length);
        assertEquals("E", p[0].first().letter());
        assertEquals("D", p[1].first().letter());
    }
}
