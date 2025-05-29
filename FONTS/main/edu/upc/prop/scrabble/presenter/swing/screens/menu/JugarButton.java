package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.BoardType;
import edu.upc.prop.scrabble.data.properties.GameProperties;
import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.swing.scenes.GameScene;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * Botó encarregat de gestionar el menú de jugar i les seves funcionalitats associades,
 * com carregar seleccionar quants jugadors hi hauran, de quin tipus, els seus noms, el llenguatge de la partida i la mida del tauler
 *
 * @author Biel Pérez Silvestre
 */
public class JugarButton extends MenuButton {
    /**
     * Panell principal sobre el qual es mostren els menús desplegables del botó.
     */
    private final JPanel parentPanel;
    /**
     * Indica si el menú desplegable del botó "Jugar" està actualment actiu o no.
     */
    private boolean menuActive = false;
    /**
     * Panell de configuració de la partida amb fons degradat,
     * títol, etiquetes i separadors visuals,
     * on es col·loquen les opcions per a jugadors, llenguatge i mida del tauler.
     */
    private JPanel settingsPanel;
    private final JFrame window;

    /**
     * ComboBox que permet seleccionar la mida del tauler
     */
    private JComboBox<BoardType> tableSizeComboBox;

    /**
     * ComboBox que permet seleccionar el llenguatge de la partida
     */
    private JComboBox<Language> languageComboBox;
    /**
     * ComboBox que permet seleccionar el Tipus del jugador 1
     */
    private JComboBox<String> player1Type;
    /**
     * ComboBox que permet seleccionar el Tipus del jugador 2
     */
    private JComboBox<String> player2Type;
    /**
     * ComboBox que permet seleccionar el Tipus del jugador 3
     */
    private JComboBox<String> player3Type;
    /**
     * ComboBox que permet seleccionar el Tipus del jugador 4
     */
    private JComboBox<String> player4Type;

    /**
     * Textfield que permet inserir el nom del jugador 1
     */
    private JTextField player1Name;
    /**
     * Textfield que permet inserir el nom del jugador 2
     */
    private JTextField player2Name;
    /**
     * Textfield que permet inserir el nom del jugador 3
     */
    private JTextField player3Name;
    /**
     * Textfield que permet inserir el nom del jugador 4
     */
    private JTextField player4Name;

    /**
     * String per a guardar el tipus del player 1
     */
    private String p1Type;

    /**
     * String per a guardar el tipus del player 2
     */
    private String p2Type;

    /**
     * String per a guardar el tipus del player 3
     */
    private String p3Type;

    /**
     * String per a guardar el tipus del jugador 4
     */
    private String p4Type;

    /**
     * String per a guardar el nom del jugador 1
     */
    private String p1Name;
    /**
     * String per a guardar el nom del jugador 2
     */
    private String p2Name;
    /**
     * String per a guardar el nom del jugador 3
     */
    private String p3Name;
    /**
     * String per a guardar el nom del jugador 4
     */
    private String p4Name = "NOCHECKED";

    /**
     * Array que guarda els noms dels jugadors
     */
    private String[] names = new String[]{"p1", "p2", "p3", "p4"};

    /**
     * Array que guarda el tipos dels jugador
     */
    private String[] types = new String[]{"NO", "NO", "NO", "NO"};

    /**
     * String per a guardar el llenguatge de la partida
     */
    private Language selectedLanguage = Language.English;
    /**
     * String per a guardar la mida del tauler
     */
    private BoardType selectedTableSize = BoardType.Standard;

    /**
     * String per a guardar el numero de jugadors que no son CPU
     */
    private int selectedRealPlayers = 2;
    /**
     * String per a guardar el numero de jugadors que son CPU
     */
    private int selectedCPUPlayers = 0;


    /**
     * Creador del botó "Jugar", que desplega el menú per configurar i carregar una partida
     *
     * @param parent El panell principal sobre el qual es mostrarà el menú.
     */
    public JugarButton(JPanel parent, JFrame window) {
        super("Jugar");
        this.parentPanel = parent;
        this.window = window;
        addActionListener(_ -> {
            if (otherButtons != null) {
                for (MenuButton b : otherButtons) {
                       if (b != null) b.Close();
                }
            }
            toggleSettingsPanel();
        });
    }

    /**
     * Tanca el panell desplegat pel botó "Continuar", si està actiu.
     * Elimina el panell de configuració del contenidor i actualitza la interfície.
     */
    @Override
    public void Close() {
        if (!menuActive) return;

        Container container = parentPanel.getParent();
        container.remove(settingsPanel);
        menuActive = false;
        container.revalidate();
        container.repaint();
    }

