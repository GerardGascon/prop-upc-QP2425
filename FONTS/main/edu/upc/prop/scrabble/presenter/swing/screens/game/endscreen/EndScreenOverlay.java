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

/**
 * Pantalla final del joc que mostra els resultats de la partida.
 * Aquesta classe crea una interfície visual amb un podi per als primers 3 jugadors,
 * una llista completa de classificació, animacions de confeti i botons d'acció.
 *
 * La pantalla està dividida en dues àrees principals:
 * - Panell lateral esquerre amb botons d'acció
 * - Àrea principal amb resultats i animacions
 *
 * @author Albert Usero
 */
public class EndScreenOverlay extends JPanel {
    /** Percentatge d'amplada de pantalla ocupat pel panell lateral */
    private final float SIDE_PANEL_WIDTH_PERCENT = 0.35f;

    /** Percentatge d'alçada de pantalla ocupat per l'àrea del podi */
    private final float PODIUM_HEIGHT_PERCENT = 0.4f;

    /** Alçada mínima en píxels de cada targeta de jugador */
    private final int MIN_PLAYER_CARD_HEIGHT = 40;

    /** Alçada màxima en píxels de cada targeta de jugador */
    private final int MAX_PLAYER_CARD_HEIGHT = 60;

    /** Marge mínim en píxels entre targetes de jugadors */
    private final int MIN_PLAYER_CARD_MARGIN = 4;

    /** Marge màxim en píxels entre targetes de jugadors */
    private final int MAX_PLAYER_CARD_MARGIN = 8;

    /** Array de jugadors de la partida ordenats per puntuació */
    private final Player[] players;

    /** Panell que conté els botons d'acció (Nova Partida, Menú Principal, Sortir) */
    private JPanel buttonPanel;

    /** Lista de partículas de confeti per a l'animació */
    private List<ConfettiParticle> confettiParticles;

    /** Timer per animar les partícules de confeti */
    private Timer confettiTimer;

    /** Generador de números aleatoris per al confeti */
    private Random random;

    /** Finestra principal de l'aplicació */
    private JFrame window;

    /** Propietats de la partida actual */
    private GameProperties gameProperties;

