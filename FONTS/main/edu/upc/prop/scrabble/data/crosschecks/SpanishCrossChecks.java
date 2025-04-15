package edu.upc.prop.scrabble.data.crosschecks;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.dawg.DAWG;

public class SpanishCrossChecks extends CrossChecks {
    //ESP: letras normales + pos 27 para la ñ, pos 28 para la RR, 29 LL, 30 CH
    private final String[] letters;

    public SpanishCrossChecks(Board board, DAWG dawg) {
        super(board);
        letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Ñ", "RR", "LL", "CH"};
    }

    @Override
    public String[] getLetters() {
        return letters;
    }

    @Override
    public int getNumberOfLetters() {
        return 30;
    }
}