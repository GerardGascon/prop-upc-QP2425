package edu.upc.prop.scrabble.presenter.swing.screens.game.endscreen;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.properties.GameProperties;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.swing.scenes.GameScene;
import edu.upc.prop.scrabble.presenter.swing.scenes.MenuScene;
import edu.upc.prop.scrabble.presenter.swing.screens.menu.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.awt.SystemColor.window;

public class EndScreenOverlay extends JPanel {
    /** Percentatge d'amplada de pantalla ocupat pel panell lateral */
    private final float SIDE_PANEL_WIDTH_PERCENT = 0.35f;

    /** Percentatge d'alçada de pantalla ocupat per l'àrea del podi */
    private final float PODIUM_HEIGHT_PERCENT = 0.4f;

    /** Alçada en píxels de cada targeta de jugador a la llista de classificació */
    private final int PLAYER_CARD_HEIGHT = 60; // Aumentado de 50 a 60

    /** Marge en píxels entre targetes de jugadors */
    private final int PLAYER_CARD_MARGIN = 8; // Aumentado de 6 a 8

    /** Array de jugadors de la partida */
    private final Player[] players;

    /** Panell que conté els botons d'acció */
    private JPanel buttonPanel;

    /** Lista de partículas de confeti */
    private List<ConfettiParticle> confettiParticles;

    /** Timer para animación de confeti */
    private Timer confettiTimer;

    /** Random para generar confeti */
    private Random random;

    private JFrame window;

    private GameProperties gameProperties;
    /**
     * Constructor que crea un EndScreen amb els jugadors especificats.
     *
     * @param sortedPlayers Array de jugadors per mostrar a la pantalla final
     */
    public EndScreenOverlay(Player[] sortedPlayers, JFrame window, GameProperties gameProperties) {
        setLayout(null);
        setBackground(new Color(0x2d, 0x2d, 0x2d));
        this.players = sortedPlayers;
        this.confettiParticles = new ArrayList<>();
        this.random = new Random();
        this.window = window;
        this.gameProperties = gameProperties;

        putButtons();
        initConfetti();
        System.out.println("end screen overlay");
        addMouseWheelListener(InputEvent::consume);
    }

    /**
     * Inicializa el sistema de confeti
     */
    private void initConfetti() {
        // Crear partículas de confeti
        for (int i = 0; i < 50; i++) {
            confettiParticles.add(new ConfettiParticle());
        }

        // Timer para animar el confeti
        confettiTimer = new Timer(50, e -> {
            updateConfetti();
            repaint();
        });
        confettiTimer.start();
    }

    /**
     * Actualiza las partículas de confeti
     */
    private void updateConfetti() {
        int width = getWidth();
        int height = getHeight();
        int podiumCenterX = (int) (width * SIDE_PANEL_WIDTH_PERCENT) + (int) (width * (1 - SIDE_PANEL_WIDTH_PERCENT)) / 2;

        for (ConfettiParticle particle : confettiParticles) {
            particle.update();

            // Reiniciar partícula si sale de la pantalla
            if (particle.y > height || particle.x < 0 || particle.x > width) {
                particle.reset(podiumCenterX, width, height);
            }
        }
    }

