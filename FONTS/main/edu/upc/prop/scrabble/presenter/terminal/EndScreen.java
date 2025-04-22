package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.domain.game.IEndScreen;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;

/***
 * Class that shows the endscreen of a game that has ended.
 * @author Gina Escofet González
 */
public class EndScreen implements IEndScreen {
    @Override
    public void show(Player[] sortedPlayers) {
        System.out.println("Game Over!");
        System.out.println("Final Scores:");
        for (int i = 0; i < sortedPlayers.length; ++i) {
            Player p = sortedPlayers[i];
            System.out.println(i + 1 + ". " + p.getName() + ": " + p.getScore());
        }
        SceneManager.getInstance().quit();
    }
}
