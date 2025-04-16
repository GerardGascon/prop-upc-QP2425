package edu.upc.prop.scrabble.domain.game;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.leaderboard.Score;
import edu.upc.prop.scrabble.domain.turns.Turn;

/**
 * Class that check each if the game has ended,case in which it updates the leaderboard
 * @author  Biel Pérez Silvestre
 */
public class GameStepper {
    private final Turn turn;
    private final Leaderboard leaderboard;
    private final Player[] player;

    public GameStepper(Turn turn, Leaderboard leaderboard,Player[] players) {
        this.turn = turn;
        this.leaderboard = leaderboard;
        this.player = players;
    }

    public void run(){
       boolean ended = turn.run();
       if (ended) {
           int maxScore = 0;
           //Check who is the winner
           for (Player player : player) {
                if (player.getScore() > maxScore) {
                    maxScore = player.getScore();
                }
           }

           for (Player player : player) {
               boolean winner = (player.getScore() == maxScore);
               leaderboard.addScore(new Score(player.getScore(),winner,player.getName()));
           }
       }
    }
}