    /**
     * Mostra o amaga el panell de configuració de la partida associat al botó "Jugqr".
     * Si el panell ja està actiu, s’elimina de la interfície i es refresca l’escena.
     * Si no està actiu, es crea un nou panell amb disseny personalitzat (fons degradat, títol i seccions de configuració),
     * es posiciona al costat dret i es fusiona amb el panell principal.
     */
    public void toggleSettingsPanel() {

// Retornar al mode original
        if (menuActive) {
            Container container = parentPanel.getParent();
            container.remove(settingsPanel);
            menuActive = false;
            container.revalidate();
            container.repaint();
            return;
        }
// Guarda menu original
        menuActive = true;
        Container menuMockPanel = parentPanel.getParent();
// Panel de settings
        settingsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                // Fill
                GradientPaint gradient = new GradientPaint(
                        (float) (width * 0.4), (float) (height * 0.15),
                        new Color(240, 240, 250),
                        (float) (width * 0.8), (float) (height * 0.95), new Color(210, 210, 230)
                );

                g2d.setPaint(gradient);
                g2d.fillRoundRect((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.9), (int) (height * 0.9), 20, 20);

// Títol
                g2d.setFont(new Font("SansSerif", Font.BOLD, 30));
                g2d.setColor(new Color(60, 60, 80));
                String title = "Game Settings";
                FontMetrics fm = g2d.getFontMetrics();
                int titleWidth = fm.stringWidth(title);
                g2d.drawString(title, (int) (width * 0.4), (int) (height * 0.15));

// Linea
                // Linea títol
                g2d.setColor(new Color(180, 180, 200));
                g2d.drawLine((int) (width * 0.1), (int) (height * 0.2), (int) (width * 0.9), (int) (height * 0.2));

// Settings títols
                g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
                g2d.setColor(new Color(80, 80, 100));

                g2d.drawString("Player 1", (int) (width * 0.2), (int) (height * 0.3));
                g2d.drawString("Player 2", (int) (width * 0.4), (int) (height * 0.3));
                g2d.drawString("Player 3", (int) (width * 0.6), (int) (height * 0.3));
                g2d.drawString("Player 4", (int) (width * 0.8), (int) (height * 0.3));

                g2d.drawString("Type: ", (int) (width * 0.1), (int) (height * 0.4));
                g2d.drawString("Name: ", (int) (width * 0.1), (int) (height * 0.5));

                g2d.drawString("Language", (int) (width * 0.1), (int) (height * 0.6));
                g2d.drawString("Table Size", (int) (width * 0.1), (int) (height * 0.7));

                g2d.dispose();
            }
        };

        settingsPanel.setLayout(null);
        settingsPanel.setOpaque(false);

// SetBounds
        menuMockPanel.setLayout(null);
        int menuWidth = (int) (menuMockPanel.getWidth() * 0.6);
        settingsPanel.setBounds(
                (int) (menuMockPanel.getWidth() * 0.4),
                0,
                menuWidth,
                menuMockPanel.getHeight()
        );

// Afegir bottons d'opció multiple
        addSettingsComponents(settingsPanel.getWidth(), settingsPanel.getHeight());
