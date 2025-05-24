package edu.upc.prop.scrabble.presenter.swing;

import edu.upc.prop.scrabble.presenter.swing.screens.game.GameMock;
import edu.upc.prop.scrabble.presenter.swing.screens.menu.MenuMock;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

/**
 * Punt d'entrada principal de l'aplicació Scrabble amb interfície Swing.
 * Crea la finestra principal i mostra la pantalla de joc.
 * Gestiona la configuració de la finestra segons el sistema operatiu.
 *
 * @author Gerard Gascón
 */
public class Main {
    /**
     * Mètode principal que inicia l'aplicació.
     * Executa la creació de la finestra en el fil d'esdeveniments de Swing.
     *
     * @param args arguments de la línia de comandes (no utilitzats)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = createMainWindow();

            disableTraversalKeys();
            window.add(new GameMock());
            window.setVisible(true);
        });
    }

    /**
     * Deshabilita les tecles de navegació per defecte (TAB, SHIFT+TAB) per evitar que canviïn el focus.
     */
    private static void disableTraversalKeys() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .setDefaultFocusTraversalKeys(
                        KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                        Collections.emptySet()
                );

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .setDefaultFocusTraversalKeys(
                        KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
                        Collections.emptySet()
                );
    }

    /**
     * Crea la finestra principal adaptada segons el sistema operatiu.
     *
     * @return JFrame configurat com a finestra principal
     */
    private static JFrame createMainWindow() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows"))
            return createMainWindow_Windows();
        else
            return createMainWindow_Linux();
    }

    /**
     * Crea la finestra principal per a sistemes Windows.
     * La finestra és sense decoració, no redimensionable i a pantalla completa.
     *
     * @return JFrame configurat per a Windows
     */
    private static JFrame createMainWindow_Windows() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        window.setUndecorated(true);
        window.setResizable(false);
        window.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        return window;
    }

    /**
     * Crea la finestra principal per a sistemes Linux.
     * Intenta posar la finestra en mode pantalla completa si és compatible.
     * En cas contrari, estableix la mida completa de la pantalla.
     *
     * @return JFrame configurat per a Linux
     */
    private static JFrame createMainWindow_Linux() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(window);
        } else {
            window.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
            window.setLocationRelativeTo(null);
        }

        return window;
    }
}
