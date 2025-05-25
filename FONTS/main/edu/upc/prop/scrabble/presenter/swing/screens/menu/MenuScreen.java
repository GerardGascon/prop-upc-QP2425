package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import javax.swing.*;
import java.awt.*;

public class MenuScreen extends JPanel {
    private final float SIDE_PANEL_WIDTH_PERCENT = 0.4f;
    private JugarButton playButton;
    private ContinueButton continueButton;
    private RanquingButton rankingButton;
    private MenuButton quitButton;

    public MenuScreen() {
        setLayout(null);
        setBackground(new Color(0x54, 0x63, 0xff));
        createButtons();
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        playButton = new JugarButton(buttonPanel);
        continueButton = new ContinueButton(buttonPanel);
        rankingButton = new RanquingButton(buttonPanel);
        quitButton = new MenuButton("Sortir");

        /**
        playButton.setOtherButtons(new MenuButton[]{continueButton, rankingButton});
        continueButton.setOtherButtons(new MenuButton[]{playButton, rankingButton});
        rankingButton.setOtherButtons(new MenuButton[]{playButton, continueButton});
        **/

        buttonPanel.setLayout(new GridLayout(4, 1, 0, 20));
        buttonPanel.add(playButton);
        buttonPanel.add(continueButton);
        buttonPanel.add(rankingButton);
        buttonPanel.add(quitButton);

        add(buttonPanel);
    }

    public JugarButton getPlayButton() { return playButton; }

    public ContinueButton getContinueButton() { return continueButton; }

    public RanquingButton getRankingButton() { return rankingButton; }

    public MenuButton getQuitButton() { return quitButton; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Side panel
        int width = getWidth();
        int height = getHeight();
        g2.setColor(new Color(0x2d, 0x2d, 0x2d));
        g2.fillRect(0, 0, (int)(width * SIDE_PANEL_WIDTH_PERCENT), height);

        // Title
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, height / 8));
        FontMetrics fm = g2.getFontMetrics();
        String text = "Scrabble";
        int textX = width / 20;
        int textY = height / 7 + fm.getAscent() / 2;
        g2.drawString(text, textX, textY);
    }

    @Override
    public void doLayout() {
        super.doLayout();

        JPanel buttonPanel = (JPanel) getComponent(0);
        int panelWidth = getWidth() / 5;
        int panelHeight = getHeight() / 3;
        int x = getWidth() / 20;
        int y = getHeight() - panelHeight - 50;
        buttonPanel.setBounds(x, y, panelWidth, panelHeight);
    }
}