package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import edu.upc.prop.scrabble.data.board.BoardType;
import edu.upc.prop.scrabble.data.properties.GameProperties;
import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.persistence.platform.gson.streamers.SaveReader;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.swing.scenes.GameScene;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static edu.upc.prop.scrabble.presenter.swing.scenes.GameScene.SAVE_FILE_NAME;


/**
 * Botó encarregat de gestionar el menú de continue i les seves funcionalitats associades,
 * com carregar una partida o sortir del menú.
 *
 * @author Biel Perez Silvestre
 */
public class ContinueButton extends MenuButton {
    /**
     * Panell principal sobre el qual es mostren els menús desplegables del botó.
     */
    private final JPanel parentPanel;
    /**
     * La finestra principal del programa
     */
    private final JFrame window;
    /**
     * Indica si el menú desplegable del botó "Continuar" està actualment actiu o no.
     */
    private boolean menuActive = false;
    /**
     * Panell de configuració de la partida amb fons degradat,
     * títol, etiquetes i separadors visuals,
     * on es col·loquen els botons que s'utlitzaràn.
     */
    private JPanel settingsPanel;

    /**
     * Creador del botó "Continuar", que desplega el menú per carregar una partida guardada.
     *
     * @param parent El panell principal sobre el qual es mostrarà el menú.
     * @param window La finestra principal on es troba el programa.
     */
    public ContinueButton(JPanel parent, JFrame window) {
        super("Continuar");
        this.parentPanel = parent;
        this.window = window;

        if (!new SaveReader().exists(SAVE_FILE_NAME)){
            setBackground(new Color(255, 255, 255, 100));
            setEnabled(false);
        }

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
        if (!menuActive) return; // No fem res si ja està tancat

        Container container = parentPanel.getParent();
        container.remove(settingsPanel);
        menuActive = false;
        container.revalidate();
        container.repaint();
    }

    /**
     * Mostra o amaga el panell de configuració de la partida associat al botó "Continuar".
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
                GradientPaint gradient;
                if (isEnabled()){
                    gradient = new GradientPaint(
                            (float) (width * 0.4), (float) (height * 0.15),
                            new Color(240, 240, 250),
                            (float) (width * 0.8), (float) (height * 0.95), new Color(210, 210, 230)
                    );
                } else {
                    gradient = new GradientPaint(
                            (float) (width * 0.4), (float) (height * 0.15),
                            new Color(240, 240, 250, 100),
                            (float) (width * 0.8), (float) (height * 0.95), new Color(210, 210, 230, 100)
                    );
                }

                g2d.setPaint(gradient);
                g2d.fillRoundRect((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.9), (int) (height * 0.9), 20, 20);

                // Títol
                g2d.setFont(new Font("SansSerif", Font.BOLD, 30));
                g2d.setColor(new Color(60, 60, 80));
                String title = "Continuar Partida";
                FontMetrics fm = g2d.getFontMetrics();
                int titleWidth = fm.stringWidth(title);
                g2d.drawString(title, (int) (width * 0.4), (int) (height * 0.15));

                // Linea títol
                g2d.setColor(new Color(180, 180, 200));
                g2d.drawLine((int) (width * 0.1), (int) (height * 0.2), (int) (width * 0.9), (int) (height * 0.2));

                // Settings títols
                g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
                g2d.setColor(new Color(80, 80, 100));

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

        int componentWidth = (int) (width * 0.15);
        int componentHeight = (int) (height * 0.15);
        // Load Game Button
        JButton loadGameButton = new JButton("Carregar Partida");

        loadGameButton.setBounds((int) (width * 0.25), (int) (height * 0.45), componentWidth, componentHeight);

        loadGameButton.setBackground(new Color(80, 130, 200));

        loadGameButton.setForeground(Color.WHITE);

        loadGameButton.setFocusPainted(false);

        loadGameButton.addActionListener(e -> loadGame());

        settingsPanel.add(loadGameButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancelar");

        cancelButton.setBounds((int) (width * 0.55), (int) (height * 0.45), componentWidth, componentHeight);

        cancelButton.setBackground(new Color(200, 100, 100));

        cancelButton.setForeground(Color.WHITE);

        cancelButton.setFocusPainted(false);

        cancelButton.addActionListener(e -> toggleSettingsPanel());

        settingsPanel.add(cancelButton);
    }

    /**
     * Acció que s’executa quan l’usuari decideix carregar una partida.
     * Actualment pendent d’implementació.
     */
    private void loadGame() {
        toggleSettingsPanel();

        // Només ens importa el reload=true
        GameProperties properties = new GameProperties(Language.Catalan, BoardType.Standard, new ArrayList<>(), new ArrayList<>(), true);
        SceneManager.getInstance().load(GameScene.class, properties, window);
    }
}



