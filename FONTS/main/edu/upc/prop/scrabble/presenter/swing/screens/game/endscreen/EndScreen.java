package edu.upc.prop.scrabble.presenter.swing.screens.game.endscreen;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.properties.GameProperties;
import edu.upc.prop.scrabble.domain.game.IEndScreen;
import edu.upc.prop.scrabble.persistence.platform.gson.streamers.SaveReader;
import edu.upc.prop.scrabble.presenter.swing.scenes.GameScene;
import edu.upc.prop.scrabble.presenter.swing.screens.menu.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * EndScreen és un JPanel que mostra els resultats finals d'una partida de Scrabble.
 * Mostra un podi amb els 3 primers jugadors, una llista completa de classificació, i proporciona
 * botons per iniciar una nova partida, tornar al menú, o sortir de l'aplicació.
 * <p>
 * La pantalla presenta un fons amb degradat amb una visualització de podi i
 * targetes de jugadors codificades per colors que mostren puntuacions i posicions.
 *
 * @author Albert Usero
 */
public class EndScreen extends JPanel implements IEndScreen {
    /**
     * Overlay que conté els elements visuals de la pantalla final
     */
    private EndScreenOverlay endScreenOverlay;

    /**
     * Propietats de la partida actual
     */
    private GameProperties gameProperties;

    /**
     * Finestra principal de l'aplicació
     */
    private JFrame window;

    /**
     * Constructor que crea un EndScreen amb la finestra i propietats especificades.
     *
     * @param window Finestra principal de l'aplicació
     * @param gameProperties Propietats de la partida actual
     */
    public EndScreen(JFrame window, GameProperties gameProperties) {
        setLayout(null);
        setOpaque(false);
        this.window = window;
        this.gameProperties = gameProperties;
    }

    /**
     * Mostra la pantalla final amb l'array de jugadors ordenats proporcionat.
     * Fa visible la finestra, la porta al davant i demana el focus.
     *
     * @param sortedPlayers Array de jugadors ordenats per puntuació final
     */
    @Override
    public void show(Player[] sortedPlayers) {
        if (endScreenOverlay != null)
            return;

        new SaveReader().delete(GameScene.SAVE_FILE_NAME);
        endScreenOverlay = new EndScreenOverlay(sortedPlayers,window, gameProperties);
        endScreenOverlay.setBounds(0, 0, getWidth(), getHeight());
        add(endScreenOverlay);
        endScreenOverlay.requestFocusInWindow();
        setComponentZOrder(endScreenOverlay, 0);
        revalidate();
        repaint();
    }
}
