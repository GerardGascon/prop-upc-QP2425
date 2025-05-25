package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import edu.upc.prop.scrabble.presenter.scenes.Scene;

import javax.swing.*;
import java.awt.*;

/**
 * Panel amb els elements a mostrar al menú principal
 * @author Felipe Martínez Lassalle
 * @see JPanel
 */
public class MenuScreen extends JPanel {
    private final float SIDE_PANEL_WIDTH_PERCENT = 0.4f;
    private JugarButton playButton;
    private ContinueButton continueButton;
    private RanquingButton rankingButton;
    private MenuButton quitButton;

    /**
     * Creadora i inicialitzadora
     */
    public MenuScreen() {
        setLayout(null);
        setBackground(new Color(0x54, 0x63, 0xff));

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

    /**
     * Consultora
     * @return Botó Jugar
     */
    public JugarButton getPlayButton() { return playButton; }

    /**
     * Consultora
     * @return Botó Continuar
     */
    public ContinueButton getContinueButton() { return continueButton; }

    /**
     * Consultora
     * @return Botó Ranquing
     */
    public RanquingButton getRankingButton() { return rankingButton; }

    /**
     * Consultora
     * @return Botó Sortir
     */
    public MenuButton getQuitButton() { return quitButton; }

    /**
     * Dibuixa el component personalitzat, incloent el panell lateral fosc i el títol "Scrabble".
     * Aquesta funció s'encarrega de la renderització gràfica del component, utilitzant
     * antialiasing per millorar la qualitat visual del text i les formes.
     * @param g L'objecte Graphics proporcionat pel sistema per dibuixar.
     */
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

    /**
     * Reorganitza la disposició del panell de botons dins del component.
     * Calcula i assigna les dimensions i posició del primer component fill (suposadament
     * un JPanel amb botons), col·locant-lo a la part inferior del panell lateral.
     */
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