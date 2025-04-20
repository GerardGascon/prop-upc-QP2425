package edu.upc.prop.scrabble.domain.game;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.leaderboard.Score;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.domain.turns.TurnResult;

/**
 * Class that check each if the game has ended,case in which it updates the leaderboard
 * @author  Biel Pérez Silvestre
 */
public class GameStepper {
    private final Turn turn;
    private final Leaderboard leaderboard;
    private final Player[] players;
    private final IEndScreen endScreen;

    public GameStepper(Turn turn, Leaderboard leaderboard,Player[] players,IEndScreen endScreen) {
        this.turn = turn;
        this.leaderboard = leaderboard;
        this.players = players;
        this.endScreen = endScreen;
    }

    public void run(TurnResult result){
       boolean ended = turn.run(result);
       if (ended) {
           int maxScore = 0;
           //Check who is the winner
           for (Player player : players) {
                if (player.getScore() > maxScore) {
                    maxScore = player.getScore();
                }
           }

           for (Player player : players) {
               boolean winner = (player.getScore() == maxScore);
               leaderboard.addScore(new Score(player.getScore(),winner,player.getName()));
           }
           endScreen.show(players);
       }
    }
}
