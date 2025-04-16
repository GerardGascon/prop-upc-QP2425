package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;
import edu.upc.prop.scrabble.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class PlayerSetter {
    private int numplay;
    private List<String> nomPlayers = new ArrayList<>();
    private int numbots;
    private List<String> nomCpus = new ArrayList<>();
    private int mode;  //et diu si sumes a players o bots  Us visual

    public boolean hasEnded (){
        return mode == 1;
    }

    //Modificadores
    public void switchMode() {
        mode++;
        mode = mode%2;
    }

    public void addPlayer(String player) {
        int tmp = numplay + 1;
        if (!((tmp + numbots) > 4)){
            if (!nomPlayers.contains(player)){
            nomPlayers.add(player);
            numplay = tmp;
            }
            else
            {
                System.out.println("\nPlayer already exists!");
            }
        }
    }

    public void Increase(){

        if (mode == 1) {
            int tmp = numbots + 1;
            if (!((tmp + numplay) > 4)){
                nomCpus.add("CPU" + tmp);
                numbots = tmp;
            }
        }
        print_interface();
    }

    public void Decrease(){
        //Check si sumem player
        if (mode == 0) {
            int tmp = numplay - 1;
            if (!((tmp + numbots) < 0)){
                numplay = tmp;
            }
        }
        //Check Si sumem bots
        if (mode == 1) {
            int tmp = numbots - 1;
            if (!((tmp + numplay) < 0)){
                numbots = tmp;
            }
        }
        print_interface();
    }

    //Visualize
    public void print_interface(){
        String instruction = "";
        if (mode == 0)
            instruction = "\nPlease insert a player name or type 'submit' to set the number of CPU's\n";
        else
            instruction = "\nPlease select number of CPU's\ntype 'next' to increase the number of CPU \ntype 'previous' to decrease the number of CPU\ntype 'submit' to set the number of CPU's\n";
        String Up  = "---------------------------------------------------------------\n";
        String Up2 = "|                                                             |\n";
        String Players = "| Players: "; Players = Players + printPlayersName() + "     \n";
        String Up3 = "|                                                             |\n";
        String Cpus = "| CPU: "; Cpus = Cpus + printCpusName() + "                     \n";
        String Up4 = "|                                                             |\n";
        String Up6 = "---------------------------------------------------------------\n";

        System.out.println(instruction+Up+Up2+Players+Up3+Cpus+Up4+Up6);
    }

    public void print_names(){
        for (String nomPlayer : nomPlayers) {
            System.out.println(nomPlayer);
        }
        for (String cpus : nomCpus) {
            System.out.println(cpus);
        }
    }

    public String printPlayersName(){
        String players = "";
        for (String nomPlayer : nomPlayers) {
            players = players + " | " + nomPlayer;
        }
        return players;
    }

    public String printCpusName(){
        String cpus = "";
        for (String nomCpus : nomCpus) {
            cpus = cpus + " | " + nomCpus;
        }
        return cpus;
    }

    public List<String> getPlayersNames(){return nomPlayers;}

    public List<String> getCpusNames(){return nomCpus;}

    public int getRealPlayers(){
        return numplay;
    }

    public int getCpuPlayers(){
        return numbots;
    }

    public int getTotalPlayer(){
        return  numplay + numbots;
    }


}


