package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.presenter.terminal.scenes.GameScene;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;



public class GameCreator extends SceneObject {
    private final LanguageSetter languageSetter = new LanguageSetter();
    private final SizeSetter sizeSetter = new SizeSetter();
    private final PlayerSetter playerSetter = new PlayerSetter();
    private final Reader reader = new Reader();

    enum State {
        Lang,
        Size,
        Player
    }
    State state = State.Lang;

    @Override
    public void onProcess(float delta) {

        String action = reader.readLine();
        if (action == null)
            return;
        if (action.equals("next")) {
            next();
        }
        if (action.equals("previous")) {
            previous();
        }
        if (action.equals("Submit")) {
            submit();
        }
    }

    private void previous(){
        switch (state) {
            case Lang:
                languageSetter.previous();
                break;
            case Size:
                sizeSetter.previous();
                break;
            case Player:
                playerSetter.Decrease();
                break;
        }
    }

    private void next(){
        switch (state) {
            case Lang:
                languageSetter.next();
                break;
            case Size:
                sizeSetter.next();
                break;
            case Player:
                playerSetter.Increase();
                break;
        }
    }

    private void submit(){
        switch (state) {
            case Lang:
                state = State.Size;
                break;
            case Size:
                state = State.Player;
                break;
            case Player:

                if (playerSetter.hasEnded())
                    SceneManager.getInstance().load(GameScene.class);
                else
                    playerSetter.switchMode();

                break;
        }
    }
}
