package edu.upc.prop.scrabble.domain.turns;


import static edu.upc.prop.scrabble.domain.turns.TurnResult.Skip;

/**
 * Class used to manage the turn flow in the game
 * @author Biel Pérez
 */
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

    /**
     * Determine which player ends its turn and which starts it as well
     * as keeping track of how many turns have passed and if this is the last turn
     * @see Endgame
     * @see IGamePlayer
     */
    public void run() {
        int endTurn = turnNumber % players.length;
        int startTurn = (turnNumber+1) % players.length;

        if (players[endTurn].endTurn() == Skip)
            skipCounter++;
        else
            skipCounter = 0;

        players[startTurn].startTurn();
        turnNumber = turnNumber + 1;

        boolean finalturn = endgame.run(skipCounter);
    }
}
