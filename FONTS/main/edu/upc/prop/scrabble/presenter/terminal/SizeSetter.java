package edu.upc.prop.scrabble.presenter.terminal;

public class SizeSetter {
    private int selected;

    //TOT AIXO NECESITA SER CRIDAT PER ALGU (CONTROLADOR ?:D) QUE
    // QUAN ES TOQUIN LES TECLES ES CRIDI RESPECTIVAMENT A CADA FUNCIO

    //Default Junior
    public SizeSetter(int selected) {
        this.selected = 3;
    }

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

    //Visualize
    public void print_interface() {
        String Up = " ________________________________________\n";
        String Up2 = "|                                        |\n";
        String Mid = switch (selected) {
            case 0 -> "|    |Junior|    Standard     Super      |\n";
            case 1 -> "|     Junior    |Standard|    Super      |\n";
            case 2 -> "|     Junior     Standard    |Super|     |\n";
            default -> "";
        };
        String Down = "|________________________________________|\n";
        System.out.println(Up + Up2 + Mid + Down);
    }

    //Consultores
    public int getSelected() {
        return selected;
    }
}
