package edu.upc.prop.scrabble.presenter.swing.scenes;

import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.swing.screens.menu.MenuScreen;

import javax.swing.*;

public class MenuScene extends Scene {
    private final JFrame window;
    private MenuScreen menuScreen;

    public MenuScene(JFrame window) {
        this.window = window;
        window.getContentPane().removeAll();

        menuScreen = new MenuScreen();

        setupButtonActions();

        window.add(menuScreen);
        window.revalidate();
        window.repaint();
    }

    private void setupButtonActions() {
        menuScreen.getPlayButton().addActionListener(e -> handlePlay());
        menuScreen.getContinueButton().addActionListener(e -> handleContinue());
        menuScreen.getRankingButton().addActionListener(e -> handleRanking());
        menuScreen.getQuitButton().addActionListener(e -> System.exit(0));
    }

    private void handlePlay() {
        System.out.println("Play button clicked");
    }

    private void handleContinue() {
        System.out.println("Continue button clicked");
    }

    private void handleRanking() { System.out.println("Ranking button clicked"); }

}