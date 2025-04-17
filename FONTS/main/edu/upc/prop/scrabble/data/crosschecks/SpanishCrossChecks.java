package edu.upc.prop.scrabble.data.crosschecks;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.dawg.DAWG;

public class SpanishCrossChecks extends CrossChecks {
    //ESP: letras normales + pos 27 para la ñ, pos 28 para la RR, 29 LL, 30 CH
    private final String[] letters;
    private final String[] specialPieces;

    public SpanishCrossChecks(Board board, DAWG dawg) {
        super(board);
        letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Ñ", "RR", "LL", "CH"};
        specialPieces = new String[]{"Ñ", "RR", "LL", "CH"};
    }

    @Override
    public String[] getLetters() {
        return letters;
    }

    @Override
    public int getNumberOfLetters() {
        return 30;
    }

    @Override
    public Boolean ableToPlace(int x, int y, String letter) {
        int numletter;
        if(letters.length == 1) {
            if(letter.charAt(0) == 'Ñ') numletter = 27;
            else numletter = letter.charAt(0) - 'A';
        }
        else if(letter.charAt(0) == 'R') numletter = 28;
        else if(letter.charAt(0) == 'L') numletter = 29;
        else numletter = 30;

        if(getCrossCheckVer(x, y).get(numletter)) return false;
        else return !getCrossCheckHor(x, y).get(numletter);
    }
}