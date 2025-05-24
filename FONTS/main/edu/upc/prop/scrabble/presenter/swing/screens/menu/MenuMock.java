package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import javax.swing.*;
import java.awt.*;

public class MenuMock extends JPanel {
    private final float SIDE_PANEL_WIDTH_PERCENT = 0.4f;
    private final JPanel buttonPanel;

    public MenuMock() {
        setLayout(null);
        buttonPanel = addButtons();
    }

    private void setPlayerColor(Graphics g, int selectedPlayer) {
        switch (selectedPlayer) {
            case 0 -> g.setColor(new Color(0xf5, 0x2e, 0x2e));
            case 1 -> g.setColor(new Color(0x54, 0x63, 0xff));
            case 2 -> g.setColor(new Color(0xff, 0xc7, 0x17));
            case 3 -> g.setColor(new Color(0x1f, 0x9e, 0x40));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        super.paintComponent(g2);
        int width = getWidth();
        int height = getHeight();

        g2.setColor(new Color(0x2d, 0x2d, 0x2d));
        g2.fillRect(0, 0, (int) (width * SIDE_PANEL_WIDTH_PERCENT), height);

        g2.setColor(new Color(0x54, 0x63, 0xff));
        g2.fillRect((int) (width * SIDE_PANEL_WIDTH_PERCENT), 0, (int) (width - width * SIDE_PANEL_WIDTH_PERCENT), height);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, height / 8));
        FontMetrics fm = g2.getFontMetrics();
        String text = "Scrabble";
        int textX = width / 20;
        int textY = height / 7 + fm.getAscent() / 2;
        g2.drawString(text, textX, textY);
    }

    //TODO: Hardodejat WATCH OUT!
    @Override
    public void doLayout() {
        super.doLayout();

        buttonPanel.setBounds(getWidth() / 20, getHeight() - getHeight() / 3, getWidth() / 5, getHeight() / 4);
        buttonPanel.setLayout(new GridLayout(4, 1, 0, getHeight() / 100));
    }

    private JPanel addButtons(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JugarButton jugarButton = new JugarButton(buttonPanel);
        ContinueButton continueButton = new ContinueButton(buttonPanel);

        jugarButton.setOtherButton(continueButton);
        continueButton.setOtherButton(jugarButton);

        buttonPanel.add(jugarButton);
        buttonPanel.add(continueButton);
        buttonPanel.add(new RanquingButton(buttonPanel));

        MenuButton quitButton = new MenuButton("Sortir");
        quitButton.addActionListener(_ -> System.exit(0));
        buttonPanel.add(quitButton);

        add(buttonPanel);
        return buttonPanel;
    }
}
