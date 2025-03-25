package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.presenter.inout;
import edu.upc.prop.scrabble.utils.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class LanguageSetter {

    private int selected;
    private final boolean Confirm;
    private final inout InOut = new inout();
    private String words;
    private String letters;

    public LanguageSetter() {
        this.selected = 0;
        this.Confirm = false;
        this.words = "noInitizalized";
        this.letters = "noInitizalized";
    }

    public String getWords(){return words;}

    public String getLetters(){return letters;}

    public void increment_select() {
        selected++;
        if (selected >= 3) selected = 0;
    }

    public String print_interface() {
        String Up = " ________________________________________\n";
        String Up2 = "|                                        |\n";
        String Mid = switch (selected) {
            case 0 -> "|     |En|         Cat         Cast      |\n";
            case 1 -> "|      En         |Cat|        Cast      |\n";
            case 2 -> "|      En          Cat        |Cast|     |\n";
            default -> "";
        };
        String Down = "|________________________________________|\n";
        return Up + Up2 + Mid + Down;
    }

    public void load_language() {
        String filePathWords;
        String filePathLetters;

        if (selected == 0) {
            filePathWords = "FONTS/main/edu/upc/prop/scrabble/presenter/english.txt";
            filePathLetters = "FONTS/main/edu/upc/prop/scrabble/presenter/letrasENG.txt";
        }
        else if (selected == 1) {
            filePathWords = "FONTS/main/edu/upc/prop/scrabble/presenter/catalan.txt";
            filePathLetters = "FONTS/main/edu/upc/prop/scrabble/presenter/letrasCAT.txt";
        }
        else {
            filePathWords = "FONTS/main/edu/upc/prop/scrabble/presenter/castellano.txt";
            filePathLetters = "FONTS/main/edu/upc/prop/scrabble/presenter/letrasCAST.txt";
        }

        words = readFileToString(filePathWords);
        letters = readFileToString(filePathLetters);
    }

    private String readFileToString(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + filePath + " - " + e.getMessage());
            return null;
        }
        return content.toString();
    }
}
