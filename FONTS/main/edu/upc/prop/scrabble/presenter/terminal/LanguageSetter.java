package edu.upc.prop.scrabble.presenter.terminal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LanguageSetter {

    private int selected;
    private String words;
    private String letters;


    //TOT AIXO NECESITA SER CRIDAT PER ALGU (CONTROLADOR ?:D) QUE
    // QUAN ES TOQUIN LES TECLES ES CRIDI RESPECTIVAMENT A CADA FUNCIO


    //Modificadores
    public void next() {
        selected++;
        selected = selected%3;
        print_interface();
    }

    public void previous() {
        selected--;
        selected = selected%3;
        print_interface();
    }

    public void load_language() {
        String filePathWords;
        String filePathLetters;

        if (selected == 0) {
            filePathWords = "FONTS/main/edu/upc/prop/scrabble/locales/english.txt";
            filePathLetters = "FONTS/main/edu/upc/prop/scrabble/locales/letrasENG.txt";
        }
        else if (selected == 1) {
            filePathWords = "FONTS/main/edu/upc/prop/scrabble/locales/catalan.txt";
            filePathLetters = "FONTS/main/edu/upc/prop/scrabble/locales/letrasCAT.txt";
        }
        else {
            filePathWords = "FONTS/main/edu/upc/prop/scrabble/locales/castellano.txt";
            filePathLetters = "FONTS/main/edu/upc/prop/scrabble/locales/letrasCAST.txt";
        }

        words = readFileToString(filePathWords);
        letters = readFileToString(filePathLetters);
    }

    //Visualize
    public void print_interface() {
        String Up = " ________________________________________\n";
        String Up2 = "|                                        |\n";
        String Mid = switch (selected) {
            case 0 -> "|     |En|         Cat         Cast      |\n";
            case 1 -> "|      En         |Cat|        Cast      |\n";
            case 2 -> "|      En          Cat        |Cast|     |\n";
            default -> "";
        };
        String Down = "|________________________________________|\n";
        System.out.println(Up + Up2 + Mid + Down);
    }

    //Consultores
    public String getWords(){return words;}

    public String getLetters(){return letters;}

    //Auxiliar
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
