package edu.upc.prop.scrabble.data.crosschecks;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.dawg.DAWG;

public class CatalanCrossChecks extends CrossChecks {
    //CAT: letras normales + pos 27 para la ç, pos 28 para L.L, pos 29 para NY
    private final String[] letters;
    private final String[] specialPieces;

    public CatalanCrossChecks(Board board, DAWG dawg) {
        super(board);
        letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Ç", "L·L", "NY"};
        specialPieces = new String[]{"Ç", "L·L", "NY"};
    }

    @Override
    public String[] getLetters() {
        return letters;
    }

    @Override
    public int getNumberOfLetters() {
        return 29;
    }

    @Override
    public Boolean ableToPlace(int x, int y, String letter) {
        int numletter;
        if(letters.length == 1) {
            if(letter.charAt(0) == 'Ç') numletter = 27;
            else numletter = letter.charAt(0) - 'A';
        }
        else if(letter.charAt(0) == 'L') numletter = 28;
        else numletter = 29;

        if(getCrossCheckVer(x, y).get(numletter)) return false;
        else return !getCrossCheckHor(x, y).get(numletter);
    }
}