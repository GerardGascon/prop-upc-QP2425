package edu.upc.prop.scrabble.presenter.swing.screens.menu.jugar;


import edu.upc.prop.scrabble.presenter.swing.screens.menu.MenuButton;


import javax.swing.*;

import java.awt.*;


public class JugarButton extends MenuButton {

    private JPanel parentPanel;

    private boolean menuActive = false;

    private JPanel settingsPanel;

    private JComboBox<String> languageComboBox;

    private JComboBox<String> player1Type;

    private JComboBox<String> player2Type;

    private JComboBox<String> player3Type;

    private JComboBox<String> player4Type;

    private JTextField player1Name;

    private JTextField player2Name;

    private JTextField player3Name;

    private JTextField player4Name;


    private JComboBox<String> tableSizeComboBox;

    private String selectedLanguage = "English";

    private int selectedRealPlayers = 2;

    private int selectedCPUPlayers = 0;

    private String selectedTableSize = "Medium";


    public JugarButton(JPanel parent) {

        super("Jugar");

        this.parentPanel = parent;

        addActionListener(_ -> {

            toggleSettingsPanel();

        });

    }


    private void toggleSettingsPanel() {

// Retornar al mode original

        if (menuActive) {

            Container container = parentPanel.getParent();

            container.remove(settingsPanel);

            menuActive = false;

            container.revalidate();

            container.repaint();

            return;

        }


// Guardme menu original

        menuActive = true;

        Container menuMockPanel = parentPanel.getParent();


// Panel de settings

        settingsPanel = new JPanel() {

            @Override

            protected void paintComponent(Graphics g) {


                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


// Create panel background with rounded corners

                int panelX = getWidth() / 6;

                int panelY = getHeight() / 40;

                int panelWidth = getWidth() * 2 / 3;

                int panelHeight = getHeight() * 16 / 17;


// Sombra

                g2d.setColor(new Color(50, 50, 50, 100));

                g2d.fillRoundRect(panelX + 5, panelY + 5, panelWidth, panelHeight, 20, 20);


// Fill

                GradientPaint gradient = new GradientPaint(

                        panelX, panelY, new Color(240, 240, 250),

                        panelX + panelWidth, panelY + panelHeight, new Color(210, 210, 230)

                );

                g2d.setPaint(gradient);

                g2d.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 20, 20);


// Borde

                g2d.setColor(new Color(180, 180, 200));

                g2d.setStroke(new BasicStroke(2f));

                g2d.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 20, 20);


// Títol

                g2d.setFont(new Font("SansSerif", Font.BOLD, 30));

                g2d.setColor(new Color(60, 60, 80));

                String title = "Game Settings";

                FontMetrics fm = g2d.getFontMetrics();

                int titleWidth = fm.stringWidth(title);

                g2d.drawString(title, panelX + (panelWidth - titleWidth) / 2, panelY + 50);


// Linea títol

                g2d.setColor(new Color(180, 180, 200));

                g2d.drawLine(panelX + 20, panelY + 70, panelX + panelWidth - 20, panelY + 70);


// Settings títols

                g2d.setFont(new Font("SansSerif", Font.BOLD, 20));

                g2d.setColor(new Color(80, 80, 100));


                int textX = panelX + 40;

                int baseY = panelY + 120;

                int sectionHeight = 80;



                g2d.drawString("Player 1", textX + 100, baseY);

                g2d.drawString("Player 2", textX + 300, baseY);

                g2d.drawString("Player 3", textX + 500, baseY);

                g2d.drawString("Player 4", textX + 700, baseY);


                g2d.drawString("Type: ", textX, baseY + 35);

                g2d.drawString("Name: ", textX, baseY + 85);


                g2d.drawString("Language", textX, baseY + 2 * sectionHeight);

                g2d.drawString("Table Size", textX, baseY + 3 * sectionHeight);


                g2d.dispose();

            }

        };



        settingsPanel.setLayout(null);

        settingsPanel.setOpaque(false);


// Dimensions del pannel

        int panelX = settingsPanel.getWidth() / 6;

        int panelY = settingsPanel.getHeight() / 40;

        int panelWidth = settingsPanel.getWidth() * 2 / 3;


// Afegir bottons d'opció multiple

        addSettingsComponents();


// Fusionar amb l'escena original

        menuMockPanel.add(settingsPanel);


// Ajustem a on es posa

        menuMockPanel.setLayout(null);

        int menuWidth = (int) (menuMockPanel.getWidth() * 0.6);

        settingsPanel.setBounds(

                (int) (menuMockPanel.getWidth() * 0.4),

                0,

                menuWidth,

                menuMockPanel.getHeight()

        );


