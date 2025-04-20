package edu.upc.prop.scrabble.data.crosschecks;

public class SpanishCrossChecks extends CrossChecks {
    // Letras normales + pos 27 para la Ñ, pos 28 para la RR, 29 LL, 30 CH
    private final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Ñ", "RR", "LL", "CH"};

    public SpanishCrossChecks(int boardSize) {
        super(boardSize);
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
        return !getCrossCheck(x, y).get(getNumLetter(letter));
    }

    private int getNumLetter(String letter) {
        if (letter.length() == 1) {
            if (letter.charAt(0) == 'Ñ')
                return 27;
            return letter.charAt(0) - 'A';
        }
        if (letter.charAt(0) == 'R')
            return 28;
        if (letter.charAt(0) == 'L')
            return 29;
        return 30;
    }

    @Override
    protected CrossChecks copy() {
        return new SpanishCrossChecks(boardSize);
    }
}