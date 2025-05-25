package edu.upc.prop.scrabble.presenter.swing.screens.game;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.domain.game.IEndScreen;
import edu.upc.prop.scrabble.presenter.swing.screens.menu.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * EndScreen és un JPanel que mostra els resultats finals d'una partida de Scrabble.
 * Mostra un podi amb els 3 primers jugadors, una llista completa de classificació, i proporciona
 * botons per iniciar una nova partida, tornar al menú, o sortir de l'aplicació.
 *
 * La pantalla presenta un fons amb degradat amb una visualització de podi i
 * targetes de jugadors codificades per colors que mostren puntuacions i posicions.
 *
 * @author Albert Usero
 */
public class EndScreen extends JPanel implements IEndScreen {
    /** Percentatge d'amplada de pantalla ocupat pel panell lateral */
    private final float SIDE_PANEL_WIDTH_PERCENT = 0.35f;

    /** Percentatge d'alçada de pantalla ocupat per l'àrea del podi */
    private final float PODIUM_HEIGHT_PERCENT = 0.6f;

    /** Alçada en píxels de cada targeta de jugador a la llista de classificació */
    private final int PLAYER_CARD_HEIGHT = 80;

    /** Marge en píxels entre targetes de jugadors */
    private final int PLAYER_CARD_MARGIN = 10;

    /** Array de jugadors de la partida */
    private Player[] players;

    /** Array de jugadors ordenats per puntuació (ordre descendent) */
    private Player[] sortedPlayers;

    /** Panell que conté els botons d'acció */
    private JPanel buttonPanel;

    /** Callback per tornar al menú principal */
    private ActionListener onMenuCallback;

    /** Callback per iniciar una nova partida */
    private ActionListener onNewGameCallback;

    /**
     * Constructor per defecte que crea un EndScreen sense jugadors.
     * Inicialitza el component amb l'estil per defecte i arrays de jugadors buits.
     */
    public EndScreen() {
        setLayout(null);
        setBackground(new Color(0x2d, 0x2d, 0x2d));
        this.players = new Player[0];
        this.sortedPlayers = new Player[0];
        initializeButtons();
    }

    /**
     * Constructor que crea un EndScreen amb els jugadors especificats.
     *
     * @param players Array de jugadors per mostrar a la pantalla final
     */
    public EndScreen(Player[] players) {
        setLayout(null);
        setBackground(new Color(0x2d, 0x2d, 0x2d));
        setPlayers(players);
        initializeButtons();
    }

    /**
     * Mostra la pantalla final amb l'array de jugadors ordenats proporcionat.
     * Fa visible la finestra, la porta al davant i demana el focus.
     *
     * @param sortedPlayers Array de jugadors ordenats per puntuació final
     */
    @Override
    public void show(Player[] sortedPlayers) {
        setPlayers(sortedPlayers);
        setVisible(true);

        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.toFront();
            window.requestFocus();
        }

