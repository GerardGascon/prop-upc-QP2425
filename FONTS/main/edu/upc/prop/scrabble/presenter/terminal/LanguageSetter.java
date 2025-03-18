package edu.upc.prop.scrabble.presenter.terminal;


public class LanguageSetter {

    private int selected;
    private final boolean Confirm;


    public void UpdateLanguageVis() {
        String s = print_interface();
        System.out.print(s);
    }

    public LanguageSetter (){
        this.selected = 0;
        this.Confirm = false;
    }

    public void increment_select(){
        selected ++;
        if (selected >= 3)
            selected = 0;
    }

    public String print_interface(){
        String Up =  " ________________________________________\n";
        String Up2 = "|                                        |\n";
        String Mid = "";
        if (selected == 0)
            Mid =    "|     |En|         Cat         Cast      |\n";
        if (selected == 1)
            Mid =    "|      En         |Cat|        Cast      |\n";
        if (selected == 2)
            Mid =    "|      En          Cat        |Cast|     |\n";
        String Down= "|________________________________________|\n";

        return Up + Up2 + Mid + Down;
    }



    //Fer algo de que segons el selected llegeix English | Catalan | Castellan
    // Carrego arxiu y enviar lo que hem demani BagFiller

    public void load_language(){

    }

}
