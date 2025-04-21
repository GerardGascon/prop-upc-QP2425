package edu.upc.prop.scrabble.data.crosschecks;

public class EnglishCrossChecks extends CrossChecks {
    private final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public EnglishCrossChecks(int boardSize) {
        super(boardSize);
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
        return !getCrossCheck(x, y).get(getNumLetter(letter));
    }

    private int getNumLetter(String letter) {
        return letter.charAt(0) - 'A';
    }

    @Override
    protected CrossChecks copy() {
        return new EnglishCrossChecks(boardSize);
    }
}