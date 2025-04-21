package edu.upc.prop.scrabble.data.crosschecks;

public class CatalanCrossChecks extends CrossChecks {
    // Letras normales + pos 27 para la ç, pos 28 para L.L, pos 29 para NY
    private final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Ç", "L·L", "NY"};

    public CatalanCrossChecks(int boardSize) {
        super(boardSize);
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
        return !getCrossCheck(x, y).get(getNumLetter(letter));
    }

    private int getNumLetter(String letter) {
        if (letter.length() == 1) {
            if (letter.charAt(0) == 'Ç')
                return 27;
            return letter.charAt(0) - 'A';
        }

        if (letter.charAt(0) == 'L')
            return 28;
        return 29;
    }

    @Override
    protected CrossChecks copy() {
        return new CatalanCrossChecks(boardSize);
    }
}