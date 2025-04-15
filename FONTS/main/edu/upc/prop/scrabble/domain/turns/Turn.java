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
        int endTurn = turnNumber % players.length;
        int startTurn = (turnNumber+1) % players.length;


        //enum
        if (players[endTurn].endTurn() == Skip)
            skipCounter++;

        players[startTurn].startTurn();
        turnNumber = turnNumber + 1;


        boolean gameended = endgame.run(skipCounter);
    }
}