    /**
     * Inicialitza i configura el panell de botons amb els botons d'acció.
     * Crea botons per "Nova Partida", "Menú Principal" i "Sortir" amb els seus respectius gestors.
     */
    private void putButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));

        MenuButton newGameButton = new MenuButton("Nueva Partida");
        newGameButton.addActionListener(e -> {
            GameProperties properties = new GameProperties(gameProperties.language(),gameProperties.boardType(),gameProperties.players(), gameProperties.Cpus(), false);
            SceneManager.getInstance().load(GameScene.class, properties, window);
        });

        MenuButton menuButton = new MenuButton("Menú Principal");
        menuButton.addActionListener(e -> {
            SceneManager.getInstance().load(MenuScene.class, window);
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

        // Dibujar confeti
        drawConfetti(g2);

        g2.dispose();
    }

    /**
     * Dibuja las partículas de confeti
     */
    private void drawConfetti(Graphics2D g2) {
        for (ConfettiParticle particle : confettiParticles) {
            g2.setColor(particle.color);
            g2.rotate(particle.rotation, particle.x, particle.y);
            g2.fillRect((int)particle.x, (int)particle.y, particle.size, particle.size);
            g2.rotate(-particle.rotation, particle.x, particle.y);
        }
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
        g2.setFont(new Font("SansSerif", Font.BOLD, height / 15));
        FontMetrics fm = g2.getFontMetrics();

        String title = "¡Partida Terminada!";
        int titleX = width / 40;
        int titleY = height / 12;
        g2.drawString(title, titleX, titleY);

        g2.setFont(new Font("SansSerif", Font.PLAIN, height / 25));
        fm = g2.getFontMetrics();
        String winner = "Ganador: " + players[0].getName();
        int winnerY = titleY + fm.getHeight() + 5;
        g2.drawString(winner, titleX, winnerY);
    }

    /**
     * Dibuixa la secció de resultats incloent tant el podi com la llista completa de jugadors.
     *
     * @param g2 Context Graphics2D per dibuixar
     * @param width Amplada del component
     * @param height Alçada del component
     */
    private void drawResults(Graphics2D g2, int width, int height) {
        int resultsX = (int) (width * SIDE_PANEL_WIDTH_PERCENT) + 30;
        int resultsWidth = (int) (width * (1 - SIDE_PANEL_WIDTH_PERCENT)) - 60;
        int resultsY = height / 6;
        int podiumHeight = (int) (height * PODIUM_HEIGHT_PERCENT);

        // Dibujar podium para los 3 primeros
        drawPodium(g2, resultsX, resultsY, resultsWidth, podiumHeight);

        // Calcular espacio disponible para la lista
        int listStartY = resultsY + podiumHeight + 60; // Más espacio para los nombres encima del podio
        int availableHeight = height - listStartY - 60;

        // Dibujar lista completa de jugadores
        drawPlayerList(g2, resultsX, listStartY, resultsWidth, availableHeight);
    }

    /**
     * Dibuixa la visualització del podi per als 3 primers jugadors.
     * El podi mostra les posicions 1a, 2a i 3a amb diferents alçades
     * i mostra noms de jugadors i puntuacions encima dels bloques.
     *
     * @param g2 Context Graphics2D per dibuixar
     * @param x Coordenada X de l'àrea del podi
     * @param y Coordenada Y de l'àrea del podi
     * @param width Amplada de l'àrea del podi
     * @param height Alçada de l'àrea del podi
     */
    private void drawPodium(Graphics2D g2, int x, int y, int width, int height) {
        int podiumWidth = width / 3;
        int baseHeight = height / 4;

        // Posiciones del podium (2do, 1ro, 3ro)
        int[] positions = {1, 0, 2}; // Índices en sortedPlayers
        int[] heights = {baseHeight * 2, baseHeight * 3, baseHeight}; // Alturas del podium
        String[] labels = {"2º", "1º", "3º"};

        for (int i = 0; i < 3 && i < players.length; i++) {
            int pos = positions[i];
            if (pos >= players.length)
                continue;

            Player player = players[pos];
            int podiumX = x + i * podiumWidth;
            int podiumHeight = heights[i];
            int podiumY = y + height - podiumHeight;

            // Dibujar bloque del podium
            setPlayerColor(g2, pos);
            g2.fillRoundRect(podiumX + 10, podiumY, podiumWidth - 20, podiumHeight, 15, 15);

            // Dibujar borde
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(podiumX + 10, podiumY, podiumWidth - 20, podiumHeight, 15, 15);

            // Dibujar posición (más grande)
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 40)); // Aumentado de 32 a 40
            FontMetrics fm = g2.getFontMetrics();
            String label = labels[i];
            int labelX = podiumX + (podiumWidth - fm.stringWidth(label)) / 2;
            int labelY = podiumY + 30; // Ajustado para el tamaño mayor
            g2.drawString(label, labelX, labelY);

            // Dibujar puntuación dentro del podio (más grande)
            g2.setFont(new Font("SansSerif", Font.PLAIN, 24)); // Aumentado de 20 a 24
            fm = g2.getFontMetrics();
            String score = player.getScore() + " pts";
            int scoreX = podiumX + (podiumWidth - fm.stringWidth(score)) / 2;
            int scoreY = podiumY + podiumHeight - 15; // En la parte inferior del podio
            g2.drawString(score, scoreX, scoreY);

            // Dibujar nombre del jugador ENCIMA del podio
            g2.setFont(new Font("SansSerif", Font.BOLD, 20)); // Aumentado de 24 a 20 para que quepa bien encima
            fm = g2.getFontMetrics();
            String name = player.getName();
            if (fm.stringWidth(name) > podiumWidth - 20) {
                name = name.substring(0, Math.min(8, name.length())) + "...";
            }
            int nameX = podiumX + (podiumWidth - fm.stringWidth(name)) / 2;
            int nameY = podiumY - 10; // ENCIMA del podio (negativo para subir)
            g2.drawString(name, nameX, nameY);
        }
    }

    /**
     * Dibuixa la llista completa de classificació de tots els jugadors.
     * Cada jugador es mostra en format targeta amb posició, nom i puntuació.
     *
     * @param g2 Context Graphics2D per dibuixar
     * @param x Coordenada X de l'àrea de la llista de jugadors
     * @param y Coordenada Y de l'àrea de la llista de jugadors
     * @param width Amplada de l'àrea de la llista de jugadors
     * @param maxHeight Altura máxima disponible para la lista
     */
    private void drawPlayerList(Graphics2D g2, int x, int y, int width, int maxHeight) {
        // Calcular altura necesaria para todos los jugadores
        int cardTotalHeight = PLAYER_CARD_HEIGHT + PLAYER_CARD_MARGIN;
        int totalNeededHeight = players.length * cardTotalHeight + 20;
        int listHeight = Math.min(maxHeight, totalNeededHeight);

        // Dibujar fondo de la lista
        g2.setColor(new Color(255, 255, 255, 220));
        g2.fillRoundRect(x, y, width, listHeight, 12, 12);

        // Dibujar borde de la lista
        g2.setColor(new Color(200, 200, 200));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(x, y, width, listHeight, 12, 12);

        // Dibujar cada carta de jugador
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            int cardY = y + 10 + i * cardTotalHeight;

            // Solo dibujar si la carta está dentro del área visible
            if (cardY + PLAYER_CARD_HEIGHT <= y + listHeight) {
                drawPlayerCard(g2, player, x + 10, cardY, width - 20, PLAYER_CARD_HEIGHT, i + 1);
            }
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
        g2.fillRoundRect(x, y, width, height, 8, 8);

        // Color del jugador (borde izquierdo)
        setPlayerColor(g2, position - 1);
        g2.fillRoundRect(x, y, 6, height, 8, 8);

        // Posición (más grande)
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 22)); // Aumentado de 18 a 22
        FontMetrics fm = g2.getFontMetrics();
        String pos = position + "º";
        g2.drawString(pos, x + 15, y + height / 2 + fm.getAscent() / 2);

        // Nombre del jugador (más grande)
        g2.setFont(new Font("SansSerif", Font.BOLD, 20)); // Aumentado de 16 a 20
        fm = g2.getFontMetrics();
        String name = player.getName();
        // Truncar nombre si es muy largo
        if (fm.stringWidth(name) > width / 2) {
            while (fm.stringWidth(name + "...") > width / 2 && name.length() > 3) {
                name = name.substring(0, name.length() - 1);
            }
            name += "...";
        }
        g2.drawString(name, x + 60, y + height / 2); // Ajustado X para el texto más grande

        // Puntuación (más grande)
        g2.setFont(new Font("SansSerif", Font.PLAIN, 18)); // Aumentado de 16 a 18
        fm = g2.getFontMetrics();
        String score = player.getScore() + " pts";
        int scoreX = x + width - fm.stringWidth(score) - 15;
        g2.drawString(score, scoreX, y + height / 2 + fm.getAscent() / 2);

        // Indicador de CPU si es necesario (más grande)
        if (player.getCPU()) {
            g2.setColor(new Color(120, 120, 120));
            g2.setFont(new Font("SansSerif", Font.ITALIC, 14)); // Aumentado de 12 a 14
            g2.drawString("(CPU)", x + 60, y + height / 2 + 15); // Ajustado para el texto más grande
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

    /**
     * Clase interna para representar una partícula de confeti
     */
    private class ConfettiParticle {
        float x, y;
        float velocityX, velocityY;
        float rotation;
        float rotationSpeed;
        Color color;
        int size;

        public ConfettiParticle() {
            reset(getWidth() / 2, getWidth(), getHeight());
        }

        public void reset(int centerX, int screenWidth, int screenHeight) {
            // Posición inicial cerca del centro del podio
            x = centerX + random.nextInt(200) - 100;
            y = -random.nextInt(50);

            // Velocidad inicial
            velocityX = (random.nextFloat() - 0.5f) * 4;
            velocityY = random.nextFloat() * 2 + 1;

            // Rotación
            rotation = random.nextFloat() * (float) Math.PI * 2;
            rotationSpeed = (random.nextFloat() - 0.5f) * 0.2f;

            // Color aleatorio brillante
            Color[] colors = {
                    new Color(255, 0, 100),   // Rosa
                    new Color(0, 150, 255),   // Azul
                    new Color(255, 200, 0),   // Dorado
                    new Color(0, 255, 150),   // Verde
                    new Color(255, 100, 0),   // Naranja
                    new Color(200, 0, 255)    // Morado
            };
            color = colors[random.nextInt(colors.length)];

            // Tamaño
            size = random.nextInt(8) + 4;
        }

        public void update() {
            x += velocityX;
            y += velocityY;

            // Aplicar gravedad
            velocityY += 0.1f;

            // Rotación
            rotation += rotationSpeed;

            // Resistencia del aire
            velocityX *= 0.99f;
        }
    }
}