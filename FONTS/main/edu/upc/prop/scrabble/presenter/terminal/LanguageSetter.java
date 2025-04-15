package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.properties.Language;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static java.lang.Math.abs;

public class LanguageSetter {

    private String words;
    private String letters;

    Language lan = Language.English;

    //Modificadores
    public void next() {
        switch (lan) {
            case English:
                lan = Language.Catalan;
                break;
            case Catalan:
                lan = Language.Spanish;
                break;
            case Spanish:
                lan = Language.English;
                break;
        }
        print_interface();
    }

    public void previous() {
        switch (lan) {
            case English:
                lan = Language.Spanish;
                break;
            case Catalan:
                lan = Language.English;
                break;
            case Spanish:
                lan = Language.Catalan;
                break;
        }
        print_interface();
    }

    //TODO: Reusar en otro sitio----------------------------------------------------------------------------------------
    public void load_language() {
        String filePathWords;
        String filePathLetters;

        if (lan == Language.English) {
            filePathWords = "FONTS/main/edu/upc/prop/scrabble/locales/english.txt";
            filePathLetters = "FONTS/main/edu/upc/prop/scrabble/locales/letrasENG.txt";
        }
        else if (lan == Language.Catalan) {
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

    public String getWords(){return words;}

    public String getLetters(){return letters;}

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Visualize
    public void print_interface() {
        String Up = " ________________________________________\n";
        String Up2 = "|                                        |\n";
        String Mid = switch (lan) {
            case English   -> "|     |En|         Cat         Cast      |\n";
            case Catalan  -> "|      En         |Cat|        Cast      |\n";
            case Spanish -> "|      En          Cat        |Cast|     |\n";
            default -> "";
        };
        String Down = "|________________________________________|\n";
        System.out.println(Up + Up2 + Mid + Down);
    }

    //Consultores

    public Language getLanguage() {
        return lan;
    }
}
