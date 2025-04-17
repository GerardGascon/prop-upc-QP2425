package edu.upc.prop.scrabble.data.crosschecks;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.dawg.DAWG;

public class EnglishCrossChecks extends CrossChecks {
    //ENG: letras normales (hacer int(letra)- int('A')
    private final String[] letters;
    //private final String[] specialPieces;
    public EnglishCrossChecks(Board board, DAWG dawg) {
        super(board);
        letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        //specialPieces = new String[]{};
    }

    @Override
    public String[] getLetters() {
        return letters;
    }

    @Override
    public int getNumberOfLetters() {
        return 26;
    }

    @Override
    public Boolean ableToPlace(int x, int y, String letter) {
        //en ingles no hay casos raros
        int numletter = letter.charAt(0)-'A';
        if(getCrossCheckVer(x, y).get(numletter)) return false;
        else return !getCrossCheckHor(x, y).get(numletter);
    }
}