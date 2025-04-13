package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.utils.Pair;

public class PlayerSetter {
    private int numplay;
    private int numbots;
    private int mode;  //et diu si sumes a players o bots  Us visual

    //TOT AIXO NECESITA SER CRIDAT PER ALGU (CONTROLADOR ?:D) QUE
    // QUAN ES TOQUIN LES TECLES ES CRIDI RESPECTIVAMENT A CADA FUNCIO

    //Default
    public PlayerSetter() {
        this.numplay = 0;
        this.numbots = 0;
        this.mode = 0;
    }

    //Modificadores
    public void switchMode() {
        mode++;
        mode = mode%2;
    }

    public void Increase(){
        //Check si sumem player
        if (mode == 0) {
            int tmp = numplay++;
            if (!((tmp + numbots) > 4)){
                numplay = tmp;
            }
        }
        //Check Si sumem bots
        if (mode == 1) {
            int tmp = numbots++;
            if (!((tmp + numplay) > 4)){
                numbots = tmp;
            }
        }
        print_interface();
    }

    public void Decrease(){
        //Check si sumem player
        if (mode == 0) {
            int tmp = numplay--;
            if (!((tmp + numbots) < 0)){
                numplay = tmp;
            }
        }
        //Check Si sumem bots
        if (mode == 1) {
            int tmp = numbots++;
            if (!((tmp + numplay) < 0)){
                numbots = tmp;
            }
        }
        print_interface();
    }

    //Visualize
    public void print_interface(){
        String Up  = "---------------------\n";
        String Up2 = "|                    |\n";
        String Players = "| Players: "; Players = Players + numplay + "         |\n";
        String Up3 = "|                    |\n";
        String Cpus = "| CPU: "; Cpus = Cpus + numbots + "      |\n";
        String Up4 = "|                    |\n";
        String Total = "| Total Players: "; Total = Total + (numplay+numbots) + "   |\n";
        String Up5 = "|                    |\n";
        String Up6 = "---------------------\n";

        System.out.println(Up+Up2+Players+Up3+Cpus+Up4+Total+Up5+Up6);
    }

    //Consultores
    public Pair<Integer,Integer> getTotalPlayers(){
        return new Pair<>(numplay,numbots);
    }

    public int getRealPlayers(){
        return numplay;
    }

    public int getCpuPlayers(){
        return numbots;
    }
}