// Refresh

        menuMockPanel.revalidate();

        menuMockPanel.repaint();

    }


    private void addSettingsComponents() {


        int panelX = settingsPanel.getWidth() / 6;

        int panelY = settingsPanel.getHeight() / 40;

        int panelWidth = settingsPanel.getWidth() * 2 / 3;


        int componentX = panelX + 400;

        int baseY = panelY + 120;

        int sectionHeight = 80;

        int componentWidth = 150;

        int componentHeight = 30;


// Real Players ComboBox

        String[] playerTypes = {"Real", "CPU", " "};


        player1Type = new JComboBox<>(playerTypes);

        player1Type.setSelectedItem(selectedRealPlayers);

        player1Type.setBounds(componentX, baseY + 50, componentWidth, componentHeight);

        player1Type.addActionListener(e -> selectedRealPlayers = (Integer) player1Type.getSelectedItem());

        settingsPanel.add(player1Type);


        player2Type = new JComboBox<>(playerTypes);

        player2Type.setSelectedItem(selectedRealPlayers);

        player2Type.setBounds(componentX + 200, baseY + 50, componentWidth, componentHeight);

        player2Type.addActionListener(e -> selectedRealPlayers = (Integer) player2Type.getSelectedItem());

        settingsPanel.add(player2Type);


        player3Type = new JComboBox<>(playerTypes);

        player3Type.setSelectedItem(selectedRealPlayers);

        player3Type.setBounds(componentX + 400, baseY + 50, componentWidth, componentHeight);

        player3Type.addActionListener(e -> selectedRealPlayers = (Integer) player3Type.getSelectedItem());

        settingsPanel.add(player3Type);


        player4Type = new JComboBox<>(playerTypes);

        player4Type.setSelectedItem(selectedRealPlayers);

        player4Type.setBounds(componentX + 600, baseY + 50, componentWidth, componentHeight);

        player4Type.addActionListener(e -> selectedRealPlayers = (Integer) player4Type.getSelectedItem());

        settingsPanel.add(player4Type);


        player1Name = new JTextField();

        player1Name.setEditable(true);

        player1Name.setBounds(componentX, baseY + 100, componentWidth, componentHeight);

        player1Name.setColumns(10);

        settingsPanel.add(player1Name);


        player2Name = new JTextField();

        player2Name.setEditable(true);

        player2Name.setBounds(componentX+200, baseY + 100, componentWidth, componentHeight);

        player2Name.setColumns(10);

        settingsPanel.add(player2Name);


        player3Name = new JTextField();

        player3Name.setEditable(true);

        player3Name.setBounds(componentX+400, baseY + 100, componentWidth, componentHeight);

        player3Name.setColumns(10);

        settingsPanel.add(player3Name);


        player4Name = new JTextField();

        player4Name.setEditable(true);

        player4Name.setBounds(componentX+600, baseY + 100, componentWidth, componentHeight);

        player4Name.setColumns(10);

        settingsPanel.add(player4Name);




// Language ComboBox

        String[] languageOptions = {"English", "Español", "Català"};

        languageComboBox = new JComboBox<>(languageOptions);

        languageComboBox.setSelectedItem(selectedLanguage);

        languageComboBox.setBounds(componentX, baseY + 2 * sectionHeight + 50, componentWidth, componentHeight);

        languageComboBox.addActionListener(e -> selectedLanguage = (String) languageComboBox.getSelectedItem());

        settingsPanel.add(languageComboBox);


// Table Size ComboBox

        String[] tableSizeOptions = {"Junior", "Standard", "Super"};

        tableSizeComboBox = new JComboBox<>(tableSizeOptions);

        tableSizeComboBox.setSelectedItem(selectedTableSize);

        tableSizeComboBox.setBounds(componentX, baseY + 3 * sectionHeight + 50, componentWidth, componentHeight);

        tableSizeComboBox.addActionListener(e -> selectedTableSize = (String) tableSizeComboBox.getSelectedItem());

        settingsPanel.add(tableSizeComboBox);


// Add Start Game button

        JButton startButton = new JButton("Start Game");

        startButton.setBounds(panelX + 600, baseY + 13 * sectionHeight + 50, 150, 40);

        startButton.setBackground(new Color(80, 130, 200));

        startButton.setForeground(Color.WHITE);

        startButton.setFocusPainted(false);

        startButton.addActionListener(e -> startGame());

        settingsPanel.add(startButton);


// Add Cancel button

        JButton cancelButton = new JButton("Cancel");

        cancelButton.setBounds(panelX + 800, baseY + 13 * sectionHeight + 50, 150, 40);

        cancelButton.setBackground(new Color(200, 100, 100));

        cancelButton.setForeground(Color.WHITE);

        cancelButton.setFocusPainted(false);

        cancelButton.addActionListener(e -> toggleSettingsPanel());

        settingsPanel.add(cancelButton);

    }


    private void startGame() {

// Collect all settings


        System.out.println("Starting game with:");

        System.out.println("Real Players: " + selectedRealPlayers);

        System.out.println("CPU Players: " + selectedCPUPlayers);

        System.out.println("Language: " + selectedLanguage);

        System.out.println("Table Size: " + selectedTableSize);


// Close settings panel

        toggleSettingsPanel();


// TODO: Implement game start logic here

    }

}