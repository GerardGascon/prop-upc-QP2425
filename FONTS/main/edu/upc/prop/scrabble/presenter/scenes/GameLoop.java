package edu.upc.prop.scrabble.presenter.scenes;

/**
 * Classe que gestiona el bucle principal del joc.
 * <p>
 * El {@code GameLoop} s'encarrega de controlar l'execució contínua del joc,
 * actualitzant l'estat de l'escena activa a un ritme constant (FPS).
 * </p>
 *
 * @author Gerard Gascón
 */
public class GameLoop {

    /**
     * Constructor que inicialitza el bucle carregant l'escena inicial.
     *
     * @param initialScene Classe de l'escena inicial que es vol carregar.
     * @param dependencies Dependències que pugui tenir l'escena inicial.
     */
    public GameLoop(Class<? extends Scene> initialScene, Object... dependencies) {
        SceneManager.getInstance().load(initialScene, dependencies);
    }

    /**
     * Executa el bucle principal del joc, mantenint una actualització contínua de l'escena
     * amb una velocitat fixa de fotogrames per segon (FPS).
     * <p>
     * El mètode calcula el temps transcorregut entre iteracions per passar-lo a l'escena,
     * i dorm el fil per mantenir la taxa constant de fotogrames.
     * </p>
     */
    public void run() {
        long lastTime = System.nanoTime();
        final double fps = 60.0;
        final double frameTime = 1e9 / fps;

        while (SceneManager.getInstance().isRunning()) {
            long now = System.nanoTime();
            double delta = (now - lastTime) / 1e9;
            lastTime = now;

            SceneManager.getInstance().process((float)delta);

            try {
                long sleep = (long) ((frameTime - (System.nanoTime() - now)) / 1e6);
                if (sleep > 0)
                    Thread.sleep(sleep);
            } catch (InterruptedException ignored) {}
        }
    }
}
