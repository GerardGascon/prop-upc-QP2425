package edu.upc.prop.scrabble.domain.turns;

public class Turn {
    private final IGamePlayer[] players;
    private int turnNumber;

    //Constructora
    public Turn(IGamePlayer[] players) {
        this.players = players;
        this.turnNumber = 0;
    }

    public void run(){

        int start = 0, end = 0;
        boolean endgame = false;
        int modTurn = turnNumber%4;
        while (!endgame) {
            modTurn = turnNumber%4;
            switch (modTurn) {
                case 0 -> {
                    start = 0;
                    end = 3;
                }
                case 1 -> {
                    start = 1;
                    end = 2;
                }
                case 2 -> {
                    start = 2;
                    end = 1;
                }
                case 3 -> {
                    start = 3;
                    end = 2;
                }
            }

            players[end].endTurn();
            players[start].startTurn();
            turnNumber = turnNumber + 1;
            endgame = true;
            //TODO: Winchecker to chekc si s'ha acbaat la partida (en una classe aparte)
            // Winchdecker una queue de mida 3 (ultimes 3 jugades) per a cadfa jugador que diu si
            // jugada valida o invalida
        }

    }
}
