package edu.upc.prop.scrabble.domain.turns;


import static edu.upc.prop.scrabble.domain.turns.TurnResult.Skip;

public class Turn {
    private final Endgame endgame;
    private final IGamePlayer[] players;
    private int turnNumber;
    private int skipCounter;

    public Turn(Endgame endgame, IGamePlayer[] players) {
        this.endgame = endgame;
        this.players = players;
        this.turnNumber = 0;
        this.skipCounter = 0;
    }

    public void run() {
        int start = 0, end = 0;
        int modTurn = turnNumber % players.length;

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

        //enum
        if (players[end].endTurn() == Skip)
            skipCounter++;

        players[start].startTurn();
        turnNumber = turnNumber + 1;


        boolean gameended = endgame.run(skipCounter);
        //TODO: ENDGAME! to check si s'ha acbaat la partida (en una classe aparte)
        // ENDGAME una queue de mida 3 (ultimes 3 jugades) per a cadfa jugador que diu si
        // jugada valida o invalida

    }
}
