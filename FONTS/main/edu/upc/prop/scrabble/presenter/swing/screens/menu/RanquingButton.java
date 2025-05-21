package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import edu.upc.prop.scrabble.presenter.swing.screens.menu.MenuButton;

import javax.swing.*;

public class RanquingButton extends MenuButton {

    private JPanel parentPanel;
    private boolean ranquingActive = false;
    private JPanel ranquingPanel;
    private JComboBox<String> modeSelector;
    private JTextArea ranquingText;

    public RanquingButton(JPanel parent) {
        super("Ranquing");
        this.parentPanel = parent;
        addActionListener(e -> toggleRanquingPanel());
    }

    private void toggleRanquingPanel() {
    }
}