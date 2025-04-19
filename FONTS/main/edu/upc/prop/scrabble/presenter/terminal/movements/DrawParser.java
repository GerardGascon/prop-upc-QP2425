package edu.upc.prop.scrabble.presenter.terminal.movements;

import edu.upc.prop.scrabble.data.pieces.Piece;

public class DrawParser {
    public static Piece[] parse(String input) {
        String[] pieces = input.replaceAll("\\s+", "").split(",");

        Piece[] pieceArray = new Piece[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].equals("#"))
                pieceArray[i] = new Piece(pieces[i], 0, true);
            else
                pieceArray[i] = new Piece(pieces[i], 0);
        }
        return pieceArray;
    }
}
