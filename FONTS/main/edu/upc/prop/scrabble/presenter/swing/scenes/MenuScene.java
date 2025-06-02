package edu.upc.prop.scrabble.presenter.swing.scenes;

import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.persistence.platform.gson.deserializers.GsonDeserializer;
import edu.upc.prop.scrabble.persistence.platform.gson.streamers.SaveReader;
import edu.upc.prop.scrabble.persistence.runtime.controllers.DataRestorer;
import edu.upc.prop.scrabble.persistence.runtime.controllers.GameLoader;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IDeserializer;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveReader;
import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.swing.screens.menu.MenuScreen;

import javax.swing.*;

/**
 * Escena encarregada del menú principal
 * @author Felipe Martínez Lassalle
 * @see Scene
 */
public class MenuScene extends Scene {
    /**
     * Finestra principal del programa
     */
    private final JFrame window;
    /**
     * Panell on es col·loquen els elements de la vista
     */
    private final MenuScreen menuScreen;

    /**
     * Nom de l'arxiu de dades del rànquing
     */
    public static final String LEADERBOARD_FILE_NAME = "leaderboard.data";

    /**
     * Creadora i inicialitzadora de tots els elements presents al menú
     * @param window Finestra sobre la qual es mostrarà el menú
     */
    public MenuScene(JFrame window) {
        this.window = window;
        window.getContentPane().removeAll();

        Leaderboard leaderboard = new Leaderboard();

        DataRestorer dataRestorer = new DataRestorer();
        IDeserializer deserializer = new GsonDeserializer();
        ISaveReader saveReader = new SaveReader();
        GameLoader loader = new GameLoader(dataRestorer, deserializer, saveReader, LEADERBOARD_FILE_NAME);
        dataRestorer.addPersistableObject(leaderboard);

        if (saveReader.exists(LEADERBOARD_FILE_NAME))
            loader.run();

        menuScreen = new MenuScreen(window, leaderboard);

        menuScreen.getQuitButton().addActionListener(e -> System.exit(0));

        window.add(menuScreen);
        window.revalidate();
        window.repaint();
    }


    /**
     * Mètode cridat quan l’escena és desconnectada (per exemple, en canviar a una altra escena).
     * Elimina el panell del menú de la finestra.
     */
    @Override
    protected void onDetach() {
        super.onDetach();
        window.remove(menuScreen);
    }

    /**
     * Mètode cridat de forma periòdica pel bucle del joc per actualitzar l’animació del menú.
     *
     * @param delta Temps (en segons) des de l’última actualització.
     */
    @Override
    protected void onProcess(float delta) {
        super.onProcess(delta);
        menuScreen.updateAnimation(delta);
    }

}