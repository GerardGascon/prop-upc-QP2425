package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;

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
    private FloatingTile selectedTile = null;
    private Point lastMousePos = null;
    private long lastDragTime = 0;
    private Point lastDragPos = null;
    private static final int DRAG_HISTORY_SIZE = 10;
    private final Point[] dragHistory = new Point[DRAG_HISTORY_SIZE];
    private final long[] dragTimeHistory = new long[DRAG_HISTORY_SIZE];
    private int dragHistoryIndex = 0;
    private boolean dragHistoryFull = false;


    /**
     * Creadora i inicialitzadora
     */
    public MenuScreen(JFrame window, Leaderboard leaderboard) {
        setDoubleBuffered(true);
        setLayout(null);
        setBackground(new Color(64, 88, 214));

        this.buttonPanel = new JPanel();
        add(buttonPanel);
        buttonPanel.setOpaque(false);

        playButton = new JugarButton(buttonPanel, window);
        continueButton = new ContinueButton(buttonPanel, window);
        rankingButton = new RanquingButton(buttonPanel, leaderboard);
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

    /**
     * Actualitza la posició i animació de les peces flotants.
     * També comprova col·lisions entre peces per ajustar la seva direcció i velocitat.
     *
     * @param delta Temps transcorregut des de l'última actualització (en segons).
     */
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

    {
        /**
         * Inicialitza els listeners de ratolí per detectar:
         * <ul>
         *     <li>Selecció de peces mitjançant clic (mousePressed)</li>
         *     <li>Arrossegament i llançament amb velocitat simulada (mouseDragged + mouseReleased)</li>
         * </ul>
         * Aquesta funcionalitat permet una interacció natural i dinàmica amb les peces del menú.
         */
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (floatingTiles == null) return;

                for (FloatingTile tile : floatingTiles) {
                    if (tile.contains(e.getX(), e.getY())) {
                        selectedTile = tile;
                        lastMousePos = e.getPoint();
                        tile.prevSpeed = tile.speed;
                        tile.speed = 0;
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (selectedTile != null) {
                    int newestIndex = (dragHistoryIndex - 1 + DRAG_HISTORY_SIZE) % DRAG_HISTORY_SIZE;
                    int oldestIndex = dragHistoryFull ? dragHistoryIndex : 0;

                    Point newest = dragHistory[newestIndex];
                    Point oldest = dragHistory[oldestIndex];
                    long timeDiff = dragTimeHistory[newestIndex] - dragTimeHistory[oldestIndex];

                    if (timeDiff > 0 && newest != null && oldest != null && !newest.equals(oldest)) {
                        float dt = timeDiff / 1000.0f;
                        float dx = newest.x - oldest.x;
                        float dy = newest.y - oldest.y;
                        float dist = (float) Math.sqrt(dx * dx + dy * dy);
                        float speed = dist / dt;

                        speed = Math.max(selectedTile.prevSpeed, Math.min(1250f, speed));

                        if (speed > 0) {
                            selectedTile.speed = speed;
                            selectedTile.horizontalDir = dx / dist;
                            selectedTile.verticalDir = dy / dist;
                        }
                    }
                }

                selectedTile = null;
                lastMousePos = null;
                lastDragPos = null;

                for (int i = 0; i < DRAG_HISTORY_SIZE; i++) {
                    dragHistory[i] = null;
                    dragTimeHistory[i] = 0;
                }
                dragHistoryIndex = 0;
                dragHistoryFull = false;
            }
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (selectedTile != null && lastMousePos != null) {
                    selectedTile.speed = 0;

                    long now = System.currentTimeMillis();
                    Point currentMousePos = e.getPoint();

                    dragHistory[dragHistoryIndex] = currentMousePos;
                    dragTimeHistory[dragHistoryIndex] = now;
                    dragHistoryIndex = (dragHistoryIndex + 1) % DRAG_HISTORY_SIZE;
                    if (dragHistoryIndex == 0) dragHistoryFull = true;

                    int newTileX = currentMousePos.x - selectedTile.size / 2;
                    int newTileY = currentMousePos.y - selectedTile.size / 2;

                    int minX = FloatingTile.sidePanelWidth;
                    int maxX = getWidth() - selectedTile.size;
                    int maxY = getHeight() - selectedTile.size;

                    selectedTile.x = Math.max(minX, Math.min(newTileX, maxX));
                    selectedTile.y = Math.max(0, Math.min(newTileY, maxY));


                    for (FloatingTile tile : floatingTiles) {
                        if (tile != selectedTile && selectedTile.overlaps(tile)) {
                            float dx = tile.x - selectedTile.x;
                            float dy = tile.y - selectedTile.y;
                            float dist = (float) Math.sqrt(dx * dx + dy * dy);

                            if (dist == 0) {
                                dx = 1;
                                dy = 1;
                                dist = 1;
                            }

                            float nx = dx / dist;
                            float ny = dy / dist;

                            float overlapX = (selectedTile.size + tile.size) / 2f - Math.abs(dx);
                            float overlapY = (selectedTile.size + tile.size) / 2f - Math.abs(dy);

                            float pushX = nx * overlapX;
                            float pushY = ny * overlapY;

                            float dragSpeed = 0f;
                            if (dragHistoryFull || dragHistoryIndex > 1) {
                                int lastIndex = (dragHistoryIndex - 1 + DRAG_HISTORY_SIZE) % DRAG_HISTORY_SIZE;
                                int prevIndex = (dragHistoryIndex - 2 + DRAG_HISTORY_SIZE) % DRAG_HISTORY_SIZE;
                                Point lastPos = dragHistory[lastIndex];
                                Point prevPos = dragHistory[prevIndex];
                                long dt = dragTimeHistory[lastIndex] - dragTimeHistory[prevIndex];

                                if (lastPos != null && prevPos != null && dt > 0) {
                                    float vx = lastPos.x - prevPos.x;
                                    float vy = lastPos.y - prevPos.y;
                                    float distMoved = (float) Math.sqrt(vx * vx + vy * vy);
                                    dragSpeed = distMoved / (dt / 1000.0f);
                                }
                            }

                            // Set collided tile direction + scaled speed
                            float impactSpeed = Math.max(tile.prevSpeed, Math.min(1250f, dragSpeed));
                            tile.speed = impactSpeed;
                            tile.horizontalDir = nx;
                            tile.verticalDir = ny;

                            tile.x += pushX * 0.5f;
                            tile.y += pushY * 0.5f;

                            tile.x = Math.max(FloatingTile.sidePanelWidth + (float) tile.size /10, Math.min(tile.x, getWidth() - tile.size - (float) tile.size /10));
                            tile.y = Math.max((float) tile.size /10, Math.min(tile.y, getHeight() - tile.size - (float) tile.size /10));

                            if (tile.x == FloatingTile.sidePanelWidth || tile.x == getWidth() - tile.size) {
                                selectedTile.x -= pushX * 0.5f;
                            }
                            if (tile.y == 0 || tile.y == getHeight() - tile.size) {
                                selectedTile.y -= pushY * 0.5f;
                            }
                        }
                    }

                    repaint();
                }
            }
        });
    }
}
