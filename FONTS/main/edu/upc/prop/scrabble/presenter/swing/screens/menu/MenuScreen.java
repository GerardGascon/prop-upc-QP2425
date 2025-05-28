package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Panel amb els elements a mostrar al menú principal
 * @author Felipe Martínez Lassalle
 * @see JPanel
 */
public class MenuScreen extends JPanel {
    private final float SIDE_PANEL_WIDTH_PERCENT = 0.4f;
    private JPanel buttonPanel;
    private JugarButton playButton;
    private ContinueButton continueButton;
    private RanquingButton rankingButton;
    private MenuButton quitButton;
    private ArrayList<FloatingTile> floatingTiles;


    /**
     * Creadora i inicialitzadora
     */
    public MenuScreen(JFrame window) {
        setDoubleBuffered(true);
        setLayout(null);
        setBackground(new Color(64, 88, 214));

        this.buttonPanel = new JPanel();
        add(buttonPanel);
        buttonPanel.setOpaque(false);

        playButton = new JugarButton(buttonPanel, window);
        continueButton = new ContinueButton(buttonPanel, window);
        rankingButton = new RanquingButton(buttonPanel);
        quitButton = new MenuButton("Sortir");

        playButton.setOtherButtons(new MenuButton[]{continueButton, rankingButton});
        continueButton.setOtherButtons(new MenuButton[]{playButton, rankingButton});
        rankingButton.setOtherButtons(new MenuButton[]{playButton, continueButton});

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
     * Dibuixa el component personalitzat, incloent-hi el panell lateral fosc i el títol "Scrabble".
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
        g2.setColor(new Color(52, 58, 64));
        g2.fillRect(0, 0, (int)(width * SIDE_PANEL_WIDTH_PERCENT), height);

        // Title
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, height / 9));
        FontMetrics fm = g2.getFontMetrics();
        String text = "Scrabble";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int sidePanelWidth = (int)(width * SIDE_PANEL_WIDTH_PERCENT);
        int textX = (sidePanelWidth - textWidth) / 2;
        int titleAreaHeight = height / 3;
        int textY = (titleAreaHeight - textHeight) / 2 + fm.getAscent();
        g2.drawString(text, textX, textY);

        if (floatingTiles == null) {
            floatingTiles = new ArrayList<>();
            String letters = FloatingTile.getRandomString();
            Random rand = new Random();
            int tileSize = Math.min(width, height) / 12;

            FloatingTile.height = height;
            FloatingTile.width = width;
            FloatingTile.sidePanelWidth = sidePanelWidth;


            for (int i = 0; i < letters.length(); i++) {
                FloatingTile newTile;
                do {
                    int x = sidePanelWidth + rand.nextInt(width - sidePanelWidth - tileSize);
                    int y = rand.nextInt(height - tileSize);
                    newTile = new FloatingTile(String.valueOf(letters.charAt(i)), x, y, tileSize);
                } while (newTile.overlapsAny(newTile, floatingTiles));
                floatingTiles.add(newTile);
            }
        }

        for (FloatingTile tile : floatingTiles) tile.draw(g2);

        g2.dispose();
    }

    /**
     * Reorganitza la disposició del panell de botons dins del component.
     * Calcula i assigna les dimensions i posició del primer component fill (suposadament
     * un JPanel amb botons), col·locant-lo a la part inferior del panell lateral.
     */
    @Override
    public void doLayout() {
        super.doLayout();

        int width = getWidth();
        int height = getHeight();

        int sidePanelWidth = (int)(width * SIDE_PANEL_WIDTH_PERCENT);
        int panelWidth = (int)(sidePanelWidth * 0.5);
        int panelHeight = height / 3;

        int x = (sidePanelWidth - panelWidth) / 2;
        int y = height - panelHeight - 40; // bottom margin

        buttonPanel.setBounds(x, y, panelWidth, panelHeight);
    }

    public void updateAnimation(float delta) {
        if (floatingTiles != null && isVisible()) {
            for (FloatingTile tile : floatingTiles) {
                tile.move(delta);
            }

            // Check collisions
            for (int i = 0; i < floatingTiles.size(); i++) {
                for (int j = i + 1; j < floatingTiles.size(); j++) {
                    floatingTiles.get(i).checkCollision(floatingTiles.get(j));
                }
            }
            repaint();
        }
    }
}