        repaint();
        revalidate();
    }

    /**
     * Estableix l'array de jugadors per a la pantalla final.
     * Els jugadors ja haurien d'estar ordenats per puntuació en ordre descendent.
     *
     * @param players Array de jugadors ordenats per puntuació, o null per array buit
     */
    public void setPlayers(Player[] players) {
        this.players = players != null ? players : new Player[0];
        // Els jugadors ja venen ordenats segons la interfície
        this.sortedPlayers = Arrays.copyOf(this.players, this.players.length);
        repaint();
    }

    /**
     * Estableix la funció callback que s'executarà quan es cliqui el botó "Menú Principal".
     *
     * @param callback ActionListener per gestionar els clics del botó menú
     */
    public void setOnMenuCallback(ActionListener callback) {
        this.onMenuCallback = callback;
    }

    /**
     * Estableix la funció callback que s'executarà quan es cliqui el botó "Nova Partida".
     *
     * @param callback ActionListener per gestionar els clics del botó nova partida
     */
    public void setOnNewGameCallback(ActionListener callback) {
        this.onNewGameCallback = callback;
    }

    /**
     * Inicialitza i configura el panell de botons amb els botons d'acció.
     * Crea botons per "Nova Partida", "Menú Principal" i "Sortir" amb els seus respectius gestors.
     */
    private void initializeButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));

        MenuButton newGameButton = new MenuButton("Nueva Partida");
        newGameButton.addActionListener(e -> {
            if (onNewGameCallback != null) {
                onNewGameCallback.actionPerformed(e);
            }
        });

        MenuButton menuButton = new MenuButton("Menú Principal");
        menuButton.addActionListener(e -> {
            if (onMenuCallback != null) {
                onMenuCallback.actionPerformed(e);
            }
        });

        MenuButton quitButton = new MenuButton("Salir");
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(newGameButton);
        buttonPanel.add(menuButton);
        buttonPanel.add(quitButton);

        add(buttonPanel);
    }

    /**
     * Mètode de pintat personalitzat que renderitza la interfície completa de la pantalla final.
     * Dibuixa el fons, títol i resultats amb antialiasing activat.
     *
     * @param g Context gràfic per dibuixar
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        super.paintComponent(g2);

        int width = getWidth();
        int height = getHeight();

        // Dibujar fondo dividido
        drawBackground(g2, width, height);

        // Dibujar título
        drawTitle(g2, width, height);

        // Dibujar podium/resultados
        drawResults(g2, width, height);

        g2.dispose();
    }

    /**
     * Dibuixa el fons dividit amb un panell lateral fosc i àrea principal amb degradat.
     *
     * @param g2 Context Graphics2D per dibuixar
     * @param width Amplada del component
     * @param height Alçada del component
     */
    private void drawBackground(Graphics2D g2, int width, int height) {
        // Panel lateral izquierdo (más oscuro)
        g2.setColor(new Color(0x2d, 0x2d, 0x2d));
        g2.fillRect(0, 0, (int) (width * SIDE_PANEL_WIDTH_PERCENT), height);

        // Panel principal derecho (azul degradado)
        GradientPaint gradient = new GradientPaint(
                (int) (width * SIDE_PANEL_WIDTH_PERCENT), 0, new Color(0x54, 0x63, 0xff),
                width, height, new Color(0x3a, 0x47, 0xcc)
        );
        g2.setPaint(gradient);
        g2.fillRect((int) (width * SIDE_PANEL_WIDTH_PERCENT), 0,
                (int) (width - width * SIDE_PANEL_WIDTH_PERCENT), height);
    }

    /**
     * Dibuixa el títol i l'anunci del guanyador a la part superior de la pantalla.
     *
     * @param g2 Context Graphics2D per dibuixar
     * @param width Amplada del component
     * @param height Alçada del component
     */
    private void drawTitle(Graphics2D g2, int width, int height) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, height / 12));
        FontMetrics fm = g2.getFontMetrics();

        String title = "¡Partida Terminada!";
        int titleX = width / 40;
        int titleY = height / 8;
        g2.drawString(title, titleX, titleY);

        // Subtítulo con el ganador
        if (sortedPlayers.length > 0) {
            g2.setFont(new Font("SansSerif", Font.PLAIN, height / 20));
            fm = g2.getFontMetrics();
            String winner = "Ganador: " + sortedPlayers[0].getName();
            int winnerY = titleY + fm.getHeight() + 10;
            g2.drawString(winner, titleX, winnerY);
        }
    }

    /**
     * Dibuixa la secció de resultats incloent tant el podi com la llista completa de jugadors.
     *
     * @param g2 Context Graphics2D per dibuixar
     * @param width Amplada del component
     * @param height Alçada del component
     */
    private void drawResults(Graphics2D g2, int width, int height) {
        if (sortedPlayers.length == 0) return;

        int resultsX = (int) (width * SIDE_PANEL_WIDTH_PERCENT) + 50;
        int resultsWidth = (int) (width * (1 - SIDE_PANEL_WIDTH_PERCENT)) - 100;
        int resultsY = height / 4;
        int resultsHeight = (int) (height * PODIUM_HEIGHT_PERCENT);

        // Dibujar podium para los 3 primeros
        drawPodium(g2, resultsX, resultsY, resultsWidth, resultsHeight);

        // Dibujar lista completa de jugadores
        drawPlayerList(g2, resultsX, resultsY + resultsHeight + 20, resultsWidth);
    }

    /**
     * Dibuixa la visualització del podi per als 3 primers jugadors.
     * El podi mostra les posicions 1a, 2a i 3a amb diferents alçades
     * i mostra noms de jugadors i puntuacions.
     *
     * @param g2 Context Graphics2D per dibuixar
     * @param x Coordenada X de l'àrea del podi
     * @param y Coordenada Y de l'àrea del podi
     * @param width Amplada de l'àrea del podi
     * @param height Alçada de l'àrea del podi
     */
    private void drawPodium(Graphics2D g2, int x, int y, int width, int height) {
        if (sortedPlayers.length == 0) return;

        int podiumWidth = width / 3;
        int baseHeight = height / 4;

        // Posiciones del podium (2do, 1ro, 3ro)
        int[] positions = {1, 0, 2}; // Índices en sortedPlayers
        int[] heights = {baseHeight * 2, baseHeight * 3, baseHeight}; // Alturas del podium
        String[] labels = {"2º", "1º", "3º"};

        for (int i = 0; i < 3 && i < sortedPlayers.length; i++) {
            int pos = positions[i];
            if (pos >= sortedPlayers.length) continue;

            Player player = sortedPlayers[pos];
            int podiumX = x + i * podiumWidth;
            int podiumHeight = heights[i];
            int podiumY = y + height - podiumHeight;

            // Dibujar bloque del podium
            setPlayerColor(g2, pos);
            g2.fillRoundRect(podiumX + 10, podiumY, podiumWidth - 20, podiumHeight, 20, 20);

            // Dibujar borde
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(podiumX + 10, podiumY, podiumWidth - 20, podiumHeight, 20, 20);

            // Dibujar posición
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 24));
            FontMetrics fm = g2.getFontMetrics();
            String label = labels[i];
            int labelX = podiumX + (podiumWidth - fm.stringWidth(label)) / 2;
            int labelY = podiumY + 30;
            g2.drawString(label, labelX, labelY);

            // Dibujar nombre del jugador
            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            fm = g2.getFontMetrics();
            String name = player.getName();
            if (fm.stringWidth(name) > podiumWidth - 20) {
                name = name.substring(0, Math.min(8, name.length())) + "...";
            }
            int nameX = podiumX + (podiumWidth - fm.stringWidth(name)) / 2;
            int nameY = labelY + 25;
            g2.drawString(name, nameX, nameY);

            // Dibujar puntuación
            g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
            fm = g2.getFontMetrics();
            String score = player.getScore() + " pts";
            int scoreX = podiumX + (podiumWidth - fm.stringWidth(score)) / 2;
            int scoreY = nameY + 20;
            g2.drawString(score, scoreX, scoreY);
        }
    }

    /**
     * Dibuixa la llista completa de classificació de tots els jugadors en format desplaçable.
     * Cada jugador es mostra en format targeta amb posició, nom i puntuació.
     *
     * @param g2 Context Graphics2D per dibuixar
     * @param x Coordenada X de l'àrea de la llista de jugadors
     * @param y Coordenada Y de l'àrea de la llista de jugadors
     * @param width Amplada de l'àrea de la llista de jugadors
     */
    private void drawPlayerList(Graphics2D g2, int x, int y, int width) {
        g2.setColor(new Color(255, 255, 255, 200));
        g2.fillRoundRect(x, y, width,
                Math.min(sortedPlayers.length * (PLAYER_CARD_HEIGHT + PLAYER_CARD_MARGIN), 200),
                15, 15);

        for (int i = 0; i < sortedPlayers.length; i++) {
            Player player = sortedPlayers[i];
            int cardY = y + 10 + i * (PLAYER_CARD_HEIGHT + PLAYER_CARD_MARGIN);

            if (cardY + PLAYER_CARD_HEIGHT > y + 190) break; // No exceder el área

            drawPlayerCard(g2, player, x + 10, cardY, width - 20, PLAYER_CARD_HEIGHT, i + 1);
        }
    }

    /**
     * Dibuixa una targeta individual de jugador mostrant el seu rang, nom, puntuació i indicador de CPU.
     * Cada targeta està codificada per colors basant-se en la posició del jugador.
     *
     * @param g2 Context Graphics2D per dibuixar
     * @param player Objecte Player que conté la informació del jugador
     * @param x Coordenada X de la targeta
     * @param y Coordenada Y de la targeta
     * @param width Amplada de la targeta
     * @param height Alçada de la targeta
     * @param position Posició de classificació del jugador (1r, 2n, etc.)
     */
    private void drawPlayerCard(Graphics2D g2, Player player, int x, int y, int width, int height, int position) {
        // Fondo de la carta
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, width, height, 10, 10);

        // Color del jugador (borde izquierdo)
        setPlayerColor(g2, position - 1);
        g2.fillRoundRect(x, y, 8, height, 10, 10);

        // Posición
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        FontMetrics fm = g2.getFontMetrics();
        String pos = position + "º";
        g2.drawString(pos, x + 20, y + height / 2 + fm.getAscent() / 2);

        // Nombre del jugador
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        fm = g2.getFontMetrics();
        String name = player.getName();
        g2.drawString(name, x + 70, y + height / 2);

        // Puntuación
        g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
        fm = g2.getFontMetrics();
        String score = player.getScore() + " puntos";
        int scoreX = x + width - fm.stringWidth(score) - 20;
        g2.drawString(score, scoreX, y + height / 2 + fm.getAscent() / 2);

        // Indicador de CPU si es necesario
        if (player.getCPU()) {
            g2.setColor(new Color(100, 100, 100));
            g2.setFont(new Font("SansSerif", Font.ITALIC, 12));
            g2.drawString("(CPU)", x + 70, y + height / 2 + 15);
        }
    }

    /**
     * Estableix el color gràfic basat en l'índex del jugador utilitzant un esquema de colors predefinit.
     * Els colors van rotant entre vermell, blau, groc i verd per fins a 4 jugadors.
     *
     * @param g Context gràfic on establir el color
     * @param playerIndex Índex del jugador (basat en 0)
     */
    private void setPlayerColor(Graphics g, int playerIndex) {
        switch (playerIndex % 4) {
            case 0 -> g.setColor(new Color(0xf5, 0x2e, 0x2e));
            case 1 -> g.setColor(new Color(0x54, 0x63, 0xff));
            case 2 -> g.setColor(new Color(0xff, 0xc7, 0x17));
            case 3 -> g.setColor(new Color(0x1f, 0x9e, 0x40));
        }
    }

    /**
     * Posiciona el panell de botons a la cantonada inferior esquerra de la pantalla.
     * Es crida automàticament durant les actualitzacions del layout.
     */
    @Override
    public void doLayout() {
        super.doLayout();

        int buttonPanelWidth = getWidth() / 6;
        int buttonPanelHeight = 150;
        int buttonPanelX = getWidth() / 40;
        int buttonPanelY = getHeight() - buttonPanelHeight - 40;

        buttonPanel.setBounds(buttonPanelX, buttonPanelY, buttonPanelWidth, buttonPanelHeight);
    }
}