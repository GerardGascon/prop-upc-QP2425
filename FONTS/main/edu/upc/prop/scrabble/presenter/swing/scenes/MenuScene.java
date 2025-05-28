package edu.upc.prop.scrabble.presenter.swing.scenes;

import edu.upc.prop.scrabble.data.leaderboard.Score;
import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.swing.screens.menu.MenuScreen;

import javax.swing.*;

/**
 * Escena encarregada del menú principal
 * @author Felipe Martínez Lassalle
 * @see Scene
 */
public class MenuScene extends Scene {
    private final JFrame window;
    private MenuScreen menuScreen;

    /**
     * Creadora i inicialitzadora de tots els elements presents al menú
     * @param window Finestra sobre la qual es mostrarà el menú
     */
    public MenuScene(JFrame window) {
        this.window = window;
        window.getContentPane().removeAll();

        menuScreen = new MenuScreen(window);

        menuScreen.getPlayButton().addActionListener(e -> handlePlay());
        menuScreen.getContinueButton().addActionListener(e -> handleContinue());
        menuScreen.getRankingButton().addActionListener(e -> handleRanking());
        menuScreen.getQuitButton().addActionListener(e -> System.exit(0));

        window.add(menuScreen);
        window.revalidate();
        window.repaint();
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        window.remove(menuScreen);
    }

    /**
     * Acció cridada al pulsar el botó Jugar
     */
    private void handlePlay() { System.out.println("Play button clicked"); }
    /**
     * Acció cridada al pulsar el botó Continuar
     */
    private void handleContinue() { System.out.println("Continue button clicked"); }

    /**
     * Acció cridada al pulsar el botó Rànquing
     */
    private void handleRanking() { System.out.println("Ranking button clicked"); }

}