// Fusionar amb l'escena original
        menuMockPanel.add(settingsPanel);

        // Refresh
        menuMockPanel.revalidate();
        menuMockPanel.repaint();
    }

    /**
     * Afegeix els components al panell de configuració: un botó per carregar una partida i un altre per cancel·lar.
     *
     * @param width  L’amplada del panell de configuració.
     * @param height L’alçada del panell de configuració.
     */
    private void addSettingsComponents(int width, int height) {


// Real Players ComboBox
        String[] playerTypes = {"Real", "CPU", "___"};

        int componentWidth = (int) (width * 0.1);
        int componentHeight = (int) (height * 0.05);

        player1Type = new JComboBox<>(playerTypes);

        player1Type.setSelectedItem(selectedRealPlayers);

        player1Type.setBounds((int) (width * 0.2), (int) (height * 0.365), componentWidth, componentHeight);

        player1Type.addActionListener(e -> {
            player1Name.setEnabled(Objects.equals(player1Type.getSelectedItem(), playerTypes[0]));
            }
        );

        settingsPanel.add(player1Type);

        player2Type = new JComboBox<>(playerTypes);

        player2Type.setSelectedItem(selectedRealPlayers);

        player2Type.setBounds((int) (width * 0.4), (int) (height * 0.365), componentWidth, componentHeight);

        player2Type.addActionListener(e -> {
                    player2Name.setEnabled(Objects.equals(player2Type.getSelectedItem(), playerTypes[0]));
                }
        );
        settingsPanel.add(player2Type);


        player3Type = new JComboBox<>(playerTypes);

        player3Type.setSelectedItem(selectedRealPlayers);

        player3Type.setBounds((int) (width * 0.6), (int) (height * 0.365), componentWidth, componentHeight);

        player3Type.addActionListener(e -> {
                    player3Name.setEnabled(Objects.equals(player3Type.getSelectedItem(), playerTypes[0]));
                }
        );
        settingsPanel.add(player3Type);


        player4Type = new JComboBox<>(playerTypes);

        player4Type.setSelectedItem(selectedRealPlayers);

        player4Type.setBounds((int) (width * 0.8), (int) (height * 0.365), componentWidth, componentHeight);

        player4Type.addActionListener(e -> {
                    player4Name.setEnabled(Objects.equals(player4Type.getSelectedItem(), playerTypes[0]));
                }
        );
        settingsPanel.add(player4Type);


        player1Name = new JTextField();

        player1Name.setEditable(true);

        player1Name.setBounds((int) (width * 0.2), (int) (height * 0.47), componentWidth, componentHeight);

        player1Name.setColumns(10);

        player1Name.addActionListener(e -> p1Name = (String) player1Name.getText());

        settingsPanel.add(player1Name);


        player2Name = new JTextField();

        player2Name.setEditable(true);

        player2Name.setBounds((int) (width * 0.4), (int) (height * 0.47), componentWidth, componentHeight);

        player2Name.setColumns(10);

        settingsPanel.add(player2Name);


        player3Name = new JTextField();

        player3Name.setEditable(true);

        player3Name.setBounds((int) (width * 0.6), (int) (height * 0.47), componentWidth, componentHeight);

        player3Name.setColumns(10);

        settingsPanel.add(player3Name);


        player4Name = new JTextField();

        player4Name.setEditable(true);

        player4Name.setBounds((int) (width * 0.8), (int) (height * 0.47), componentWidth, componentHeight);

        player4Name.setColumns(10);

        settingsPanel.add(player4Name);


// Language ComboBox
        languageComboBox = new JComboBox<>(Language.values());

        languageComboBox.setSelectedItem(selectedLanguage);

        languageComboBox.setBounds((int) (width * 0.225), (int) (height * 0.575), (int) (width * 0.4), componentHeight);

        languageComboBox.addActionListener(e -> selectedLanguage = (Language) languageComboBox.getSelectedItem());

        settingsPanel.add(languageComboBox);


// Table Size ComboBox

        tableSizeComboBox = new JComboBox<>(BoardType.values());

        tableSizeComboBox.setSelectedItem(selectedTableSize);

        tableSizeComboBox.setBounds((int) (width * 0.225), (int) (height * 0.665), (int) (width * 0.4), componentHeight);

        tableSizeComboBox.addActionListener(e -> selectedTableSize = (BoardType) tableSizeComboBox.getSelectedItem());

        settingsPanel.add(tableSizeComboBox);


// Add Start Game button

        JButton startButton = new JButton("Start Game");

        startButton.setBounds((int) (width * 0.1), (int) (height * 0.85), (int) (width * 0.2), componentHeight);

        startButton.setBackground(new Color(80, 130, 200));

        startButton.setForeground(Color.WHITE);

        startButton.setFocusPainted(false);

        startButton.addActionListener(e -> startGame());

        settingsPanel.add(startButton);


// Add Cancel button

        JButton cancelButton = new JButton("Cancel");

        cancelButton.setBounds((int) (width * 0.7), (int) (height * 0.85), (int) (width * 0.2), componentHeight);

        cancelButton.setBackground(new Color(200, 100, 100));

        cancelButton.setForeground(Color.WHITE);

        cancelButton.setFocusPainted(false);

        cancelButton.addActionListener(e -> toggleSettingsPanel());

        settingsPanel.add(cancelButton);

    }

    /**
     * Acció que s’executa quan l’usuari, un cop ha assignat la configuració de la partida, decideix començar una partida
     * Actualment pendent d’implementació.
     */

    private boolean checkEmptyName(){
        for (int i = 0; i < 4; ++i){
            if ((types[i].equals("Real")) && names[i].equals(""))
                return true;
        }
        return false;
    }

    private void startGame() {
        // Recoger nombres directamente al iniciar la partida
        names[0] = player1Name.getText();
        names[1] = player2Name.getText();
        names[2] = player3Name.getText();
        names[3] = player4Name.getText();

        // Recoger tipos de jugadores
        types[0] = (String) player1Type.getSelectedItem();
        types[1] = (String) player2Type.getSelectedItem();
        types[2] = (String) player3Type.getSelectedItem();
        types[3] = (String) player4Type.getSelectedItem();

        // Close settings panel
        toggleSettingsPanel();
        // Obtain GameProperties

        List<String> players = new ArrayList<>();
        List<String> cpus = new ArrayList<>();
        int cpuIndex = 1;
        for (int i = 0; i < 4; ++i) {
            if (types[i].equals("CPU")) {
                cpus.add("CPU " + cpuIndex);
                cpuIndex++;
            } else if (types[i].equals("Real")) {
                players.add(names[i]);
            }
        }
        if (!checkEmptyName() && (players.size() + cpus.size()) >= 2) {
            SceneManager.getInstance().load(GameScene.class, new GameProperties(selectedLanguage, selectedTableSize, players, cpus, false), window);
        }
    }
}