    /**
     * Constructor que crea una pantalla final amb els jugadors especificats.
     * Inicialitza la interfície, configura els botons d'acció i comença l'animació de confeti.
     *
     * @param sortedPlayers Array de jugadors ordenats per puntuació (primer element = guanyador)
     * @param window Finestra principal de l'aplicació
     * @param gameProperties Propietats de la partida actual
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
     * Inicialitza el sistema d'animació de confeti.
     * Crea 50 partícules de confeti i configura un timer per actualitzar-les
     * cada 50 mil·lisegons.
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
     * Actualitza la posició i estat de totes les partícules de confeti.
     * Les partícules que surten de la pantalla es reinicialitzen al centre del podi.
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
     * Crea botons per "Nueva Partida", "Menú Principal" i "Salir" amb els seus respectius gestors.
     *
     * Els botons permeten:
     * - Nova Partida: Iniciar una nova partida amb les mateixes configuracions
     * - Menú Principal: Tornar al menú principal
     * - Sortir: Tancar l'aplicació
     */
    private void putButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));

        MenuButton newGameButton = new MenuButton("Nova Partida");
        newGameButton.addActionListener(e -> {
            GameProperties properties = new GameProperties(gameProperties.language(),gameProperties.boardType(),gameProperties.players(), gameProperties.Cpus(), false);
            SceneManager.getInstance().load(GameScene.class, properties, window);
        });

        MenuButton menuButton = new MenuButton("Menú Principal");
        menuButton.addActionListener(e -> {
            SceneManager.getInstance().load(MenuScene.class, window);
        });

        MenuButton quitButton = new MenuButton("Sortir");
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(newGameButton);
        buttonPanel.add(menuButton);
        buttonPanel.add(quitButton);

        add(buttonPanel);
    }

    /**
     * Calcula les dimensions dinàmiques per a les targetes de jugadors.
     * Ajusta l'alçada de les targetes i els marges basant-se en l'espai disponible
     * i el nombre de jugadors per assegurar que tots es mostrin correctament.
     *
     * @param availableHeight Alçada disponible per a la llista de jugadors
     * @return Array amb dos elements: [alçada de targeta, marge entre targetes]
     */
    private int[] calculateCardDimensions(int availableHeight) {
        int numPlayers = players.length;

        // Calcular altura óptima de tarjeta y margen
        int totalMargins = (numPlayers - 1) * MAX_PLAYER_CARD_MARGIN + 20; // 20 para padding superior e inferior
        int availableForCards = availableHeight - totalMargins;
        int optimalCardHeight = availableForCards / numPlayers;

        // Limitar entre valores mínimos y máximos
        int cardHeight = Math.max(MIN_PLAYER_CARD_HEIGHT, Math.min(MAX_PLAYER_CARD_HEIGHT, optimalCardHeight));

        // Recalcular margen basándose en la altura de tarjeta final
        int usedHeightForCards = cardHeight * numPlayers;
        int remainingHeight = availableHeight - usedHeightForCards - 20; // 20 para padding
        int margin = Math.max(MIN_PLAYER_CARD_MARGIN, Math.min(MAX_PLAYER_CARD_MARGIN, remainingHeight / Math.max(1, numPlayers - 1)));

        return new int[]{cardHeight, margin};
    }

    /**
     * Mètode de pintat personalitzat que renderitza la interfície completa de la pantalla final.
     * Dibuixa el fons dividit, el títol amb el guanyador, els resultats amb podi i
     * les animacions de confeti. Utilitza antialiasing per millorar la qualitat visual.
     *
     * @param g Context gràfic per dibuixar els elements visuals
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
     * Dibuixa les partícules de confeti amb rotació i colors variats.
     * Cada partícula es renderitza com un quadrat petit amb el seu color i rotació específics.
     *
     * @param g2 Context Graphics2D per dibuixar les partícules
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
     * El panell lateral (esquerre) té un color fosc uniforme mentre que l'àrea principal
     * (dreta) presenta un degradat blau que va des de blau clar a blau fosc.
     *
     * @param g2 Context Graphics2D per dibuixar el fons
     * @param width Amplada total del component
     * @param height Alçada total del component
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
     * Mostra "Partida acabada!" com a títol principal i "Guanyador: [nom]" com a subtítol.
     * La mida de la font s'ajusta proporcionalment a l'alçada de la pantalla.
     *
     * @param g2 Context Graphics2D per dibuixar el text
     * @param width Amplada total del component
     * @param height Alçada total del component
     */
    private void drawTitle(Graphics2D g2, int width, int height) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, height / 20));
        FontMetrics fm = g2.getFontMetrics();

        String title = "Partida acabada!";
        int titleX = width / 40;
        int titleY = height / 12;
        g2.drawString(title, titleX, titleY);

        g2.setFont(new Font("SansSerif", Font.PLAIN, height / 20));
        fm = g2.getFontMetrics();
        String winner = "Guanyador: " + players[0].getName();
        int winnerY = titleY + fm.getHeight() + 5;
        g2.drawString(winner, titleX, winnerY);
    }

    /**
     * Dibuixa la secció de resultats incloent tant el podi com la llista completa de jugadors.
     * Organitza l'espai disponible distribuint-lo entre el podi (40% de l'alçada) i
     * la llista de jugadors (espai restant).
     *
     * @param g2 Context Graphics2D per dibuixar els resultats
     * @param width Amplada total del component
     * @param height Alçada total del component
     */
    private void drawResults(Graphics2D g2, int width, int height) {
        int resultsX = (int) (width * SIDE_PANEL_WIDTH_PERCENT) + 30;
        int resultsWidth = (int) (width * (1 - SIDE_PANEL_WIDTH_PERCENT)) - 60;
        int resultsY = height / 6;
        int podiumHeight = (int) (height * PODIUM_HEIGHT_PERCENT);

        // Dibujar podium para los 3 primeros
        drawPodium(g2, resultsX, resultsY, resultsWidth, podiumHeight);

        // Calcular espacio disponible para la lista
        int listStartY = resultsY + podiumHeight + 60;
        int availableHeight = height - listStartY - 60;

        // Dibujar lista completa de jugadores (sin limitación de altura)
        drawPlayerList(g2, resultsX, listStartY, resultsWidth, availableHeight);
    }

    /**
     * Dibuixa la visualització del podi per als 3 primers jugadors.
     * El podi mostra les posicions 1a, 2a i 3a amb diferents alçades (1r més alt, 2n mitjà, 3r més baix).
     * Cada posició mostra el nom del jugador, la seva puntuació i un número de posició.
     * Els colors dels blocs del podi corresponen als colors assignats a cada jugador.
     *
     * @param g2 Context Graphics2D per dibuixar el podi
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
            g2.setFont(new Font("SansSerif", Font.BOLD, 44));
            FontMetrics fm = g2.getFontMetrics();
            String label = labels[i];
            int labelX = podiumX + (podiumWidth - fm.stringWidth(label)) / 2;
            int labelY = podiumY + 36;
            g2.drawString(label, labelX, labelY);

            // Dibujar puntuación dentro del podio (más grande)
            g2.setFont(new Font("SansSerif", Font.PLAIN, 28));
            fm = g2.getFontMetrics();
            String score = player.getScore() + " pts";
            int scoreX = podiumX + (podiumWidth - fm.stringWidth(score)) / 2;
            int scoreY = podiumY + podiumHeight - 15;
            g2.drawString(score, scoreX, scoreY);

            // Dibujar nombre del jugador
            g2.setFont(new Font("SansSerif", Font.BOLD, 40));
            fm = g2.getFontMetrics();
            String name = player.getName();
            if (fm.stringWidth(name) > podiumWidth - 20) {
                name = name.substring(0, Math.min(8, name.length())) + "...";
            }
            int nameX = podiumX + (podiumWidth - fm.stringWidth(name)) / 2;
            int nameY = podiumY - 10;
            g2.drawString(name, nameX, nameY);
        }
    }

    /**
     * Dibuixa la llista completa de classificació de tots els jugadors.
     * Cada jugador es mostra en format targeta amb posició, nom i puntuació.
     * Les targetes s'ajusten dinàmicament per mostrar tots els jugadors sense necessitat de scroll.
     * La llista té un fons semi-transparent amb vores arrodonides.
     *
     * @param g2 Context Graphics2D per dibuixar la llista
     * @param x Coordenada X de l'àrea de la llista de jugadors
     * @param y Coordenada Y de l'àrea de la llista de jugadors
     * @param width Amplada de l'àrea de la llista de jugadors
     * @param availableHeight Altura disponible per a la llista completa
     */
    private void drawPlayerList(Graphics2D g2, int x, int y, int width, int availableHeight) {
        if (players.length == 0) return;

        // Calcular dimensiones dinámicas
        int[] dimensions = calculateCardDimensions(availableHeight);
        int cardHeight = dimensions[0];
        int cardMargin = dimensions[1];

        // Calcular altura total necesaria
        int totalHeight = players.length * cardHeight + (players.length - 1) * cardMargin + 20;

        // Dibujar fondo de la lista
        g2.setColor(new Color(255, 255, 255, 220));
        g2.fillRoundRect(x, y, width, totalHeight, 12, 12);

        // Dibujar borde de la lista
        g2.setColor(new Color(200, 200, 200));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(x, y, width, totalHeight, 12, 12);

        // Dibujar cada carta de jugador
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            int cardY = y + 10 + i * (cardHeight + cardMargin);
            drawPlayerCard(g2, player, x + 10, cardY, width - 20, cardHeight, i + 1);
        }
    }

    /**
     * Dibuixa una targeta individual de jugador mostrant el seu rang, nom i puntuació.
     * Cada targeta està codificada per colors basant-se en la posició del jugador.
     * El tamany del text s'ajusta dinàmicament segons l'alçada de la targeta per
     * mantenir la llegibilitat independentment del nombre de jugadors.
     *
     * @param g2 Context Graphics2D per dibuixar la targeta
     * @param player Objecte Player que conté la informació del jugador
     * @param x Coordenada X de la targeta
     * @param y Coordenada Y de la targeta
     * @param width Amplada de la targeta
     * @param height Alçada de la targeta
     * @param position Posició de classificació del jugador (1r, 2n, 3r, etc.)
     */
    private void drawPlayerCard(Graphics2D g2, Player player, int x, int y, int width, int height, int position) {
        // Fondo de la carta
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, width, height, 8, 8);

        // Color del jugador (borde izquierdo)
        setPlayerColor(g2, position - 1);
        g2.fillRoundRect(x, y, 6, height, 8, 8);

        // Calcular tamaño de fuente basado en la altura de la tarjeta
        int baseFontSize = Math.max(12, Math.min(25, height / 2));
        int smallFontSize = Math.max(10, baseFontSize - 3);

        // Posición
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, baseFontSize));
        FontMetrics fm = g2.getFontMetrics();
        String pos = position + "º";
        g2.drawString(pos, x + 15, y + height / 2 + fm.getAscent() / 2);

        // Nombre del jugador
        g2.setFont(new Font("SansSerif", Font.BOLD, baseFontSize));
        fm = g2.getFontMetrics();
        String name = player.getName();
        // Truncar nombre si es muy largo
        if (fm.stringWidth(name) > width / 2) {
            while (fm.stringWidth(name + "...") > width / 2 && name.length() > 3) {
                name = name.substring(0, name.length() - 1);
            }
            name += "...";
        }
        g2.drawString(name, x + 60, y + height / 2 + fm.getAscent() / 2);

        // Puntuación
        g2.setFont(new Font("SansSerif", Font.PLAIN, baseFontSize));
        fm = g2.getFontMetrics();
        String score = player.getScore() + " pts";
        int scoreX = x + width - fm.stringWidth(score) - 15;
        g2.drawString(score, scoreX, y + height / 2 + fm.getAscent() / 2);
    }

    /**
     * Estableix el color gràfic basat en l'índex del jugador utilitzant un esquema de colors predefinit.
     * Els colors assignats són:
     * - Jugador 1: Vermell (#f52e2e)
     * - Jugador 2: Blau (#5463ff)
     * - Jugador 3: Groc (#ffc717)
     * - Jugador 4: Verd (#1f9e40)
     *
     * Els colors es repeteixen cíclicament per a més de 4 jugadors.
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
     * El panell s'ajusta automàticament segons les dimensions de la finestra.
     * Es crida automàticament durant les actualitzacions del layout del component.
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
     * Classe interna que representa una partícula de confeti per a l'animació.
     * Cada partícula té propietats de posició, velocitat, rotació i aparença visual.
     * Les partícules cauen per gravetat i es mouen amb efectes de resistència de l'aire.
     *
     * Les partícules es reinicialitzen automàticament quan surten de la pantalla,
     * creant un efecte continu de confeti que cau des del centre del podi.
     */
    private class ConfettiParticle {
        /** Posició X actual de la partícula */
        float x, y;

        /** Velocitat horitzontal i vertical de la partícula */
        float velocityX, velocityY;

        /** Angle de rotació actual de la partícula */
        float rotation;

        /** Velocitat de rotació de la partícula */
        float rotationSpeed;

        /** Color de la partícula */
        Color color;

        /** Mida de la partícula en píxels */
        int size;

        /**
         * Constructor que inicialitza una nova partícula de confeti.
         * Estableix valors aleatoris per a posició, velocitat, rotació i aparença.
         */
        public ConfettiParticle() {
            reset(getWidth() / 2, getWidth(), getHeight());
        }

        /**
         * Reinicialitza la partícula amb nous valors aleatoris.
         * Posiciona la partícula prop del centre del podi amb velocitat i propietats aleatòries.
         *
         * @param centerX Coordenada X del centre on apareixerà la partícula
         * @param screenWidth Amplada de la pantalla per als càlculs de límits
         * @param screenHeight Alçada de la pantalla per als càlculs de límits
         */
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

        /**
         * Actualitza la posició i estat de la partícula per al següent frame d'animació.
         * Aplica gravetat, resistència de l'aire i rotació per crear un moviment realista.
         */
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