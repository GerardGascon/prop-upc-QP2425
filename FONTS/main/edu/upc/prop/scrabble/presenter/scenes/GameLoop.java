package edu.upc.prop.scrabble.presenter.scenes;

public class GameLoop {

    public GameLoop(Class<? extends Scene> initialScene) {
        SceneManager.getInstance().load(initialScene);
    }

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
