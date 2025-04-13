package edu.upc.prop.scrabble.presenter.terminal.draw;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;

import java.util.Arrays;
import java.util.List;

public class DrawReader {
    private final IDrawReader drawReader;
    private final PieceDrawer pieceDrawer;

    public DrawReader(IDrawReader drawReader, PieceDrawer pieceDrawer) {
        this.drawReader = drawReader;
        this.pieceDrawer = pieceDrawer;
    }

    public void run(int n) {
        Piece[] piecesToDraw = drawReader.run(n);
        Piece[] drawnPieces = pieceDrawer.run(piecesToDraw);
        System.out.print("Drawn pieces: ");
        for (Piece p : drawnPieces) {
            System.out.print(p + " ");
        }
        System.out.println();
    }
}
