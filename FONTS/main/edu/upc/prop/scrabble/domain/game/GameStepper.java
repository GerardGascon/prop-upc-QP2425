package edu.upc.prop.scrabble.domain.game;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.leaderboard.Score;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.domain.turns.TurnResult;
import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Classe responsable d'actualitzar el leaderboard quan la partida finalitza.
 * També gestiona l'execució dels torns i mostra la pantalla final.
 *
 * @author Biel Pérez Silvestre
 */
public class GameStepper {
    /**
     * Objecte que gestiona el flux dels torns de la partida.
     * @see Turn
     */
    private final Turn turn;
    /**
     * Objecte que representa el leaderboard on s'actualitzen les puntuacions.
     * @see Leaderboard
     */
    private final Leaderboard leaderboard;
    /**
     * Array dels jugadors participants a la partida.
     * @see Player
     */
    private final Player[] players;
    /**
     * Objecte encarregat de mostrar la pantalla final de la partida.
     * @see IEndScreen
     */
    private final IEndScreen endScreen;

    private final GameSaver leaderboardSaver;

    /**
     * Constructor de GameStepper.
     * @param turn Objecte que gestiona els torns.
     * @param leaderboard Leaderboard on es registren les puntuacions.
     * @param players Jugadors de la partida.
     * @param endScreen Pantalla final a mostrar quan acaba la partida.
     */
    public GameStepper(Turn turn, Leaderboard leaderboard,Player[] players,IEndScreen endScreen, GameSaver leaderBoardSaver) {
        this.turn = turn;
        this.leaderboard = leaderboard;
        this.players = players;
        this.endScreen = endScreen;
        this.leaderboardSaver = leaderBoardSaver;
    }

    /**
     * Executa el torn actual, actualitza el leaderboard si la partida ha acabat,
     * i mostra la pantalla final amb els jugadors ordenats per puntuació.
     * @param result Resultat del torn actual.
     */
    public void run(TurnResult result){
        Player[] sortedPlayers = Arrays.stream(players)
                .sorted(Comparator.comparingInt(Player::getScore).reversed())
                .toArray(Player[]::new);

        boolean ended = turn.run(result);
        if (ended) {
           int maxScore = sortedPlayers[0].getScore();

           for (Player player : players) {
               if (player.getCPU())
                   continue;

               boolean winner = (player.getScore() == maxScore);
               leaderboard.addScore(new Score(player.getScore(),winner,player.getName()));
           }
           endScreen.show(sortedPlayers);
           leaderboardSaver.run();
       }
    }
}
