package edu.upc.prop.scrabble.data.crosschecks;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.dawg.DAWG;

public class CatalanCrossChecks extends CrossChecks {
    //CAT: letras normales + pos 27 para la ç, pos 28 para L.L, pos 29 para NY
    private final String[] letters;

    public CatalanCrossChecks(Board board, DAWG dawg) {
        super(board);
        letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Ç", "L·L", "NY"};
    }

    @Override
    public String[] getLetters() {
        return letters;
    }

    @Override
    public int getNumberOfLetters() {
        return 29;
    }
}