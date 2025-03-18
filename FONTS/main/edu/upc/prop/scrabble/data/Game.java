package edu.upc.prop.scrabble.data;
import java.util.*;


public class Game {
    private int NumPlayers;
    private Player[] players = new Player[NumPlayers];
    private Board GameBoard;
    private String Language;
    //Falta que creein la clase Bag :D
    private Bag GameBag;



    public void SetNumPlayers(int n){
        if (n > 4)
            System.out.println("Error no hi poden haber més de 4 jugadors");
        else if (n < 1)
            System.out.println("Error ha d'haber-hi més de 1 jugador");
        else
        NumPlayers = n;
    }

    //Inserció dels jugadors
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setLanguage(String Lan){
        Language = Lan;
    }


    public void save(){
        //Guardem num Players
        String SaveNumPlayers = String.valueOf(NumPlayers) + "\n";
        //Guardem Players
        String[] SavePlayers = {"Player1: ", "Player2: ", "Player3: ", "Player4: "};
        for (int i = 0; i < 4; ++i){
            SavePlayers[i] += "Name: " + players[i].getName() + " CPU: " + players[i].getCPU() + "Hand: " +  players[i].getHand() + "\n";
        }

        //Guardem Board Status
        String SaveBoard = "Board: \n";
        SaveBoard += "Size: " + GameBoard.getSize() + "\n";
        //Hablar con Geri :D
        SaveBoard += "Tauler: " + GameBoard.printBoard() + "\n";

        //Guardem Language
        String SaveLanguage = "Language: " + Language + "\n";

        //Guardem La Bosa
        //Hablar con Gina :D
        String SaveBosa = "Bosa: " + Bag.getLista() + "\n";


        String SaveFile = SaveNumPlayers + SavePlayers[0] + SavePlayers[1] + SavePlayers[2] + SavePlayers[3] + SaveBoard + SaveLanguage + SaveBosa;
        //De momento lo printea y ya | Ya hare para que lo guarde en un fichero PREGUNTAR PROFE SI PODEMOS USAR LIBRERIAS DE FUKCING .txt
        System.out.println(SaveFile);
    }

    public void load(String SaveFile){
        //Obtenir numplayer
        NumPlayers = Character.getNumericValue(SaveFile.charAt(0));
        SetNumPlayers(NumPlayers);

        int i = 0;
        while (SaveFile.charAt(i) != '\n') {
            ++i;
        }
        ++i;

        Player[] LoadPlayers = new Player[NumPlayers];
        //Obtenim Playerss
        for (int j = 0; j < 4; ++j) {
            //Saltar Player1:_Name:_
            i += 15;
            String Name = "";
            while(SaveFile.charAt(i) != ' '){
                Name = Name + SaveFile.charAt(i);
                ++i;
            }
            //Saltar _CPU:_
            i += 6;
            String CPU = "";
            while(SaveFile.charAt(i) != ' '){
                CPU = CPU + SaveFile.charAt(i);
                ++i;
            }
            boolean IsCPU = CPU.equals("true");

            //Saltar _Hand:_
            i += 7;
            String Hand = "";
            while(SaveFile.charAt(i) != '\n'){
                Hand = Hand + SaveFile.charAt(i);
                ++i;
            }
            ++i;

            Player Jugador;
            //ALUSAT NECESITO ESTAS FUNCIONES!
            Jugador.setName(Name);
            Jugador.setCPU(CPU);
            Jugador.setHand(Hand);

            LoadPlayers[j] = Jugador;
        }
        setPlayers(LoadPlayers);
        ++i;
        i += 13;

        //Obtenim Board
        int Size = 0;
        while (SaveFile.charAt(i) != '\n'){
            Size = Size * 10 + Character.getNumericValue(SaveFile.charAt(i));
        }
        ++i;

        String[][] MyBoard = new String[Size][Size];
        for (int j = 0; j < Size; ++j){
            int k = 0;
            while (SaveFile.charAt(i) != '\n'){
                MyBoard[j][k] = (String.valueOf(SaveFile.charAt(i)));
                ++i;
                ++k;
            }
            ++i;
        }

        //GERI NECESITO ESTAS FUNCIONES!
        GameBoard.setSize(Size);
        GameBoard.setBoard(MyBoard);

        //Obtenim Language
        i += 10;
        String LoadLanguage = "";
        while(SaveFile.charAt(i) != '\n'){
            LoadLanguage = LoadLanguage + SaveFile.charAt(i);
            ++i;
        }
        ++i;
        setLanguage(LoadLanguage);

        //Obtenim Bosa
        //Esperar
    }
}
