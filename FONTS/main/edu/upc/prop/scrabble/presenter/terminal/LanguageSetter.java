package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.properties.Language;

public class LanguageSetter {

    Language lan = Language.English;

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

    public void print_interface() {
        String Instruction = "\ntype 'next' to go right\ntype 'previous' to go left \ntype 'submit' to select language\n";
        String Up = " ________________________________________\n";
        String Up2 = "|                                        |\n";
        String Mid = switch (lan) {
            case English   -> "|     |En|         Cat         Cast      |\n";
            case Catalan  -> "|      En         |Cat|        Cast      |\n";
            case Spanish -> "|      En          Cat        |Cast|     |\n";
        };
        String Down = "|________________________________________|\n";
        System.out.println(Instruction + Up + Up2 + Mid + Down);
    }

    public Language getLanguage() {
        return lan;
    }
}
