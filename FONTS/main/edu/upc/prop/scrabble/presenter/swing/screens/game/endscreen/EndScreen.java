package edu.upc.prop.scrabble.presenter.swing.screens.game.endscreen;

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
 * <p>
 * La pantalla presenta un fons amb degradat amb una visualització de podi i
 * targetes de jugadors codificades per colors que mostren puntuacions i posicions.
 *
 * @author Albert Usero
 */
public class EndScreen extends JPanel implements IEndScreen {
    /** Callback per tornar al menú principal */
    private ActionListener onMenuCallback;

    /** Callback per iniciar una nova partida */
    private ActionListener onNewGameCallback;

    private EndScreenOverlay endScreenOverlay;

    /**
     * Constructor per defecte que crea un EndScreen sense jugadors.
     * Inicialitza el component amb l'estil per defecte i arrays de jugadors buits.
     */
    public EndScreen() {
        setLayout(null);
        setOpaque(false);
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

        endScreenOverlay = new EndScreenOverlay(sortedPlayers);
        endScreenOverlay.setBounds(0, 0, getWidth(), getHeight());
        add(endScreenOverlay);
        endScreenOverlay.requestFocusInWindow();
        setComponentZOrder(endScreenOverlay, 0);
        revalidate();
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
}