package edu.upc.prop.scrabble.data.crosschecks;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.dawg.DAWG;

public class EnglishCrossChecks extends CrossChecks {
    //ENG: letras normales (hacer int(letra)- int('A')
    private final String[] letters;

    public EnglishCrossChecks(Board board, DAWG dawg) {
        super(board);
        letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    }

    @Override
    public String[] getLetters() {
        return letters;
    }

    @Override
    public int getNumberOfLetters() {
        return 26;
    }
}